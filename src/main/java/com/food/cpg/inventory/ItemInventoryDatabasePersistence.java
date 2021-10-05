package com.food.cpg.inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;

public class ItemInventoryDatabasePersistence implements IItemInventoryPersistence {

    private final ICommonDatabaseOperation commonDatabaseOperation;

    public ItemInventoryDatabasePersistence(ICommonDatabaseOperation commonDatabaseOperation) {
        this.commonDatabaseOperation = commonDatabaseOperation;
    }

    @Override
    public List<ItemInventory> getAll(int manufacturerId) {

        List<ItemInventory> itemInventoryList = new ArrayList<>();

        String sql = "select ii.item_id, ii.quantity, i.item_name from item_inventory ii join items i on ii.item_id = i.item_id where i.manufacturer_id = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(manufacturerId);

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        ItemInventory itemInventory = new ItemInventory();
                        loadItemInventoryDetailsFromResultSet(rs, itemInventory);
                        itemInventoryList.add(itemInventory);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return itemInventoryList;
    }

    @Override
    public void save(ItemInventory itemInventory) {
        String sql = "insert into item_inventory (item_id, quantity) " + "values (?, ?)";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(itemInventory.getItemId());
        placeholderValues.add(itemInventory.getItemQuantity());

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadItemInventoryDetailsFromResultSet(ResultSet resultSet, ItemInventory itemInventory) throws SQLException {
        itemInventory.setItemId(resultSet.getInt("item_id"));
        itemInventory.setItemQuantity(resultSet.getDouble("quantity"));
        itemInventory.setItemName(resultSet.getString("item_name"));
    }

    public void increaseQuantity(ItemInventory itemInventory) {
        String sql = "update item_inventory set quantity = quantity + ? where item_id = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(itemInventory.getItemQuantity());
        placeholderValues.add(itemInventory.getItemId());

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void decreaseQuantity(ItemInventory itemInventory) {
        String sql = "update item_inventory set quantity = quantity - ? where item_id = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(itemInventory.getItemQuantity());
        placeholderValues.add(itemInventory.getItemId());

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
