package com.food.cpg.salesorder;

public class SalesOrderEndpoint {

    public static final String SALES_ORDERS_REQUEST_END_POINT = "/sales-orders";
    public static final String SALES_ORDER_FORM_END_POINT = "/add-sales-order";
    public static final String SAVE_SALES_ORDER_END_POINT = "/save-sales-order";
    public static final String DELETE_SALES_ORDER_END_POINT = "/sales-order/delete/{orderNumber}";
    public static final String MOVE_SALES_ORDER_END_POINT = "/sales-order/move/{orderNumber}";
    public static final String CALCULATE_SALES_COST_END_POINT = "/calculate-sales-total";

    private SalesOrderEndpoint() {
    }
}
