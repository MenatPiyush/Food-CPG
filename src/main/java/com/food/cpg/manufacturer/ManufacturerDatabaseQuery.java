package com.food.cpg.manufacturer;

public class ManufacturerDatabaseQuery {

    public static final String SELECT_ALL_MANUFACTURER = "select * from manufacturer";
    public static final String LOAD_MANUFACTURER = "select * from manufacturer where manufacturer_email = ?";
    public static final String INSERT_MANUFACTURER = "insert into manufacturer (manufacturer_company_name, manufacturer_email, manufacturer_contact, manufacturer_address) values (?, ?, ?, ?)";
    public static final String CREATE_MANUFACTURER_LOGIN_ACCOUNT = "insert into users (email, password) values(?,?)";

    private ManufacturerDatabaseQuery() {
    }
}
