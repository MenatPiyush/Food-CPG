package com.food.cpg.inventory;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(InventoryFactory.class)
public class RawMaterialInventoryDatabasePersistenceTest {

    private static final String GET_INSTANCE_METHOD = "instance";
    private static final Integer TEST_RAW_MATERIAL_ID = 1;
    private static final Double TEST_RAW_MATERIAL_QUANTITY = 10.0;

    @Mock
    ICommonDatabaseOperation commonDatabaseOperation;

    @Mock
    Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    ResultSet resultSet;

    @Mock
    IRawMaterialInventory rawMaterialInventory;

    @Mock
    InventoryFactory inventoryFactory;

    @Before
    public void setUp() throws SQLException {
        when(commonDatabaseOperation.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    public void getAllTest() throws Exception {
        PowerMockito.mockStatic(InventoryFactory.class);
        PowerMockito.doReturn(inventoryFactory).when(InventoryFactory.class, GET_INSTANCE_METHOD);
        when(inventoryFactory.makeRawMaterialInventory()).thenReturn(rawMaterialInventory);
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(any(), anyList());

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(1);

        RawMaterialInventoryDatabasePersistence rawMaterialInventoryDatabasePersistence = new RawMaterialInventoryDatabasePersistence(commonDatabaseOperation);

        rawMaterialInventoryDatabasePersistence.getAll(1);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(3)).next();
        verify(resultSet, times(4)).getString(anyString());
        verify(resultSet, times(2)).getInt(anyString());
        verify(resultSet, times(2)).getDouble(anyString());
    }

    @Test
    public void saveTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());

        RawMaterialInventoryDatabasePersistence rawMaterialInventoryDatabasePersistence = new RawMaterialInventoryDatabasePersistence(commonDatabaseOperation);

        rawMaterialInventoryDatabasePersistence.save(TEST_RAW_MATERIAL_ID);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
    }

    @Test
    public void increaseQuantityTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());
        when(rawMaterialInventory.getRawMaterialId()).thenReturn(TEST_RAW_MATERIAL_ID);
        when(rawMaterialInventory.getRawMaterialQuantity()).thenReturn(TEST_RAW_MATERIAL_QUANTITY);

        RawMaterialInventoryDatabasePersistence rawMaterialInventoryDatabasePersistence = new RawMaterialInventoryDatabasePersistence(commonDatabaseOperation);

        rawMaterialInventoryDatabasePersistence.increaseQuantity(rawMaterialInventory);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
        verify(rawMaterialInventory, times(1)).getRawMaterialId();
        verify(rawMaterialInventory, times(1)).getRawMaterialQuantity();
    }

    @Test
    public void decreaseQuantityTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());
        when(rawMaterialInventory.getRawMaterialId()).thenReturn(TEST_RAW_MATERIAL_ID);
        when(rawMaterialInventory.getRawMaterialQuantity()).thenReturn(TEST_RAW_MATERIAL_QUANTITY);

        RawMaterialInventoryDatabasePersistence rawMaterialInventoryDatabasePersistence = new RawMaterialInventoryDatabasePersistence(commonDatabaseOperation);

        rawMaterialInventoryDatabasePersistence.decreaseQuantity(rawMaterialInventory);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
        verify(rawMaterialInventory, times(1)).getRawMaterialId();
        verify(rawMaterialInventory, times(1)).getRawMaterialQuantity();
    }
}
