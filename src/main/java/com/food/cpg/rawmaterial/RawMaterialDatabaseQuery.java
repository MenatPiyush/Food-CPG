package com.food.cpg.rawmaterial;

public class RawMaterialDatabaseQuery {

    public static final String SELECT_ALL_RAW_MATERIALS = "select * from raw_materials where manufacturer_id = ?";
    public static final String LOAD_RAW_MATERIALS = "select * from raw_materials where raw_material_id = ?";
    public static final String INSERT_RAW_MATERIALS = "insert into raw_materials (raw_material_name, vendor_id, unit_cost, unit_measurement, unit_measurement_uom, reorder_point_quantity, reorder_point_quantity_uom, manufacturer_id) values (?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_RAW_MATERIALS = "update raw_materials set raw_material_name = ?, vendor_id = ?, unit_cost = ?, unit_measurement = ?, unit_measurement_uom = ?, reorder_point_quantity = ?, reorder_point_quantity_uom = ? where raw_material_id = ?";
    public static final String DELETE_RAW_MATERIALS = "delete from raw_materials where raw_material_id = ?";

    private RawMaterialDatabaseQuery() {
    }
}
