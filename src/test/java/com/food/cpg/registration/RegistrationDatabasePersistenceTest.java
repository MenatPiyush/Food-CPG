package com.food.cpg.registration;

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
import com.food.cpg.manufacturer.IManufacturer;
import com.food.cpg.manufacturer.ManufacturerFactory;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ManufacturerFactory.class, RegistrationFactory.class})
public class RegistrationDatabasePersistenceTest {

    private static final String TEST_CONTACT_EMAIL = "rotesh@testregistration.com";
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
    ManufacturerFactory manufacturerFactory;

    @Mock
    IManufacturer manufacturer;

    @Mock
    RegistrationFactory registrationFactory;

    @Mock
    IRegistration registration;

    @Before
    public void setUp() throws SQLException {
        when(commonDatabaseOperation.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    public void getAllTest() throws Exception {
        PowerMockito.mockStatic(ManufacturerFactory.class);
        PowerMockito.doReturn(manufacturerFactory).when(ManufacturerFactory.class, GET_INSTANCE_METHOD);
        when(manufacturerFactory.makeManufacturer()).thenReturn(manufacturer);

        PowerMockito.mockStatic(RegistrationFactory.class);
        PowerMockito.doReturn(registrationFactory).when(RegistrationFactory.class, GET_INSTANCE_METHOD);
        when(registrationFactory.makeRegistration()).thenReturn(registration);

        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(1);

        RegistrationDatabasePersistence registrationDatabasePersistence = new RegistrationDatabasePersistence(commonDatabaseOperation);

        registrationDatabasePersistence.getAll();

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(3)).next();
        verify(resultSet, times(2)).getInt(anyString());
        verify(resultSet, times(8)).getString(anyString());
        verify(resultSet, times(2)).getLong(anyString());
    }

    @Test
    public void approveTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());

        RegistrationDatabasePersistence registrationDatabasePersistence = new RegistrationDatabasePersistence(commonDatabaseOperation);

        registrationDatabasePersistence.approve(TEST_CONTACT_EMAIL);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
    }

    @Test
    public void blockTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());

        RegistrationDatabasePersistence registrationDatabasePersistence = new RegistrationDatabasePersistence(commonDatabaseOperation);

        registrationDatabasePersistence.approve(TEST_CONTACT_EMAIL);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
    }
}
