package com.food.cpg.manufactureorder;

public class ManufactureOrderEndPoint {

    public static final String MANUFACTURE_ORDERS_REQUEST_END_POINT = "/manufacture-orders";
    public static final String MANUFACTURE_ORDER_FORM_END_POINT = "/add-manufacture-order";
    public static final String SAVE_MANUFACTURE_ORDER_END_POINT = "/save-manufacture-order";
    public static final String DELETE_MANUFACTURE_ORDER_END_POINT = "/manufacture-order/delete/{orderNumber}";
    public static final String MOVE_MANUFACTURE_ORDER_END_POINT = "/manufacture-order/move/{orderNumber}";
    public static final String CALCULATE_MANUFACTURE_COST_END_POINT = "/calculate-total";

    private ManufactureOrderEndPoint() {
    }
}
