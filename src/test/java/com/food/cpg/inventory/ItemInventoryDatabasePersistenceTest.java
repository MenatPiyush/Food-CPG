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
import org.mockito.junit.MockitoJUnitRunner;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ItemInventoryDatabasePersistenceTest {

    private static final Integer TEST_ITEM_ID = 10;
    private static final Double TEST_ITEM_QUANTITY = 2.0;
    private static final Double TEST_QUANTITY = 0.0;

    @Mock
    ICommonDatabaseOperation commonDatabaseOperation;

    @Mock
    Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    ResultSet resultSet;

    @Mock
    ItemInventory itemInventory;

    @Before
    public void setUp() throws SQLException {
        when(commonDatabaseOperation.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    public void getAllTest() throws SQLException {
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(1);

        ItemInventoryDatabasePersistence itemInventoryDatabasePersistence = new ItemInventoryDatabasePersistence(commonDatabaseOperation);

        itemInventoryDatabasePersistence.getAll(1);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(3)).next();
        verify(resultSet, times(2)).getString(anyString());
        verify(resultSet, times(2)).getInt(anyString());
        verify(resultSet, times(2)).getDouble(anyString());
    }


    @Test
    public void increaseQuantityTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());
        when(itemInventory.getItemId()).thenReturn(TEST_ITEM_ID);
        when(itemInventory.getItemQuantity()).thenReturn(TEST_ITEM_QUANTITY);

        ItemInventoryDatabasePersistence itemInventoryDatabasePersistence = new ItemInventoryDatabasePersistence(commonDatabaseOperation);

        itemInventoryDatabasePersistence.increaseQuantity(itemInventory);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
        verify(itemInventory, times(1)).getItemId();
        verify(itemInventory, times(1)).getItemQuantity();
    }

    @Test
    public void decreaseQuantityTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());
        when(itemInventory.getItemId()).thenReturn(TEST_ITEM_ID);
        when(itemInventory.getItemQuantity()).thenReturn(TEST_ITEM_QUANTITY);

        ItemInventoryDatabasePersistence itemInventoryDatabasePersistence = new ItemInventoryDatabasePersistence(commonDatabaseOperation);

        itemInventoryDatabasePersistence.decreaseQuantity(itemInventory);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
        verify(itemInventory, times(1)).getItemId();
        verify(itemInventory, times(1)).getItemQuantity();
    }

    @Test
    public void saveTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());
        when(itemInventory.getItemId()).thenReturn(TEST_ITEM_ID);
        when(itemInventory.getItemQuantity()).thenReturn(TEST_QUANTITY);

        ItemInventoryDatabasePersistence itemInventoryDatabasePersistence = new ItemInventoryDatabasePersistence(commonDatabaseOperation);

        itemInventoryDatabasePersistence.save(itemInventory);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
        verify(itemInventory, times(1)).getItemId();
        verify(itemInventory, times(1)).getItemQuantity();
    }

}
