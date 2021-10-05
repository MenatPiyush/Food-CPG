package com.food.cpg.item;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ItemRawMaterialDatabasePersistenceTest {

    private static final Integer TEST_ITEM_ID = 1;
    private static final Integer TEST_ITEM_RAW_MATERIAL_ID = 10;
    private static final Integer TEST_ITEM_RAW_MATERIAL_VENDOR_ID = 1;
    private static final Double TEST_ITEM_RAW_MATERIAL_QUANTITY = 10.0;
    private static final String TEST_ITEM_RAW_MATERIAL_UOM = "g";
    private static final Double TEST_ITEM_RAW_MATERIAL_UNIT_COST = 3.0;
    private static final Double TEST_ITEM_RAW_MATERIAL_TOTAL_COST = 30.0;

    @Mock
    Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    ResultSet resultSet;

    @Mock
    ICommonDatabaseOperation commonDatabaseOperation;

    @Mock
    IItemRawMaterial itemRawMaterial;

    @Before
    public void setUp() throws SQLException {
        when(commonDatabaseOperation.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }


    @Test
    public void saveTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());
        when(itemRawMaterial.getItemId()).thenReturn(TEST_ITEM_ID);
        when(itemRawMaterial.getRawMaterialId()).thenReturn(TEST_ITEM_RAW_MATERIAL_ID);
        when(itemRawMaterial.getVendorId()).thenReturn(TEST_ITEM_RAW_MATERIAL_VENDOR_ID);
        when(itemRawMaterial.getRawMaterialQuantity()).thenReturn(TEST_ITEM_RAW_MATERIAL_QUANTITY);
        when(itemRawMaterial.getRawMaterialQuantityUOM()).thenReturn(TEST_ITEM_RAW_MATERIAL_UOM);
        when(itemRawMaterial.getRawMaterialUnitCost()).thenReturn(TEST_ITEM_RAW_MATERIAL_UNIT_COST);
        when(itemRawMaterial.getCost()).thenReturn(TEST_ITEM_RAW_MATERIAL_TOTAL_COST);

        ItemRawMaterialDatabasePersistence itemRawMaterialDatabasePersistence = new ItemRawMaterialDatabasePersistence(commonDatabaseOperation);

        itemRawMaterialDatabasePersistence.save(itemRawMaterial);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
        verify(itemRawMaterial, times(1)).getItemId();
        verify(itemRawMaterial, times(1)).getRawMaterialId();
        verify(itemRawMaterial, times(1)).getVendorId();
        verify(itemRawMaterial, times(1)).getRawMaterialQuantity();
        verify(itemRawMaterial, times(1)).getRawMaterialQuantityUOM();
        verify(itemRawMaterial, times(1)).getRawMaterialUnitCost();
        verify(itemRawMaterial, times(1)).getCost();
    }

    @Test
    public void deleteTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());

        ItemRawMaterialDatabasePersistence itemRawMaterialDatabasePersistence = new ItemRawMaterialDatabasePersistence(commonDatabaseOperation);

        itemRawMaterialDatabasePersistence.delete(1);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
    }

    @Test
    public void loadUnitCostTest() throws SQLException {
        Integer rawMaterialId = 1;
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(rawMaterialId);

        ItemRawMaterialDatabasePersistence itemRawMaterialDatabasePersistence = new ItemRawMaterialDatabasePersistence(commonDatabaseOperation);
        itemRawMaterialDatabasePersistence.loadUnitCost(rawMaterialId);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(1)).getDouble(anyString());
        Assert.assertNotNull(itemRawMaterial.getCost());
    }

}
