package com.food.cpg.vendor;

public class VendorDatabaseQuery {

    public static final String SELECT_ALL_VENDOR = "select * from vendors where manufacturer_id = ?";
    public static final String LOAD_VENDOR = "select * from vendors where vendor_id = ?";
    public static final String INSERT_VENDOR = "insert into vendors (vendor_name, vendor_address, contact_person_name, contact_person_email, contact_person_phone, manufacturer_id) values (?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_VENDOR = "update vendors set vendor_name = ?, vendor_address = ?, contact_person_name = ?, contact_person_email = ?, contact_person_phone = ? where vendor_id = ?";
    public static final String DELETE_VENDOR = "delete from vendors where vendor_id = ?";

    private VendorDatabaseQuery() {
    }
}
