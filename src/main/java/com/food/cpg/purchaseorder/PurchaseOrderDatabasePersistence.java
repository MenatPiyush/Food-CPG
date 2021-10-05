package com.food.cpg.purchaseorder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;

public class PurchaseOrderDatabasePersistence implements IPurchaseOrderPersistence {

    private final ICommonDatabaseOperation commonDatabaseOperation;

    public PurchaseOrderDatabasePersistence(ICommonDatabaseOperation commonDatabaseOperation) {
        this.commonDatabaseOperation = commonDatabaseOperation;
    }

    @Override
    public List<IPurchaseOrder> getOpenPurchaseOrder(int manufacturerId) {
        return getPurchaseOrders(manufacturerId, PurchaseOrderStatus.Status.OPEN.name());
    }

    @Override
    public List<IPurchaseOrder> getPlacedPurchaseOrder(int manufacturerId) {
        return getPurchaseOrders(manufacturerId, PurchaseOrderStatus.Status.PLACED.name());
    }

    @Override
    public List<IPurchaseOrder> getReceivedPurchaseOrder(int manufacturerId) {
        return getPurchaseOrders(manufacturerId, PurchaseOrderStatus.Status.RECEIVED.name());
    }

    @Override
    public List<IPurchaseOrder> getPaidPurchaseOrder(int manufacturerId) {
        return getPurchaseOrders(manufacturerId, PurchaseOrderStatus.Status.PAID.name());
    }

    @Override
    public void save(IPurchaseOrder purchaseOrder) {
        String sql = PurchaseOrderDatabaseQuery.INSERT_PURCHASE_ORDER;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(purchaseOrder.getOrderNumber());
        placeholderValues.add(purchaseOrder.getVendorId());
        placeholderValues.add(purchaseOrder.getTotalCost());
        placeholderValues.add(purchaseOrder.getManufacturerId());

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void changeStatus(String orderNumber, String orderStatus) {
        String sql = PurchaseOrderDatabaseQuery.CHANGE_PURCHASE_ORDER_STATUS;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(orderStatus);
        placeholderValues.add(orderNumber);
        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(IPurchaseOrder purchaseOrder) {
        String sql = PurchaseOrderDatabaseQuery.DELETE_PURCHASE_ORDER;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(purchaseOrder.getOrderNumber());
        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void load(IPurchaseOrder purchaseOrder) {
        String sql = PurchaseOrderDatabaseQuery.LOAD_PURCHASE_ORDER;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(purchaseOrder.getOrderNumber());

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        loadPurchaseOrderDetailsFromResultSet(rs, purchaseOrder);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<IPurchaseOrder> getPurchaseOrders(int manufacturerId, String orderStatus) {
        List<IPurchaseOrder> purchaseOrders = new ArrayList<>();

        String sql = PurchaseOrderDatabaseQuery.SELECT_ALL_PURCHASE_ORDER;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(manufacturerId);
        placeholderValues.add(orderStatus);

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        IPurchaseOrder purchaseOrder = PurchaseOrderFactory.instance().makePurchaseOrder();
                        loadPurchaseOrderDetailsFromResultSet(rs, purchaseOrder);

                        purchaseOrders.add(purchaseOrder);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return purchaseOrders;
    }

    private void loadPurchaseOrderDetailsFromResultSet(ResultSet resultSet, IPurchaseOrder purchaseOrder) throws SQLException {
        purchaseOrder.setOrderNumber(resultSet.getString(PurchaseOrderDatabaseColumn.ORDER_NUMBER));
        purchaseOrder.setTotalCost(resultSet.getDouble(PurchaseOrderDatabaseColumn.TOTAL_COST));
        purchaseOrder.setStatusChangeDate(resultSet.getTimestamp(PurchaseOrderDatabaseColumn.STATUS_CHANGE_DATE));
        String orderStatus = resultSet.getString(PurchaseOrderDatabaseColumn.ORDER_STATUS);
        PurchaseOrderStatus purchaseOrderStatus = PurchaseOrderFactory.instance().makeOrderStatus(orderStatus);
        purchaseOrder.setPurchaseOrderStatus(purchaseOrderStatus);
    }
}
