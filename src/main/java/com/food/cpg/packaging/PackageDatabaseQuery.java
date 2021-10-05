package com.food.cpg.packaging;

public class PackageDatabaseQuery {

    public static final String SELECT_ALL_PACKAGE = "select * from packages where manufacturer_id = ?";
    public static final String LOAD_PACKAGE= "select * from packages where package_id = ?";
    public static final String INSERT_PACKAGE = "insert into packages (item_id, package_name, quantity, manufacturing_cost, wholesale_cost, retail_cost, manufacturer_id) values (?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_PACKAGE = "update packages set item_id = ?, package_name = ?, quantity = ?, manufacturing_cost = ?, wholesale_cost = ?, retail_cost = ? where package_id = ?";
    public static final String DELETE_PACKAGE = "delete from packages where package_id = ?";

    private PackageDatabaseQuery(){

    }
}
