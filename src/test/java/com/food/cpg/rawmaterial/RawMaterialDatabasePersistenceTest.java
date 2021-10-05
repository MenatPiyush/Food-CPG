package com.food.cpg.rawmaterial;

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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RawMaterialFactory.class)
public class RawMaterialDatabasePersistenceTest {

    private static final Integer TEST_MANUFACTURER_ID = 1;
    private static final Integer TEST_VENDOR_ID = 1;
    private static final Integer TEST_RAW_MATERIAL_ID = 1;
    private static final String TEST_RAW_MATERIAL_NAME = "Test raw material 1";
    private static final Double TEST_RAW_MATERIAL_UNIT_COST = 10.0;
    private static final Double TEST_RAW_MATERIAL_UNIT_MEASUREMENT = 5.0;
    private static final String TEST_RAW_MATERIAL_UNIT_MEASUREMENT_UOM = "g";
    private static final Double TEST_RAW_MATERIAL_REORDER_QUANTITY = 15.0;
    private static final String TEST_RAW_MATERIAL_REORDER_QUANTITY_UOM = "g";
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
    IRawMaterial rawMaterial;

    @Mock
    RawMaterialFactory rawMaterialFactory;

    @Before
    public void setUp() throws SQLException {
        when(commonDatabaseOperation.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    public void getAllTest() throws Exception {
        PowerMockito.mockStatic(RawMaterialFactory.class);
        PowerMockito.doReturn(rawMaterialFactory).when(RawMaterialFactory.class, GET_INSTANCE_METHOD);
        when(rawMaterialFactory.makeRawMaterial()).thenReturn(rawMaterial);

        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(TEST_MANUFACTURER_ID);

        RawMaterialDatabasePersistence rawMaterialDatabasePersistence = new RawMaterialDatabasePersistence(commonDatabaseOperation);

        rawMaterialDatabasePersistence.getAll(TEST_MANUFACTURER_ID);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(3)).next();
        verify(resultSet, times(6)).getString(anyString());
        verify(resultSet, times(4)).getInt(anyString());
        verify(resultSet, times(6)).getDouble(anyString());
    }

    @Test
    public void loadTest() throws SQLException {
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());
        when(rawMaterial.getId()).thenReturn(TEST_RAW_MATERIAL_ID);

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(rawMaterial.getId());

        RawMaterialDatabasePersistence rawMaterialDatabasePersistence = new RawMaterialDatabasePersistence(commonDatabaseOperation);

        rawMaterialDatabasePersistence.load(rawMaterial);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(3)).getString(anyString());
        verify(resultSet, times(2)).getInt(anyString());
        verify(resultSet, times(3)).getDouble(anyString());
    }

    @Test
    public void saveTest() throws SQLException {
        doReturn(TEST_RAW_MATERIAL_ID).when(commonDatabaseOperation).executeUpdateGetId(anyString(), anyList());
        when(rawMaterial.getName()).thenReturn(TEST_RAW_MATERIAL_NAME);
        when(rawMaterial.getVendorId()).thenReturn(TEST_VENDOR_ID);
        when(rawMaterial.getUnitCost()).thenReturn(TEST_RAW_MATERIAL_UNIT_COST);
        when(rawMaterial.getUnitMeasurement()).thenReturn(TEST_RAW_MATERIAL_UNIT_MEASUREMENT);
        when(rawMaterial.getUnitMeasurementUOM()).thenReturn(TEST_RAW_MATERIAL_UNIT_MEASUREMENT_UOM);
        when(rawMaterial.getReorderPointQuantity()).thenReturn(TEST_RAW_MATERIAL_REORDER_QUANTITY);
        when(rawMaterial.getReorderPointQuantityUOM()).thenReturn(TEST_RAW_MATERIAL_REORDER_QUANTITY_UOM);
        when(rawMaterial.getManufacturerId()).thenReturn(TEST_MANUFACTURER_ID);

        RawMaterialDatabasePersistence rawMaterialDatabasePersistence = new RawMaterialDatabasePersistence(commonDatabaseOperation);

        rawMaterialDatabasePersistence.save(rawMaterial);

        verify(commonDatabaseOperation, times(1)).executeUpdateGetId(anyString(), anyList());
        verify(rawMaterial, times(1)).getName();
        verify(rawMaterial, times(1)).getVendorId();
        verify(rawMaterial, times(1)).getUnitCost();
        verify(rawMaterial, times(1)).getUnitMeasurement();
        verify(rawMaterial, times(1)).getUnitMeasurementUOM();
        verify(rawMaterial, times(1)).getReorderPointQuantity();
        verify(rawMaterial, times(1)).getReorderPointQuantityUOM();
        verify(rawMaterial, times(1)).getManufacturerId();
    }

    @Test
    public void updateTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());
        when(rawMaterial.getName()).thenReturn(TEST_RAW_MATERIAL_NAME);
        when(rawMaterial.getVendorId()).thenReturn(TEST_VENDOR_ID);
        when(rawMaterial.getUnitCost()).thenReturn(TEST_RAW_MATERIAL_UNIT_COST);
        when(rawMaterial.getUnitMeasurement()).thenReturn(TEST_RAW_MATERIAL_UNIT_MEASUREMENT);
        when(rawMaterial.getUnitMeasurementUOM()).thenReturn(TEST_RAW_MATERIAL_UNIT_MEASUREMENT_UOM);
        when(rawMaterial.getReorderPointQuantity()).thenReturn(TEST_RAW_MATERIAL_REORDER_QUANTITY);
        when(rawMaterial.getReorderPointQuantityUOM()).thenReturn(TEST_RAW_MATERIAL_REORDER_QUANTITY_UOM);
        when(rawMaterial.getId()).thenReturn(TEST_RAW_MATERIAL_ID);

        RawMaterialDatabasePersistence rawMaterialDatabasePersistence = new RawMaterialDatabasePersistence(commonDatabaseOperation);

        rawMaterialDatabasePersistence.update(rawMaterial);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
        verify(rawMaterial, times(1)).getName();
        verify(rawMaterial, times(1)).getVendorId();
        verify(rawMaterial, times(1)).getUnitCost();
        verify(rawMaterial, times(1)).getUnitMeasurement();
        verify(rawMaterial, times(1)).getUnitMeasurementUOM();
        verify(rawMaterial, times(1)).getReorderPointQuantity();
        verify(rawMaterial, times(1)).getReorderPointQuantityUOM();
        verify(rawMaterial, times(1)).getId();
    }

    @Test
    public void deleteTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());

        RawMaterialDatabasePersistence rawMaterialDatabasePersistence = new RawMaterialDatabasePersistence(commonDatabaseOperation);

        rawMaterialDatabasePersistence.delete(TEST_RAW_MATERIAL_ID);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
    }
}
