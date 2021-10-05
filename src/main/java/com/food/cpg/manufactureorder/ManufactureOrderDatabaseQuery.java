package com.food.cpg.manufactureorder;

public class ManufactureOrderDatabaseQuery {

    public static final String SELECT_ALL_MANUFACTURE_ORDER = "select * from manufacture_orders where manufacturer_id = ? and order_status = ?";
    public static final String LOAD_MANUFACTURE_ORDER = "select * from manufacture_orders where order_number = ?";
    public static final String INSERT_MANUFACTURE_ORDER = "insert into manufacture_orders (order_number, item_id, quantity, quantity_uom, manufacturing_cost, tax, cost, manufacturer_id) values ( ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String CHANGE_MANUFACTURE_ORDER_STATUS = "update manufacture_orders set order_status = ? where order_number = ?";
    public static final String DELETE_MANUFACTURE_ORDER = "delete from manufacture_orders where order_number = ?";
    public static final String LOAD_ITEM_COST = "select item_total_cost from items where item_id = ?";

    private ManufactureOrderDatabaseQuery() {
    }
}
