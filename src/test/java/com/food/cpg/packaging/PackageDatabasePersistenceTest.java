package com.food.cpg.packaging;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PackageFactory.class)
public class PackageDatabasePersistenceTest extends TestCase {
    private static final Integer TEST_MANUFACTURER_ID = 1;
    private static final Integer TEST_ITEM_ID = 1;
    private static final Integer TEST_PACKAGE_ID = 1;
    private static final String TEST_PACKAGE_NAME = "Test package 1";
    private static final Double TEST_QUANTITY = 10.0;
    private static final Double TEST_MANUFACTURING_COST = 50.0;
    private static final Double TEST_WHOLE_SALE_COST = 100.0;
    private static final Double TEST_RETAIL_COST = 150.0;
    public static final String GET_INSTANCE_METHOD = "instance";

    @Mock
    ICommonDatabaseOperation commonDatabaseOperation;

    @Mock
    Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    ResultSet resultSet;

    @Mock
    IPackage packages;

    @Mock
    PackageFactory packageFactory;

    @Before
    public void setUp() throws SQLException {
        when(commonDatabaseOperation.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    public void getAllTest() throws Exception {
        PowerMockito.mockStatic(PackageFactory.class);
        PowerMockito.doReturn(packageFactory).when(PackageFactory.class, GET_INSTANCE_METHOD);
        when(packageFactory.makePackage()).thenReturn(packages);
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(TEST_MANUFACTURER_ID);

        PackageDatabasePersistence packagesDatabasePersistence = new PackageDatabasePersistence(commonDatabaseOperation);

        packagesDatabasePersistence.getAll(TEST_MANUFACTURER_ID);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(3)).next();
        verify(resultSet, times(2)).getString(anyString());
        verify(resultSet, times(4)).getInt(anyString());
        verify(resultSet, times(8)).getDouble(anyString());
    }

    @Test
    public void loadTest() throws SQLException {
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());
        when(packages.getPackageId()).thenReturn(TEST_PACKAGE_ID);

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(packages.getPackageId());

        PackageDatabasePersistence packagesDatabasePersistence = new PackageDatabasePersistence(commonDatabaseOperation);

        packagesDatabasePersistence.load(packages);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(1)).getString(anyString());
        verify(resultSet, times(2)).getInt(anyString());
        verify(resultSet, times(4)).getDouble(anyString());
    }

    @Test
    public void saveTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());
        when(packages.getItemId()).thenReturn(TEST_ITEM_ID);
        when(packages.getPackageName()).thenReturn(TEST_PACKAGE_NAME);
        when(packages.getQuantity()).thenReturn(TEST_QUANTITY);
        when(packages.getManufacturingCost()).thenReturn(TEST_MANUFACTURING_COST);
        when(packages.getWholesaleCost()).thenReturn(TEST_WHOLE_SALE_COST);
        when(packages.getRetailCost()).thenReturn(TEST_RETAIL_COST);
        when(packages.getManufacturerId()).thenReturn(TEST_MANUFACTURER_ID);

        PackageDatabasePersistence packagesDatabasePersistence = new PackageDatabasePersistence(commonDatabaseOperation);

        packagesDatabasePersistence.save(packages);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
        verify(packages, times(1)).getItemId();
        verify(packages, times(1)).getPackageName();
        verify(packages, times(1)).getQuantity();
        verify(packages, times(1)).getManufacturingCost();
        verify(packages, times(1)).getWholesaleCost();
        verify(packages, times(1)).getRetailCost();
        verify(packages, times(1)).getManufacturerId();
    }

    @Test
    public void updateTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());
        when(packages.getItemId()).thenReturn(TEST_ITEM_ID);
        when(packages.getPackageName()).thenReturn(TEST_PACKAGE_NAME);
        when(packages.getQuantity()).thenReturn(TEST_QUANTITY);
        when(packages.getManufacturingCost()).thenReturn(TEST_MANUFACTURING_COST);
        when(packages.getWholesaleCost()).thenReturn(TEST_WHOLE_SALE_COST);
        when(packages.getRetailCost()).thenReturn(TEST_RETAIL_COST);
        when(packages.getPackageId()).thenReturn(TEST_PACKAGE_ID);

        PackageDatabasePersistence packagesDatabasePersistence = new PackageDatabasePersistence(commonDatabaseOperation);

        packagesDatabasePersistence.update(packages);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
        verify(packages, times(1)).getItemId();
        verify(packages, times(1)).getPackageName();
        verify(packages, times(1)).getQuantity();
        verify(packages, times(1)).getManufacturingCost();
        verify(packages, times(1)).getWholesaleCost();
        verify(packages, times(1)).getRetailCost();
        verify(packages, times(1)).getPackageId();
    }

    @Test
    public void deleteTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());

        PackageDatabasePersistence packagesDatabasePersistence = new PackageDatabasePersistence(commonDatabaseOperation);

        packagesDatabasePersistence.delete(TEST_PACKAGE_ID);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
    }
}