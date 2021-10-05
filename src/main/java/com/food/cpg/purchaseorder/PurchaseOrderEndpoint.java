package com.food.cpg.purchaseorder;

public class PurchaseOrderEndpoint {

    public static final String PURCHASE_ORDERS_END_POINT = "/purchase-orders";
    public static final String PURCHASE_ORDER_FORM_END_POINT = "/add-purchase-order";
    public static final String PURCHASE_ORDER_BY_ITEM_FORM_END_POINT = "/add-purchase-order-byitem";
    public static final String ADD_PO_RAW_MATERIAL_END_POINT = "/add-po-raw-material";
    public static final String SAVE_PURCHASE_ORDER_END_POINT = "/save-purchase-order";
    public static final String SAVE_PURCHASE_ORDER_BY_ITEM_END_POINT = "/save-purchase-order-byitem";
    public static final String DELETE_PURCHASE_ORDER_END_POINT = "/purchase-orders/delete/{orderNumber}";
    public static final String MOVE_PURCHASE_ORDER_END_POINT = "/purchase-orders/move/{orderNumber}";

    private PurchaseOrderEndpoint() {
    }
}
