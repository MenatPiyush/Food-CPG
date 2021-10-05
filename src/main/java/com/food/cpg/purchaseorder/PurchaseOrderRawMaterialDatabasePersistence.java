package com.food.cpg.purchaseorder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;

public class PurchaseOrderRawMaterialDatabasePersistence implements IPurchaseOrderRawMaterialPersistence {

    private final ICommonDatabaseOperation commonDatabaseOperation;

    public PurchaseOrderRawMaterialDatabasePersistence(ICommonDatabaseOperation commonDatabaseOperation) {
        this.commonDatabaseOperation = commonDatabaseOperation;
    }

    @Override
    public void save(IPurchaseOrderRawMaterial purchaseOrderRawMaterial) {

        String sql = PurchaseOrderDatabaseQuery.INSERT_PURCHASE_ORDER_RAW_MATERIAL;

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(purchaseOrderRawMaterial.getPurchaseOrderNumber());
        placeholderValues.add(purchaseOrderRawMaterial.getRawMaterialId());
        placeholderValues.add(purchaseOrderRawMaterial.getRawMaterialQuantity());
        placeholderValues.add(purchaseOrderRawMaterial.getRawMaterialQuantityUOM());

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PurchaseOrderRawMaterial> getPurchaseOrderItemRawMaterial(int itemId) {
        List<PurchaseOrderRawMaterial> purchaseOrderItemRawMaterials = new ArrayList<>();

        String sql = PurchaseOrderDatabaseQuery.SELECT_ALL_PO_RAW_MATERIAL_BY_ITEM;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(itemId);
        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        PurchaseOrderRawMaterial purchaseOrderRawMaterial = PurchaseOrderFactory.instance().makePurchaseOrderRawMaterial();

                        loadPurchaseOrderRawMaterialDetailsFromItemResultSet(rs, purchaseOrderRawMaterial);

                        purchaseOrderItemRawMaterials.add(purchaseOrderRawMaterial);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return purchaseOrderItemRawMaterials;
    }

    @Override
    public List<PurchaseOrderRawMaterial> getPurchaseOrderRawMaterials(String orderNumber) {
        List<PurchaseOrderRawMaterial> purchaseOrderRawMaterials = new ArrayList<>();

        String sql = PurchaseOrderDatabaseQuery.SELECT_ALL_PO_RAW_MATERIAL;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(orderNumber);

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        PurchaseOrderRawMaterial purchaseOrderRawMaterial = PurchaseOrderFactory.instance().makePurchaseOrderRawMaterial();

                        loadPurchaseOrderRawMaterialDetailsFromResultSet(rs, purchaseOrderRawMaterial);

                        purchaseOrderRawMaterials.add(purchaseOrderRawMaterial);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return purchaseOrderRawMaterials;
    }

    @Override
    public void delete(String purchaseOrderNumber) {

        String sql = PurchaseOrderDatabaseQuery.DELETE_PURCHASE_ORDER_RAW_MATERIAL;

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(purchaseOrderNumber);
        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadPurchaseOrderRawMaterialDetailsFromItemResultSet(ResultSet resultSet, IPurchaseOrderRawMaterial purchaseOrderRawMaterial) throws SQLException {
        purchaseOrderRawMaterial.setRawMaterialId(resultSet.getInt(PurchaseOrderDatabaseColumn.PO_RAW_MATERIAL_ID));
        purchaseOrderRawMaterial.setRawMaterialQuantity(resultSet.getDouble(PurchaseOrderDatabaseColumn.PO_RAW_MATERIAL_QUANTITY_BY_IEM));
        purchaseOrderRawMaterial.setRawMaterialQuantityUOM(resultSet.getString(PurchaseOrderDatabaseColumn.PO_RAW_MATERIAL_UOM_BY_ITEM));
    }

    private void loadPurchaseOrderRawMaterialDetailsFromResultSet(ResultSet resultSet, IPurchaseOrderRawMaterial purchaseOrderRawMaterial) throws SQLException {
        purchaseOrderRawMaterial.setRawMaterialId(resultSet.getInt(PurchaseOrderDatabaseColumn.PO_RAW_MATERIAL_ID));
        purchaseOrderRawMaterial.setRawMaterialQuantity(resultSet.getDouble(PurchaseOrderDatabaseColumn.PO_RAW_MATERIAL_QUANTITY));
        purchaseOrderRawMaterial.setRawMaterialQuantityUOM(resultSet.getString(PurchaseOrderDatabaseColumn.PO_RAW_MATERIAL_UOM));
    }
}
