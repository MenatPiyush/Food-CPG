package com.food.cpg.inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;

public class RawMaterialInventoryDatabasePersistence implements IRawMaterialInventoryPersistence {

    private final ICommonDatabaseOperation commonDatabaseOperation;

    public RawMaterialInventoryDatabasePersistence(ICommonDatabaseOperation commonDatabaseOperation) {
        this.commonDatabaseOperation = commonDatabaseOperation;
    }

    @Override
    public List<IRawMaterialInventory> getAll(int manufacturerId) {

        List<IRawMaterialInventory> rawMaterialInventories = new ArrayList<>();

        String sql = "select rmi.raw_material_id, rmi.quantity, rmi.quantity_uom, rm.raw_material_name from raw_material_inventory rmi join raw_materials rm on rmi.raw_material_id = rm.raw_material_id where rm.manufacturer_id = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(manufacturerId);

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        IRawMaterialInventory rawMaterialInventory = InventoryFactory.instance().makeRawMaterialInventory();
                        loadRawMaterialInventoryDetailsFromResultSet(rs, rawMaterialInventory);
                        rawMaterialInventories.add(rawMaterialInventory);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rawMaterialInventories;
    }

    @Override
    public void save(int rawMaterialId) {
        String sql = "insert into raw_material_inventory (raw_material_id) values (?)";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(rawMaterialId);

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void increaseQuantity(IRawMaterialInventory rawMaterialInventory) {
        String sql = "update raw_material_inventory set quantity = quantity + ? where raw_material_id = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(rawMaterialInventory.getRawMaterialQuantity());
        placeholderValues.add(rawMaterialInventory.getRawMaterialId());

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void decreaseQuantity(IRawMaterialInventory rawMaterialInventory) {
        String sql = "update raw_material_inventory set quantity = quantity - ? where raw_material_id = ?";
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(rawMaterialInventory.getRawMaterialQuantity());
        placeholderValues.add(rawMaterialInventory.getRawMaterialId());

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadRawMaterialInventoryDetailsFromResultSet(ResultSet resultSet, IRawMaterialInventory rawMaterialInventory) throws SQLException {
        rawMaterialInventory.setRawMaterialId(resultSet.getInt("raw_material_id"));
        rawMaterialInventory.setRawMaterialQuantity(resultSet.getDouble("quantity"));
        rawMaterialInventory.setRawMaterialQuantityUOM(resultSet.getString("quantity_uom"));
        rawMaterialInventory.setRawMaterialName(resultSet.getString("raw_material_name"));

    }
}
