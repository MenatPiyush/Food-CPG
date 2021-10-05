package com.food.cpg.salesorder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.food.cpg.item.Item;
import com.food.cpg.packaging.Package;

@Controller
public class SalesOrderController {

    private static final String REDIRECT_NOTATION = "redirect:";
    private static final String SHOW_SALES_ORDERS_ROUTE = "sales-order/sales-orders";
    private static final String SHOW_ADD_SALES_ORDER_FORM_ROUTE = "sales-order/add-sales-order";
    private static final String VIEW_OPEN_SALES_ORDERS_KEY = "openSalesOrders";
    private static final String VIEW_PACKAGED_SALES_ORDERS_KEY = "packagedSalesOrders";
    private static final String VIEW_SHIPPED_SALES_ORDERS_KEY = "shippedSalesOrders";
    private static final String VIEW_PAID_SALES_ORDERS_KEY = "paidSalesOrders";
    private static final String VIEW_ITEMS_KEY = "items";
    private static final String VIEW_PACKAGES_KEY = "packages";
    private static final String ORDER_NUMBER_PATH_VARIABLE_NAME = "orderNumber";

    @GetMapping(SalesOrderEndpoint.SALES_ORDERS_REQUEST_END_POINT)
    public String showSalesOrders(SalesOrder salesOrder, Model model) {
        model.addAttribute(VIEW_OPEN_SALES_ORDERS_KEY, salesOrder.getAllOpenOrders());
        model.addAttribute(VIEW_PACKAGED_SALES_ORDERS_KEY, salesOrder.getAllPackagedOrders());
        model.addAttribute(VIEW_SHIPPED_SALES_ORDERS_KEY, salesOrder.getAllShippedOrders());
        model.addAttribute(VIEW_PAID_SALES_ORDERS_KEY, salesOrder.getAllPaidOrders());
        return SHOW_SALES_ORDERS_ROUTE;
    }

    @GetMapping(SalesOrderEndpoint.DELETE_SALES_ORDER_END_POINT)
    public String deleteSalesOrder(@PathVariable(ORDER_NUMBER_PATH_VARIABLE_NAME) String orderNumber, SalesOrder salesOrder) {
        salesOrder.setOrderNumber(orderNumber);
        salesOrder.delete();

        return redirectToSalesOrders();
    }

    @GetMapping(SalesOrderEndpoint.SALES_ORDER_FORM_END_POINT)
    public String addSalesOrderForm(SalesOrder salesOrder, Item item, Package packages, Model model) {
        model.addAttribute(VIEW_ITEMS_KEY, item.getAll());
        model.addAttribute(VIEW_PACKAGES_KEY, packages.getAll());
        return SHOW_ADD_SALES_ORDER_FORM_ROUTE;
    }

    @GetMapping(SalesOrderEndpoint.MOVE_SALES_ORDER_END_POINT)
    public String moveSalesOrder(@PathVariable(ORDER_NUMBER_PATH_VARIABLE_NAME) String orderNumber, SalesOrder salesOrder) {
        salesOrder.setOrderNumber(orderNumber);
        salesOrder.load();
        salesOrder.moveOrderToNextStage();

        return redirectToSalesOrders();
    }

    @PostMapping(SalesOrderEndpoint.CALCULATE_SALES_COST_END_POINT)
    public String calculateTotalCost(SalesOrder salesOrder, Item item, Package packages, Model model) {
        salesOrder.calculateTotalCost();
        model.addAttribute(VIEW_ITEMS_KEY, item.getAll());
        model.addAttribute(VIEW_PACKAGES_KEY, packages.getAll());
        return SHOW_ADD_SALES_ORDER_FORM_ROUTE;
    }

    @PostMapping(SalesOrderEndpoint.SAVE_SALES_ORDER_END_POINT)
    public String saveSalesOrder(SalesOrder salesOrder) {
        salesOrder.save();
        return redirectToSalesOrders();
    }

    private String redirectToSalesOrders() {
        return REDIRECT_NOTATION + SalesOrderEndpoint.SALES_ORDERS_REQUEST_END_POINT;
    }
}