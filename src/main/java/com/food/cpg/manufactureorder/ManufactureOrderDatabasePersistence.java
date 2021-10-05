package com.food.cpg.manufactureorder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;

public class ManufactureOrderDatabasePersistence implements IManufactureOrderPersistence {

    private final ICommonDatabaseOperation commonDatabaseOperation;

    public ManufactureOrderDatabasePersistence(ICommonDatabaseOperation commonDatabaseOperation) {
        this.commonDatabaseOperation = commonDatabaseOperation;
    }

    @Override
    public List<IManufactureOrder> getAllOpenOrders(int manufacturerId) {
        return getAllOrders(manufacturerId, ManufactureOrderStatus.Status.OPEN.name());
    }

    @Override
    public List<IManufactureOrder> getAllManufacturedOrders(int manufacturerId) {
        return getAllOrders(manufacturerId, ManufactureOrderStatus.Status.MANUFACTURED.name());
    }

    @Override
    public List<IManufactureOrder> getAllPackagedOrders(int manufacturerId) {
        return getAllOrders(manufacturerId, ManufactureOrderStatus.Status.PACKAGED.name());
    }

    @Override
    public List<IManufactureOrder> getAllStoredOrders(int manufacturerId) {
        return getAllOrders(manufacturerId, ManufactureOrderStatus.Status.STORED.name());
    }


    @Override
    public List<IManufactureOrder> getAllOrders(int manufacturerId, String orderStatus) {
        List<IManufactureOrder> manufactureOrders = new ArrayList<>();

        String sql = ManufactureOrderDatabaseQuery.SELECT_ALL_MANUFACTURE_ORDER;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(manufacturerId);
        placeholderValues.add(orderStatus);

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        IManufactureOrder manufactureOrder = ManufactureOrderFactory.instance().makeManufactureOrder();
                        loadManufactureOrderDetailsFromResultSet(rs, manufactureOrder);

                        manufactureOrders.add(manufactureOrder);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return manufactureOrders;

    }


    @Override
    public void changeStatus(String orderNumber, String orderStatus) {
        String sql = ManufactureOrderDatabaseQuery.CHANGE_MANUFACTURE_ORDER_STATUS;
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
    public void load(IManufactureOrder manufactureOrder) {
        String sql = ManufactureOrderDatabaseQuery.LOAD_MANUFACTURE_ORDER;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(manufactureOrder.getOrderNumber());

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        loadManufactureOrderDetailsFromResultSet(rs, manufactureOrder);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void save(IManufactureOrder manufactureOrder) {

        String sql = ManufactureOrderDatabaseQuery.INSERT_MANUFACTURE_ORDER;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(manufactureOrder.getOrderNumber());
        placeholderValues.add(manufactureOrder.getItemId());
        placeholderValues.add(manufactureOrder.getItemQuantity());
        placeholderValues.add(manufactureOrder.getItemQuantityUOM());
        placeholderValues.add(manufactureOrder.getManufacturingCost());
        placeholderValues.add(manufactureOrder.getTax());
        placeholderValues.add(manufactureOrder.getCost());
        placeholderValues.add(manufactureOrder.getManufacturerId());

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String orderNumber) {
        String sql = ManufactureOrderDatabaseQuery.DELETE_MANUFACTURE_ORDER;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(orderNumber);

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Double loadItemCost(int itemId) {
        Double itemCost = null;
        String sql = ManufactureOrderDatabaseQuery.LOAD_ITEM_COST;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(itemId);

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        itemCost = rs.getDouble(ManufactureOrderDatabaseColumn.ITEM_TOTAL_COST);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return itemCost;
    }

    private void loadManufactureOrderDetailsFromResultSet(ResultSet resultSet, IManufactureOrder manufactureOrder) throws SQLException {
        manufactureOrder.setOrderNumber(resultSet.getString(ManufactureOrderDatabaseColumn.ORDER_NUMBER));
        manufactureOrder.setItemId(resultSet.getInt(ManufactureOrderDatabaseColumn.ITEM_ID));
        manufactureOrder.setItemQuantity(resultSet.getDouble(ManufactureOrderDatabaseColumn.ITEM_QUANTITY));
        manufactureOrder.setItemQuantityUOM(resultSet.getString(ManufactureOrderDatabaseColumn.ITEM_QUANTITY_UOM));
        manufactureOrder.setManufacturingCost(resultSet.getDouble(ManufactureOrderDatabaseColumn.MANUFACTURING_COST));
        manufactureOrder.setCost(resultSet.getDouble(ManufactureOrderDatabaseColumn.TOTAL_COST));

        String orderStatus = resultSet.getString(ManufactureOrderDatabaseColumn.ORDER_STATUS);
        ManufactureOrderStatus manufactureOrderStatus = ManufactureOrderFactory.instance().makeOrderStatus(orderStatus);
        manufactureOrder.setManufactureOrderStatus(manufactureOrderStatus);

        manufactureOrder.setStatusChangeDate(resultSet.getTimestamp(ManufactureOrderDatabaseColumn.STATUS_CHANGE_DATE));
    }

}
