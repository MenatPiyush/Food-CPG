package com.food.cpg.manufactureorder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ManufactureOrderFactory.class)
public class ManufactureOrderDatabasePersistenceTest {

    private static final String TEST_ORDER_NUMBER = "MO-123456";
    private static final Integer TEST_MANUFACTURE_ID = 1;
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
    IManufactureOrder manufactureOrder;

    @Mock
    ManufactureOrderFactory manufactureOrderFactory;

    @Mock
    ManufactureOrderStatus manufactureOrderStatus;

    @Before
    public void setUp() throws SQLException {
        when(commonDatabaseOperation.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    public void getAllOpenOrdersTest() throws Exception {
        PowerMockito.mockStatic(ManufactureOrderFactory.class);
        PowerMockito.doReturn(manufactureOrderFactory).when(ManufactureOrderFactory.class, GET_INSTANCE_METHOD);
        when(manufactureOrderFactory.makeManufactureOrder()).thenReturn(manufactureOrder);

        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(1);
        placeholderValues.add(ManufactureOrderStatus.Status.OPEN.name());

        ManufactureOrderDatabasePersistence manufactureOrderDatabasePersistence = new ManufactureOrderDatabasePersistence(commonDatabaseOperation);

        manufactureOrderDatabasePersistence.getAllOpenOrders(TEST_MANUFACTURE_ID);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(3)).next();
        verify(resultSet, times(6)).getString(anyString());
        verify(resultSet, times(2)).getInt(anyString());
        verify(resultSet, times(6)).getDouble(anyString());
        verify(resultSet, times(2)).getTimestamp(anyString());
    }

    @Test
    public void getAllManufacturedOrdersTest() throws Exception {
        PowerMockito.mockStatic(ManufactureOrderFactory.class);
        PowerMockito.doReturn(manufactureOrderFactory).when(ManufactureOrderFactory.class, GET_INSTANCE_METHOD);
        when(manufactureOrderFactory.makeManufactureOrder()).thenReturn(manufactureOrder);

        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(1);
        placeholderValues.add(ManufactureOrderStatus.Status.MANUFACTURED.name());

        ManufactureOrderDatabasePersistence manufactureOrderDatabasePersistence = new ManufactureOrderDatabasePersistence(commonDatabaseOperation);

        manufactureOrderDatabasePersistence.getAllManufacturedOrders(TEST_MANUFACTURE_ID);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(3)).next();
        verify(resultSet, times(6)).getString(anyString());
        verify(resultSet, times(2)).getInt(anyString());
        verify(resultSet, times(6)).getDouble(anyString());
        verify(resultSet, times(2)).getTimestamp(anyString());
    }

    @Test
    public void getAllPackagedOrdersTest() throws Exception {
        PowerMockito.mockStatic(ManufactureOrderFactory.class);
        PowerMockito.doReturn(manufactureOrderFactory).when(ManufactureOrderFactory.class, GET_INSTANCE_METHOD);
        when(manufactureOrderFactory.makeManufactureOrder()).thenReturn(manufactureOrder);

        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(1);
        placeholderValues.add(ManufactureOrderStatus.Status.PACKAGED.name());

        ManufactureOrderDatabasePersistence manufactureOrderDatabasePersistence = new ManufactureOrderDatabasePersistence(commonDatabaseOperation);

        manufactureOrderDatabasePersistence.getAllPackagedOrders(TEST_MANUFACTURE_ID);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(3)).next();
        verify(resultSet, times(6)).getString(anyString());
        verify(resultSet, times(2)).getInt(anyString());
        verify(resultSet, times(6)).getDouble(anyString());
        verify(resultSet, times(2)).getTimestamp(anyString());
    }

    @Test
    public void getAllStoredOrdersTest() throws Exception {
        PowerMockito.mockStatic(ManufactureOrderFactory.class);
        PowerMockito.doReturn(manufactureOrderFactory).when(ManufactureOrderFactory.class, GET_INSTANCE_METHOD);
        when(manufactureOrderFactory.makeManufactureOrder()).thenReturn(manufactureOrder);

        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(1);
        placeholderValues.add(ManufactureOrderStatus.Status.STORED.name());

        ManufactureOrderDatabasePersistence manufactureOrderDatabasePersistence = new ManufactureOrderDatabasePersistence(commonDatabaseOperation);

        manufactureOrderDatabasePersistence.getAllStoredOrders(TEST_MANUFACTURE_ID);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(3)).next();
        verify(resultSet, times(6)).getString(anyString());
        verify(resultSet, times(2)).getInt(anyString());
        verify(resultSet, times(6)).getDouble(anyString());
        verify(resultSet, times(2)).getTimestamp(anyString());
    }

    @Test
    public void loadTest() throws Exception {
        PowerMockito.mockStatic(ManufactureOrderFactory.class);
        PowerMockito.doReturn(manufactureOrderFactory).when(ManufactureOrderFactory.class, GET_INSTANCE_METHOD);
        when(manufactureOrderFactory.makeOrderStatus(anyString())).thenReturn(manufactureOrderStatus);

        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());
        when(manufactureOrder.getOrderNumber()).thenReturn(TEST_ORDER_NUMBER);

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(TEST_ORDER_NUMBER);

        ManufactureOrderDatabasePersistence manufactureOrderDatabasePersistence = new ManufactureOrderDatabasePersistence(commonDatabaseOperation);

        manufactureOrderDatabasePersistence.load(manufactureOrder);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(3)).getString(anyString());
        verify(resultSet, times(1)).getInt(anyString());
        verify(resultSet, times(3)).getDouble(anyString());
        verify(resultSet, times(1)).getTimestamp(anyString());
    }

    @Test
    public void deleteTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());

        ManufactureOrderDatabasePersistence manufactureOrderDatabasePersistence = new ManufactureOrderDatabasePersistence(commonDatabaseOperation);

        manufactureOrderDatabasePersistence.delete(TEST_ORDER_NUMBER);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
    }


}
