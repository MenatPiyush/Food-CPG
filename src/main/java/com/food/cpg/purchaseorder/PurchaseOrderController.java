package com.food.cpg.purchaseorder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.food.cpg.inventory.Unit;
import com.food.cpg.item.Item;
import com.food.cpg.rawmaterial.RawMaterial;
import com.food.cpg.vendor.Vendor;

@Controller
public class PurchaseOrderController {

    private static final String REDIRECT_NOTATION = "redirect:";
    private static final String SHOW_PURCHASE_ORDERS_ROUTE = "purchase-order/purchase-orders";
    private static final String SHOW_ADD_PURCHASE_ORDER_FORM_ROUTE = "purchase-order/add-purchase-order";
    private static final String SHOW_ADD_PURCHASE_ORDER_BY_ITEM_FORM_ROUTE = "purchase-order/add-purchase-order-byitem";
    private static final String VIEW_OPEN_PURCHASE_ORDERS_KEY = "openPurchaseOrders";
    private static final String VIEW_PLACED_PURCHASE_ORDERS_KEY = "placedPurchaseOrders";
    private static final String VIEW_RECEIVED_PURCHASE_ORDERS_KEY = "receivedPurchaseOrders";
    private static final String VIEW_RAW_MATERIALS_KEY = "rawMaterials";
    private static final String VIEW_UNITS_KEY = "units";
    private static final String VIEW_VENDORS_KEY = "vendors";
    private static final String VIEW_ITEMS_KEY = "items";
    private static final String ORDER_NUMBER_PATH_VARIABLE_NAME = "orderNumber";

    @GetMapping(PurchaseOrderEndpoint.PURCHASE_ORDERS_END_POINT)
    public String showPurchaseOrders(PurchaseOrder purchaseOrder, Model model) {
        model.addAttribute(VIEW_OPEN_PURCHASE_ORDERS_KEY, purchaseOrder.getAllOpenOrders());
        model.addAttribute(VIEW_PLACED_PURCHASE_ORDERS_KEY, purchaseOrder.getAllPlacedOrders());
        model.addAttribute(VIEW_RECEIVED_PURCHASE_ORDERS_KEY, purchaseOrder.getAllReceivedOrders());
        return SHOW_PURCHASE_ORDERS_ROUTE;
    }

    @GetMapping(PurchaseOrderEndpoint.PURCHASE_ORDER_FORM_END_POINT)
    public String showAddPurchaseOrderForm(PurchaseOrder purchaseOrder, PurchaseOrderRawMaterial purchaseOrderRawMaterial, RawMaterial rawMaterial, Vendor vendor, Model model) {
        model.addAttribute(VIEW_UNITS_KEY, Unit.values());
        model.addAttribute(VIEW_VENDORS_KEY, vendor.getAll());
        model.addAttribute(VIEW_RAW_MATERIALS_KEY, rawMaterial.getAll());
        return SHOW_ADD_PURCHASE_ORDER_FORM_ROUTE;
    }

    @GetMapping(PurchaseOrderEndpoint.PURCHASE_ORDER_BY_ITEM_FORM_END_POINT)
    public String showAddPurchaseOrderByItemForm(PurchaseOrderByItem purchaseOrderByItem, PurchaseOrderRawMaterial purchaseOrderRawMaterial, Item item, Model model) {
        model.addAttribute(VIEW_ITEMS_KEY, item.getAll());
        return SHOW_ADD_PURCHASE_ORDER_BY_ITEM_FORM_ROUTE;
    }

    @PostMapping(PurchaseOrderEndpoint.ADD_PO_RAW_MATERIAL_END_POINT)
    public String addPurchaseOrderRawMaterial(PurchaseOrder purchaseOrder, PurchaseOrderRawMaterial purchaseOrderRawMaterial, RawMaterial rawMaterial, Vendor vendor, Model model) {
        purchaseOrderRawMaterial.loadDetails(rawMaterial);
        purchaseOrder.addPurchaseOrderRawMaterials(purchaseOrderRawMaterial);

        model.addAttribute(VIEW_UNITS_KEY, Unit.values());
        model.addAttribute(VIEW_VENDORS_KEY, vendor.getAll());
        model.addAttribute(VIEW_RAW_MATERIALS_KEY, rawMaterial.getAll());
        return SHOW_ADD_PURCHASE_ORDER_FORM_ROUTE;
    }

    @PostMapping(PurchaseOrderEndpoint.SAVE_PURCHASE_ORDER_BY_ITEM_END_POINT)
    public String savePurchaseOrderByItem(PurchaseOrderByItem purchaseOrderByItem, RawMaterial rawMaterial) {
        purchaseOrderByItem.createPurchaseOrderByItem(rawMaterial);
        return redirectToPurchaseOrders();
    }

    @PostMapping(PurchaseOrderEndpoint.SAVE_PURCHASE_ORDER_END_POINT)
    public String savePurchaseOrder(PurchaseOrder purchaseOrder) {
        purchaseOrder.save();
        return redirectToPurchaseOrders();
    }

    @GetMapping(PurchaseOrderEndpoint.DELETE_PURCHASE_ORDER_END_POINT)
    public String deletePurchaseOrder(@PathVariable(ORDER_NUMBER_PATH_VARIABLE_NAME) String orderNumber, PurchaseOrder purchaseOrder, PurchaseOrderRawMaterial purchaseOrderRawMaterial) {
        purchaseOrder.setOrderNumber(orderNumber);
        purchaseOrderRawMaterial.setPurchaseOrderNumber(orderNumber);
        purchaseOrder.delete();
        purchaseOrderRawMaterial.delete();
        return redirectToPurchaseOrders();
    }

    @GetMapping(PurchaseOrderEndpoint.MOVE_PURCHASE_ORDER_END_POINT)
    public String movePurchaseOrder(@PathVariable(ORDER_NUMBER_PATH_VARIABLE_NAME) String orderNumber, PurchaseOrder purchaseOrder) {
        purchaseOrder.setOrderNumber(orderNumber);
        purchaseOrder.load();
        purchaseOrder.moveOrderToNextStage();
        return redirectToPurchaseOrders();
    }

    private String redirectToPurchaseOrders() {
        return REDIRECT_NOTATION + PurchaseOrderEndpoint.PURCHASE_ORDERS_END_POINT;
    }
}