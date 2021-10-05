package com.food.cpg.item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;

public class ItemDatabasePersistence implements IItemPersistence {

    private final ICommonDatabaseOperation commonDatabaseOperation;

    public ItemDatabasePersistence(ICommonDatabaseOperation commonDatabaseOperation) {
        this.commonDatabaseOperation = commonDatabaseOperation;
    }

    @Override
    public List<IItem> getAll(int manufacturerId) {

        List<IItem> itemList = new ArrayList<>();

        String sql = ItemDatabaseQuery.SELECT_ALL_ITEM;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(manufacturerId);

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        IItem item = ItemFactory.instance().makeItem();
                        loadItemDetailsFromResultSet(rs, item);
                        itemList.add(item);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return itemList;
    }

    @Override
    public void load(IItem item) {

        String sql = ItemDatabaseQuery.LOAD_ITEM;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(item.getId());

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        loadItemDetailsFromResultSet(rs, item);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer save(IItem item) {
        Integer itemId = null;
        String sql = ItemDatabaseQuery.SAVE_ITEM;
        List<Object> placeholderValues = new ArrayList<>();
        List<Object> placeholderValuesInventory = new ArrayList<>();
        placeholderValues.add(item.getName());
        placeholderValues.add(item.getCookingCost());
        placeholderValues.add(item.getTotalCost());
        placeholderValues.add(item.getManufacturerId());

        try {
            itemId = commonDatabaseOperation.executeUpdateGetId(sql, placeholderValues);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return itemId;
    }

    @Override
    public void delete(int itemId) {

        String sql = ItemDatabaseQuery.DELETE_ITEM;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(itemId);
        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadItemDetailsFromResultSet(ResultSet resultSet, IItem item) throws SQLException {
        item.setId(resultSet.getInt(ItemDatabaseColumn.ITEM_ID));
        item.setName(resultSet.getString(ItemDatabaseColumn.ITEM_NAME));
        item.setCookingCost(resultSet.getDouble(ItemDatabaseColumn.ITEM_COOKING_COST));
        item.setTotalCost(resultSet.getDouble(ItemDatabaseColumn.ITEM_TOTAL_COST));
    }
}
