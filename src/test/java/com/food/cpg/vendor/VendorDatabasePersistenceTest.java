package com.food.cpg.vendor;

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
@PrepareForTest(VendorFactory.class)
public class VendorDatabasePersistenceTest {

    public static final String GET_INSTANCE_METHOD = "instance";
    private static final Integer TEST_MANUFACTURER_ID = 1;
    private static final Integer TEST_VENDOR_ID = 1;
    private static final String TEST_VENDOR_NAME = "Test Vendor 1";
    private static final String TEST_VENDOR_ADDRESS = "Test Address 1";
    private static final String TEST_CONTACT_PERSON_NAME = "Test Person 1";
    private static final String TEST_CONTACT_PERSON_EMAIL = "person1@vendor1.com";
    private static final Long TEST_CONTACT_PERSON_PHONE = 9876543210L;

    @Mock
    ICommonDatabaseOperation commonDatabaseOperation;

    @Mock
    Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    ResultSet resultSet;

    @Mock
    IVendor vendor;

    @Mock
    VendorFactory vendorFactory;

    @Before
    public void setUp() throws SQLException {
        when(commonDatabaseOperation.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    public void getAllTest() throws Exception {
        PowerMockito.mockStatic(VendorFactory.class);
        PowerMockito.doReturn(vendorFactory).when(VendorFactory.class, GET_INSTANCE_METHOD);
        when(vendorFactory.makeVendor()).thenReturn(vendor);

        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(TEST_MANUFACTURER_ID);

        VendorDatabasePersistence vendorDatabasePersistence = new VendorDatabasePersistence(commonDatabaseOperation);

        vendorDatabasePersistence.getAll(TEST_MANUFACTURER_ID);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(3)).next();
        verify(resultSet, times(8)).getString(anyString());
        verify(resultSet, times(2)).getInt(anyString());
        verify(resultSet, times(2)).getLong(anyString());
    }

    @Test
    public void loadTest() throws SQLException {
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());
        when(vendor.getId()).thenReturn(TEST_VENDOR_ID);

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(vendor.getId());

        VendorDatabasePersistence vendorDatabasePersistence = new VendorDatabasePersistence(commonDatabaseOperation);

        vendorDatabasePersistence.load(vendor);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(4)).getString(anyString());
        verify(resultSet, times(1)).getInt(anyString());
        verify(resultSet, times(1)).getLong(anyString());
    }

    @Test
    public void saveTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());
        when(vendor.getName()).thenReturn(TEST_VENDOR_NAME);
        when(vendor.getAddress()).thenReturn(TEST_VENDOR_ADDRESS);
        when(vendor.getContactPersonName()).thenReturn(TEST_CONTACT_PERSON_NAME);
        when(vendor.getContactPersonEmail()).thenReturn(TEST_CONTACT_PERSON_EMAIL);
        when(vendor.getContactPersonPhone()).thenReturn(TEST_CONTACT_PERSON_PHONE);
        when(vendor.getManufacturerId()).thenReturn(TEST_MANUFACTURER_ID);

        VendorDatabasePersistence vendorDatabasePersistence = new VendorDatabasePersistence(commonDatabaseOperation);

        vendorDatabasePersistence.save(vendor);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
        verify(vendor, times(1)).getName();
        verify(vendor, times(1)).getAddress();
        verify(vendor, times(1)).getContactPersonName();
        verify(vendor, times(1)).getContactPersonEmail();
        verify(vendor, times(1)).getContactPersonPhone();
        verify(vendor, times(1)).getManufacturerId();
    }

    @Test
    public void updateTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());
        when(vendor.getName()).thenReturn(TEST_VENDOR_NAME);
        when(vendor.getAddress()).thenReturn(TEST_VENDOR_ADDRESS);
        when(vendor.getContactPersonName()).thenReturn(TEST_CONTACT_PERSON_NAME);
        when(vendor.getContactPersonEmail()).thenReturn(TEST_CONTACT_PERSON_EMAIL);
        when(vendor.getContactPersonPhone()).thenReturn(TEST_CONTACT_PERSON_PHONE);
        when(vendor.getId()).thenReturn(TEST_VENDOR_ID);

        VendorDatabasePersistence vendorDatabasePersistence = new VendorDatabasePersistence(commonDatabaseOperation);

        vendorDatabasePersistence.update(vendor);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
        verify(vendor, times(1)).getName();
        verify(vendor, times(1)).getAddress();
        verify(vendor, times(1)).getContactPersonName();
        verify(vendor, times(1)).getContactPersonEmail();
        verify(vendor, times(1)).getContactPersonPhone();
        verify(vendor, times(1)).getId();
    }

    @Test
    public void deleteTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());

        VendorDatabasePersistence vendorDatabasePersistence = new VendorDatabasePersistence(commonDatabaseOperation);

        vendorDatabasePersistence.delete(1);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
    }
}
