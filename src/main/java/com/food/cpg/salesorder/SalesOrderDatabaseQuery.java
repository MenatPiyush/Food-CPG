package com.food.cpg.salesorder;

public class SalesOrderDatabaseQuery {

    public static final String SELECT_ALL_SALES_ORDER = "select * from sales_orders where manufacturer_id = ? and order_status = ?";
    public static final String LOAD_SALES_ORDER = "select * from sales_orders where order_number = ?";
    public static final String INSERT_SALES_ORDER = "insert into sales_orders (order_number, item_id, package_id, package_cost, shipping_cost, tax, total_cost, is_for_charity, buyer_details, manufacturer_id) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String CHANGE_SALES_ORDER_STATUS = "update sales_orders set order_status = ? where order_number = ?";
    public static final String DELETE_SALES_ORDER = "delete from sales_orders where order_number = ?";

    private SalesOrderDatabaseQuery() {
    }
}
