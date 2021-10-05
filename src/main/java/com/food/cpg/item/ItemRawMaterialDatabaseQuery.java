package com.food.cpg.item;

public class ItemRawMaterialDatabaseQuery {

    public static final String SAVE_ITEM_RAW_MATERIAL = "insert into item_raw_materials (item_id, raw_material_id, vendor_id, raw_material_quantity, raw_material_quantity_uom, raw_material_unit_cost, cost) values (?, ?, ?, ?,?,?,?)";
    public static final String DELETE_ITEM_RAW_MATERIAL = "delete from item_raw_materials where item_id = ?";
    public static final String LOAD_ITEM_RAW_MATERIAL_UNIT_COST = "select unit_cost from raw_materials where raw_material_id = ?";
    private ItemRawMaterialDatabaseQuery(){

    }
}
