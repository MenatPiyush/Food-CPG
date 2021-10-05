package com.food.cpg.item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;

public class ItemRawMaterialDatabasePersistence implements IItemRawMaterialPersistence {

    private final ICommonDatabaseOperation commonDatabaseOperation;

    public ItemRawMaterialDatabasePersistence(ICommonDatabaseOperation commonDatabaseOperation) {
        this.commonDatabaseOperation = commonDatabaseOperation;
    }

    @Override
    public void save(IItemRawMaterial itemRawMaterial) {

        String sql = ItemRawMaterialDatabaseQuery.SAVE_ITEM_RAW_MATERIAL;

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(itemRawMaterial.getItemId());
        placeholderValues.add(itemRawMaterial.getRawMaterialId());
        placeholderValues.add(itemRawMaterial.getVendorId());
        placeholderValues.add(itemRawMaterial.getRawMaterialQuantity());
        placeholderValues.add(itemRawMaterial.getRawMaterialQuantityUOM());
        placeholderValues.add(itemRawMaterial.getRawMaterialUnitCost());
        placeholderValues.add(itemRawMaterial.getCost());

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(int itemId) {

        String sql = ItemRawMaterialDatabaseQuery.DELETE_ITEM_RAW_MATERIAL;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(itemId);

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Double loadUnitCost(Integer rawMaterialId) {

        Double unitCost = 0.0;
        String sql = ItemRawMaterialDatabaseQuery.LOAD_ITEM_RAW_MATERIAL_UNIT_COST;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(rawMaterialId);

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        unitCost = resultSet.getDouble(ItemRawMaterialDatabaseColumn.UNIT_COST);

                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return unitCost;
    }
}
