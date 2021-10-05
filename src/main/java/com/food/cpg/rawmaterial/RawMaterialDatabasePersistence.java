package com.food.cpg.rawmaterial;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;

public class RawMaterialDatabasePersistence implements IRawMaterialPersistence {

    private final ICommonDatabaseOperation commonDatabaseOperation;

    public RawMaterialDatabasePersistence(ICommonDatabaseOperation commonDatabaseOperation) {
        this.commonDatabaseOperation = commonDatabaseOperation;
    }

    @Override
    public List<IRawMaterial> getAll(int manufacturerId) {
        List<IRawMaterial> rawMaterials = new ArrayList<>();

        String sql = RawMaterialDatabaseQuery.SELECT_ALL_RAW_MATERIALS;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(manufacturerId);

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        IRawMaterial rawMaterial = RawMaterialFactory.instance().makeRawMaterial();
                        loadRawMaterialDetailsFromResultSet(rs, rawMaterial);

                        rawMaterials.add(rawMaterial);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rawMaterials;
    }

    @Override
    public void load(IRawMaterial rawMaterial) {
        String sql = RawMaterialDatabaseQuery.LOAD_RAW_MATERIALS;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(rawMaterial.getId());

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        loadRawMaterialDetailsFromResultSet(rs, rawMaterial);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Integer save(IRawMaterial rawMaterial) {
        Integer rawMaterialId = null;
        String sql = RawMaterialDatabaseQuery.INSERT_RAW_MATERIALS;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(rawMaterial.getName());
        placeholderValues.add(rawMaterial.getVendorId());
        placeholderValues.add(rawMaterial.getUnitCost());
        placeholderValues.add(rawMaterial.getUnitMeasurement());
        placeholderValues.add(rawMaterial.getUnitMeasurementUOM());
        placeholderValues.add(rawMaterial.getReorderPointQuantity());
        placeholderValues.add(rawMaterial.getReorderPointQuantityUOM());
        placeholderValues.add(rawMaterial.getManufacturerId());

        try {
            rawMaterialId = commonDatabaseOperation.executeUpdateGetId(sql, placeholderValues);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rawMaterialId;
    }

    @Override
    public void update(IRawMaterial rawMaterial) {
        String sql = RawMaterialDatabaseQuery.UPDATE_RAW_MATERIALS;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(rawMaterial.getName());
        placeholderValues.add(rawMaterial.getVendorId());
        placeholderValues.add(rawMaterial.getUnitCost());
        placeholderValues.add(rawMaterial.getUnitMeasurement());
        placeholderValues.add(rawMaterial.getUnitMeasurementUOM());
        placeholderValues.add(rawMaterial.getReorderPointQuantity());
        placeholderValues.add(rawMaterial.getReorderPointQuantityUOM());
        placeholderValues.add(rawMaterial.getId());

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int rawMaterialId) {
        String sql = RawMaterialDatabaseQuery.DELETE_RAW_MATERIALS;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(rawMaterialId);

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadRawMaterialDetailsFromResultSet(ResultSet resultSet, IRawMaterial rawMaterial) throws SQLException {
        rawMaterial.setId(resultSet.getInt(RawMaterialDatabaseColumn.RAW_MATERIAL_ID));
        rawMaterial.setName(resultSet.getString(RawMaterialDatabaseColumn.RAW_MATERIAL_NAME));
        rawMaterial.setVendorId(resultSet.getInt(RawMaterialDatabaseColumn.VENDOR_ID));
        rawMaterial.setUnitCost(resultSet.getDouble(RawMaterialDatabaseColumn.UNIT_COST));
        rawMaterial.setUnitMeasurement(resultSet.getDouble(RawMaterialDatabaseColumn.UNIT_MEASUREMENT));
        rawMaterial.setUnitMeasurementUOM(resultSet.getString(RawMaterialDatabaseColumn.UNIT_MEASUREMENT_UOM));
        rawMaterial.setReorderPointQuantity(resultSet.getDouble(RawMaterialDatabaseColumn.RECORDER_POINT_QUANTITY));
        rawMaterial.setReorderPointQuantityUOM(resultSet.getString(RawMaterialDatabaseColumn.RECORDER_POINT_QUANTITY_UOM));
    }
}