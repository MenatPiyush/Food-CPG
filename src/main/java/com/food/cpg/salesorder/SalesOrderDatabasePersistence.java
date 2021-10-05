package com.food.cpg.salesorder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;

public class SalesOrderDatabasePersistence implements ISalesOrderPersistence {

    private final ICommonDatabaseOperation commonDatabaseOperation;

    public SalesOrderDatabasePersistence(ICommonDatabaseOperation commonDatabaseOperation) {
        this.commonDatabaseOperation = commonDatabaseOperation;
    }

    @Override
    public List<ISalesOrder> getAllOpenOrders(int manufacturerId) {
        return getAllOrders(manufacturerId, SalesOrderStatus.Status.OPEN.name());
    }

    @Override
    public List<ISalesOrder> getAllPackagedOrders(int manufacturerId) {
        return getAllOrders(manufacturerId, SalesOrderStatus.Status.PACKAGED.name());
    }

    @Override
    public List<ISalesOrder> getAllShippedOrders(int manufacturerId) {
        return getAllOrders(manufacturerId, SalesOrderStatus.Status.SHIPPED.name());
    }

    @Override
    public List<ISalesOrder> getAllPaidOrders(int manufacturerId) {
        return getAllOrders(manufacturerId, SalesOrderStatus.Status.PAID.name());
    }

    @Override
    public void load(ISalesOrder salesOrder) {
        String sql = SalesOrderDatabaseQuery.LOAD_SALES_ORDER;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(salesOrder.getOrderNumber());

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        loadSalesOrderDetailsFromResultSet(rs, salesOrder);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(ISalesOrder salesOrder) {

        String sql = SalesOrderDatabaseQuery.INSERT_SALES_ORDER;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(salesOrder.getOrderNumber());
        placeholderValues.add(salesOrder.getItemId());
        placeholderValues.add(salesOrder.getPackageId());
        placeholderValues.add(salesOrder.getPackageCost());
        placeholderValues.add(salesOrder.getShippingCost());
        placeholderValues.add(salesOrder.getTax());
        placeholderValues.add(salesOrder.getTotalCost());
        placeholderValues.add(salesOrder.getIsForCharity());
        placeholderValues.add(salesOrder.getBuyerDetails());
        placeholderValues.add(salesOrder.getManufacturerId());

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String orderNumber) {
        String sql = SalesOrderDatabaseQuery.DELETE_SALES_ORDER;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(orderNumber);

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void changeStatus(String orderNumber, String orderStatus) {
        String sql = SalesOrderDatabaseQuery.CHANGE_SALES_ORDER_STATUS;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(orderStatus);
        placeholderValues.add(orderNumber);

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<ISalesOrder> getAllOrders(int manufacturerId, String orderStatus) {
        List<ISalesOrder> salesOrders = new ArrayList<>();

        String sql = SalesOrderDatabaseQuery.SELECT_ALL_SALES_ORDER;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(manufacturerId);
        placeholderValues.add(orderStatus);

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        ISalesOrder salesOrder = SalesOrderFactory.instance().makeSalesOrder();
                        loadSalesOrderDetailsFromResultSet(rs, salesOrder);

                        salesOrders.add(salesOrder);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return salesOrders;
    }


    private void loadSalesOrderDetailsFromResultSet(ResultSet resultSet, ISalesOrder salesOrder) throws SQLException {
        salesOrder.setOrderNumber(resultSet.getString(SalesOrderDatabaseColumn.ORDER_NUMBER));
        salesOrder.setItemId(resultSet.getInt(SalesOrderDatabaseColumn.ITEM_ID));
        salesOrder.setPackageId(resultSet.getInt(SalesOrderDatabaseColumn.PACKAGE_ID));
        salesOrder.setTotalCost(resultSet.getDouble(SalesOrderDatabaseColumn.TOTAL_COST));
        salesOrder.setIsForCharity(resultSet.getBoolean(SalesOrderDatabaseColumn.IS_FOR_CHARITY));

        String orderStatus = resultSet.getString(SalesOrderDatabaseColumn.ORDER_STATUS);
        SalesOrderStatus salesOrderStatus = SalesOrderFactory.instance().makeOrderStatus(orderStatus);
        salesOrder.setSalesOrderStatus(salesOrderStatus);

        salesOrder.setStatusChangeDate(resultSet.getTimestamp(SalesOrderDatabaseColumn.STATUS_CHANGE_DATE));
    }
}