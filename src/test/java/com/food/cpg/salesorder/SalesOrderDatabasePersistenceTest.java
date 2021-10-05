package com.food.cpg.salesorder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SalesOrderFactory.class)
public class SalesOrderDatabasePersistenceTest {

    private static final String TEST_ORDER_NUMBER = "SO-123456";
    private static final Integer TEST_MANUFACTURER_ID = 1;
    private static final Integer TEST_PACKAGE_ID = 1;
    private static final Integer TEST_ITEM_ID = 1;
    private static final String TEST_BUYER_DETAILS = "I am the buyer";
    private static final Double TEST_PACKAGE_COST = 200.0;
    private static final Double TEST_SHIPPING_COST = 150.0;
    private static final Double TEST_TAX = 10.0;
    private static final Double TEST_TOTAL_COST = 385.0;
    private static final boolean TEST_IS_FOR_CHARITY = true;
    private static final String GET_INSTANCE_METHOD = "instance";

    @Mock
    ICommonDatabaseOperation commonDatabaseOperation;

    @Mock
    Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    ResultSet resultSet;

    @Mock
    ISalesOrder salesOrder;

    @Mock
    SalesOrderFactory salesOrderFactory;

    @Mock
    SalesOrderStatus salesOrderStatus;

    @Before
    public void setUp() throws SQLException {
        when(commonDatabaseOperation.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    public void getAllOpenOrdersTest() throws Exception {
        PowerMockito.mockStatic(SalesOrderFactory.class);
        PowerMockito.doReturn(salesOrderFactory).when(SalesOrderFactory.class, GET_INSTANCE_METHOD);
        when(salesOrderFactory.makeSalesOrder()).thenReturn(salesOrder);

        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(1);
        placeholderValues.add(SalesOrderStatus.Status.OPEN.name());

        SalesOrderDatabasePersistence salesOrderDatabasePersistence = new SalesOrderDatabasePersistence(commonDatabaseOperation);

        salesOrderDatabasePersistence.getAllOpenOrders(1);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(3)).next();
        verify(resultSet, times(4)).getString(anyString());
        verify(resultSet, times(4)).getInt(anyString());
        verify(resultSet, times(2)).getDouble(anyString());
        verify(resultSet, times(2)).getTimestamp(anyString());
    }

    @Test
    public void getAllPackagedOrdersTest() throws Exception {
        PowerMockito.mockStatic(SalesOrderFactory.class);
        PowerMockito.doReturn(salesOrderFactory).when(SalesOrderFactory.class, GET_INSTANCE_METHOD);
        when(salesOrderFactory.makeSalesOrder()).thenReturn(salesOrder);

        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(1);
        placeholderValues.add(SalesOrderStatus.Status.PACKAGED.name());

        SalesOrderDatabasePersistence salesOrderDatabasePersistence = new SalesOrderDatabasePersistence(commonDatabaseOperation);

        salesOrderDatabasePersistence.getAllPackagedOrders(1);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(3)).next();
        verify(resultSet, times(4)).getString(anyString());
        verify(resultSet, times(4)).getInt(anyString());
        verify(resultSet, times(2)).getDouble(anyString());
        verify(resultSet, times(2)).getTimestamp(anyString());
    }

    @Test
    public void getAllShippedOrdersTest() throws Exception {
        PowerMockito.mockStatic(SalesOrderFactory.class);
        PowerMockito.doReturn(salesOrderFactory).when(SalesOrderFactory.class, GET_INSTANCE_METHOD);
        when(salesOrderFactory.makeSalesOrder()).thenReturn(salesOrder);

        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(1);
        placeholderValues.add(SalesOrderStatus.Status.SHIPPED.name());

        SalesOrderDatabasePersistence salesOrderDatabasePersistence = new SalesOrderDatabasePersistence(commonDatabaseOperation);

        salesOrderDatabasePersistence.getAllShippedOrders(1);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(3)).next();
        verify(resultSet, times(4)).getString(anyString());
        verify(resultSet, times(4)).getInt(anyString());
        verify(resultSet, times(2)).getDouble(anyString());
        verify(resultSet, times(2)).getTimestamp(anyString());
    }

    @Test
    public void getAllPaidOrdersTest() throws Exception {
        PowerMockito.mockStatic(SalesOrderFactory.class);
        PowerMockito.doReturn(salesOrderFactory).when(SalesOrderFactory.class, GET_INSTANCE_METHOD);
        when(salesOrderFactory.makeSalesOrder()).thenReturn(salesOrder);

        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(1);
        placeholderValues.add(SalesOrderStatus.Status.PAID.name());

        SalesOrderDatabasePersistence salesOrderDatabasePersistence = new SalesOrderDatabasePersistence(commonDatabaseOperation);

        salesOrderDatabasePersistence.getAllPaidOrders(1);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(3)).next();
        verify(resultSet, times(4)).getString(anyString());
        verify(resultSet, times(4)).getInt(anyString());
        verify(resultSet, times(2)).getDouble(anyString());
        verify(resultSet, times(2)).getTimestamp(anyString());
    }

    @Test
    public void loadTest() throws Exception {
        PowerMockito.mockStatic(SalesOrderFactory.class);
        PowerMockito.doReturn(salesOrderFactory).when(SalesOrderFactory.class, GET_INSTANCE_METHOD);
        when(salesOrderFactory.makeOrderStatus(anyString())).thenReturn(salesOrderStatus);

        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());
        when(salesOrder.getOrderNumber()).thenReturn(TEST_ORDER_NUMBER);

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(TEST_ORDER_NUMBER);

        SalesOrderDatabasePersistence salesOrderDatabasePersistence = new SalesOrderDatabasePersistence(commonDatabaseOperation);

        salesOrderDatabasePersistence.load(salesOrder);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(2)).getString(anyString());
        verify(resultSet, times(2)).getInt(anyString());
        verify(resultSet, times(1)).getDouble(anyString());
        verify(resultSet, times(1)).getTimestamp(anyString());
    }

    @Test
    public void saveTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());
        when(salesOrder.getOrderNumber()).thenReturn(TEST_ORDER_NUMBER);
        when(salesOrder.getItemId()).thenReturn(TEST_ITEM_ID);
        when(salesOrder.getPackageId()).thenReturn(TEST_PACKAGE_ID);
        when(salesOrder.getPackageCost()).thenReturn(TEST_PACKAGE_COST);
        when(salesOrder.getShippingCost()).thenReturn(TEST_SHIPPING_COST);
        when(salesOrder.getTax()).thenReturn(TEST_TAX);
        when(salesOrder.getTotalCost()).thenReturn(TEST_TOTAL_COST);
        when(salesOrder.getIsForCharity()).thenReturn(TEST_IS_FOR_CHARITY);
        when(salesOrder.getBuyerDetails()).thenReturn(TEST_BUYER_DETAILS);
        when(salesOrder.getManufacturerId()).thenReturn(TEST_MANUFACTURER_ID);

        SalesOrderDatabasePersistence salesOrderDatabasePersistence = new SalesOrderDatabasePersistence(commonDatabaseOperation);

        salesOrderDatabasePersistence.save(salesOrder);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
        verify(salesOrder, times(1)).getOrderNumber();
        verify(salesOrder, times(1)).getItemId();
        verify(salesOrder, times(1)).getPackageId();
        verify(salesOrder, times(1)).getPackageCost();
        verify(salesOrder, times(1)).getShippingCost();
        verify(salesOrder, times(1)).getTax();
        verify(salesOrder, times(1)).getTotalCost();
        verify(salesOrder, times(1)).getIsForCharity();
        verify(salesOrder, times(1)).getBuyerDetails();
        verify(salesOrder, times(1)).getManufacturerId();
    }

    @Test
    public void deleteTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());

        SalesOrderDatabasePersistence salesOrderDatabasePersistence = new SalesOrderDatabasePersistence(commonDatabaseOperation);

        salesOrderDatabasePersistence.delete(TEST_ORDER_NUMBER);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
    }
}
