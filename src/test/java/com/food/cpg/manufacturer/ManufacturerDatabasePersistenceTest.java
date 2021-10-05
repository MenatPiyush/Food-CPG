package com.food.cpg.manufacturer;

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
public class ManufacturerDatabasePersistenceTest {

    private static final String TEST_COMPANY_NAME = "New Manufacturer";
    private static final String TEST_MANUFACTURER_EMAIL = "rotesh@testmanufacturer.com";
    private static final String TEST_MANUFACTURER_PASSWORD = "rotesh";
    private static final Long TEST_MANUFACTURER_CONTACT = 9876543210L;
    private static final String TEST_MANUFACTURER_ADDRESS = "Halifax";

    @Mock
    ICommonDatabaseOperation commonDatabaseOperation;

    @Mock
    Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    ResultSet resultSet;

    @Mock
    IManufacturer manufacturer;

    @Before
    public void setUp() throws SQLException {
        when(commonDatabaseOperation.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    public void getTest() throws SQLException {
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());
        when(manufacturer.getEmail()).thenReturn(TEST_MANUFACTURER_EMAIL);

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(manufacturer.getEmail());

        ManufacturerDatabasePersistence manufacturerDatabasePersistence = new ManufacturerDatabasePersistence(commonDatabaseOperation);

        manufacturerDatabasePersistence.get(TEST_MANUFACTURER_EMAIL);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(1)).getInt(anyString());
        verify(resultSet, times(3)).getString(anyString());
        verify(resultSet, times(1)).getLong(anyString());
    }

    @Test
    public void loadTest() throws SQLException {
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());
        when(manufacturer.getEmail()).thenReturn(TEST_MANUFACTURER_EMAIL);

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(manufacturer.getEmail());

        ManufacturerDatabasePersistence manufacturerDatabasePersistence = new ManufacturerDatabasePersistence(commonDatabaseOperation);

        manufacturerDatabasePersistence.load(manufacturer);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(1)).getInt(anyString());
        verify(resultSet, times(3)).getString(anyString());
        verify(resultSet, times(1)).getLong(anyString());
    }

    @Test
    public void registerTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());
        when(manufacturer.getCompanyName()).thenReturn(TEST_COMPANY_NAME);
        when(manufacturer.getEmail()).thenReturn(TEST_MANUFACTURER_EMAIL);
        when(manufacturer.getContact()).thenReturn(TEST_MANUFACTURER_CONTACT);
        when(manufacturer.getAddress()).thenReturn(TEST_MANUFACTURER_ADDRESS);

        ManufacturerDatabasePersistence manufacturerDatabasePersistence = new ManufacturerDatabasePersistence(commonDatabaseOperation);

        manufacturerDatabasePersistence.register(manufacturer);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
        verify(manufacturer, times(1)).getCompanyName();
        verify(manufacturer, times(1)).getEmail();
        verify(manufacturer, times(1)).getContact();
        verify(manufacturer, times(1)).getAddress();

    }

    @Test
    public void createLoginAccountTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());
        when(manufacturer.getEmail()).thenReturn(TEST_MANUFACTURER_EMAIL);
        when(manufacturer.getPassword()).thenReturn(TEST_MANUFACTURER_PASSWORD);

        ManufacturerDatabasePersistence manufacturerDatabasePersistence = new ManufacturerDatabasePersistence(commonDatabaseOperation);

        manufacturerDatabasePersistence.createLoginAccount(manufacturer);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
        verify(manufacturer, times(1)).getEmail();
        verify(manufacturer, times(1)).getPassword();
    }
}
