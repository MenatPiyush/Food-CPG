package com.food.cpg.purchaseorder;

public class PurchaseOrderDatabaseQuery {

    public static final String SELECT_ALL_PURCHASE_ORDER = "select * from purchase_orders where manufacturer_id = ? and order_status = ?";
    public static final String LOAD_PURCHASE_ORDER = "select * from purchase_orders where order_number = ?";
    public static final String INSERT_PURCHASE_ORDER = "insert into purchase_orders (order_number, vendor_id, total_cost, manufacturer_id) values (?, ?, ?, ?)";
    public static final String CHANGE_PURCHASE_ORDER_STATUS = "update purchase_orders set order_status = ?, status_change_date = current_timestamp() where order_number = ?";
    public static final String DELETE_PURCHASE_ORDER = "delete from purchase_orders where order_number = ?";

    public static final String INSERT_PURCHASE_ORDER_RAW_MATERIAL = "insert into purchase_order_raw_materials (purchase_order_number, raw_material_id, quantity, quantity_uom) values (?, ?, ?, ?)";
    public static final String SELECT_ALL_PO_RAW_MATERIAL_BY_ITEM = "select * from item_raw_materials where item_id = ?";
    public static final String SELECT_ALL_PO_RAW_MATERIAL = "select * from purchase_order_raw_materials where purchase_order_number = ?";
    public static final String DELETE_PURCHASE_ORDER_RAW_MATERIAL = "delete from purchase_order_raw_materials where purchase_order_number = ?";

    private PurchaseOrderDatabaseQuery() {
    }
}
