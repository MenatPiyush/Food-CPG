package com.food.cpg.manufactureorder;

import com.food.cpg.inventory.Unit;
import com.food.cpg.item.Item;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ManufactureOrderController {

    private static final String REDIRECT_NOTATION = "redirect:";
    private static final String SHOW_MANUFACTURE_ORDERS_ROUTE = "manufacture-order/manufacture-orders";
    private static final String SHOW_ADD_MANUFACTURE_ORDER_FORM_ROUTE = "manufacture-order/add-manufacture-order";
    private static final String VIEW_OPEN_MANUFACTURE_ORDERS_KEY = "openManufactureOrders";
    private static final String VIEW_MANUFACTURED_MANUFACTURE_ORDERS_KEY = "manufacturedManufactureOrders";
    private static final String VIEW_PACKAGED_MANUFACTURE_ORDERS_KEY = "packagedManufactureOrders";
    private static final String VIEW_STORED_MANUFACTURE_ORDERS_KEY = "storedManufactureOrders";
    private static final String VIEW_ITEMS_KEY = "items";
    private static final String VIEW_UNITS_KEY = "units";
    private static final String ORDER_NUMBER_PATH_VARIABLE_NAME = "orderNumber";

    @GetMapping(ManufactureOrderEndPoint.MANUFACTURE_ORDERS_REQUEST_END_POINT)
    public String showManufactureOrders(ManufactureOrder manufactureOrder, Model model) {
        model.addAttribute(VIEW_OPEN_MANUFACTURE_ORDERS_KEY, manufactureOrder.getAllOpenOrders());
        model.addAttribute(VIEW_MANUFACTURED_MANUFACTURE_ORDERS_KEY, manufactureOrder.getAllManufacturedOrders());
        model.addAttribute(VIEW_PACKAGED_MANUFACTURE_ORDERS_KEY, manufactureOrder.getAllPackagedOrders());
        model.addAttribute(VIEW_STORED_MANUFACTURE_ORDERS_KEY, manufactureOrder.getAllStoredOrders());
        return SHOW_MANUFACTURE_ORDERS_ROUTE;

    }

    @GetMapping(ManufactureOrderEndPoint.MANUFACTURE_ORDER_FORM_END_POINT)
    public String addManufactureOrderForm(ManufactureOrder manufactureOrder, Item item, Model model) {
        model.addAttribute(VIEW_UNITS_KEY, Unit.values());
        model.addAttribute(VIEW_ITEMS_KEY, item.getAll());
        return SHOW_ADD_MANUFACTURE_ORDER_FORM_ROUTE;
    }

    @PostMapping(ManufactureOrderEndPoint.SAVE_MANUFACTURE_ORDER_END_POINT)
    public String saveManufactureOrder(ManufactureOrder manufactureOrder) {
        manufactureOrder.save();
        return redirectToManufactureOrders();
    }

    @GetMapping(ManufactureOrderEndPoint.DELETE_MANUFACTURE_ORDER_END_POINT)
    public String deleteSalesOrder(@PathVariable(ORDER_NUMBER_PATH_VARIABLE_NAME) String orderNumber, ManufactureOrder manufactureOrder) {
        manufactureOrder.setOrderNumber(orderNumber);
        manufactureOrder.delete();

        return redirectToManufactureOrders();
    }

    @GetMapping(ManufactureOrderEndPoint.MOVE_MANUFACTURE_ORDER_END_POINT)
    public String moveSalesOrder(@PathVariable(ORDER_NUMBER_PATH_VARIABLE_NAME) String orderNumber, ManufactureOrder manufactureOrder) {
        manufactureOrder.setOrderNumber(orderNumber);
        manufactureOrder.load();
        manufactureOrder.moveOrderToNextStage();

        return redirectToManufactureOrders();
    }

    @PostMapping(ManufactureOrderEndPoint.CALCULATE_MANUFACTURE_COST_END_POINT)
    public String calculateTotalCost(ManufactureOrder manufactureOrder, Item item, Model model) {
        manufactureOrder.calculateTotalCost();
        model.addAttribute(VIEW_UNITS_KEY, Unit.values());
        model.addAttribute(VIEW_ITEMS_KEY, item.getAll());
        return SHOW_ADD_MANUFACTURE_ORDER_FORM_ROUTE;
    }

    private String redirectToManufactureOrders() {
        return REDIRECT_NOTATION + ManufactureOrderEndPoint.MANUFACTURE_ORDERS_REQUEST_END_POINT;
    }
}