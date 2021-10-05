package com.food.cpg.item;

public class ItemDatabaseQuery {

    public static final String SELECT_ALL_ITEM = "select * from items where manufacturer_id = ?";
    public static final String LOAD_ITEM = "select * from items where item_id = ?";
    public static final String SAVE_ITEM = "insert into items (item_name, item_cooking_cost, item_total_cost, manufacturer_id) values (?, ?, ?, ?)";
    public static final String DELETE_ITEM = "delete from items where item_id = ?";

    private ItemDatabaseQuery() {

    }
}
