package com.food.cpg.vendor;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Vendor.class)
public class VendorTest {

    public static final String GET_PERSISTENCE_METHOD = "getPersistence";
    public static final String GET_LOGGED_IN_MANUFACTURER_ID_METHOD = "getLoggedInManufacturerId";
    private static final String EMPTY_STRING = "";
    private static final String NAME_ATTRIBUTE = "name";
    private static final String CONTACT_NAME_ATTRIBUTE = "contactPersonName";
    private static final String CONTACT_EMAIL_ATTRIBUTE = "contactPersonEmail";
    private static final String CONTACT_PHONE_ATTRIBUTE = "contactPersonPhone";
    private static final Integer TEST_MANUFACTURER_ID = 1;
    private static final Integer TEST_VENDOR_ID = 1;
    private static final String TEST_VENDOR_NAME = "Test Vendor 1";
    private static final String TEST_CONTACT_NAME = "Kartik";
    private static final String TEST_CONTACT_EMAIL = "kartik@testvendor.com";
    private static final Long TEST_CONTACT_PHONE = 9876543210L;

    @Mock
    IVendorPersistence vendorPersistence;

    @Test
    public void isValidVendorNameTest() {
        IVendor vendor = new Vendor();
        vendor.setName(EMPTY_STRING);

        boolean isValidName = vendor.isValidVendor();

        Assert.assertFalse(isValidName);
        Assert.assertFalse(vendor.getErrors().isEmpty());
        Assert.assertNotNull(vendor.getErrors().get(NAME_ATTRIBUTE));
    }

    @Test
    public void isValidVendorContactPersonNameTest() {
        IVendor vendor = new Vendor();
        vendor.setContactPersonName(EMPTY_STRING);

        boolean isValidContact = vendor.isValidVendor();

        Assert.assertFalse(isValidContact);
        Assert.assertFalse(vendor.getErrors().isEmpty());
        Assert.assertNotNull(vendor.getErrors().get(CONTACT_NAME_ATTRIBUTE));
    }

    @Test
    public void isValidVendorContactPersonEmailTest() {
        IVendor vendor = new Vendor();
        vendor.setContactPersonEmail(EMPTY_STRING);

        boolean isValidContactEmail = vendor.isValidVendor();

        Assert.assertFalse(isValidContactEmail);
        Assert.assertFalse(vendor.getErrors().isEmpty());
        Assert.assertNotNull(vendor.getErrors().get(CONTACT_EMAIL_ATTRIBUTE));
    }

    @Test
    public void isValidVendorContactPersonPhoneTest() {
        IVendor vendor = new Vendor();

        boolean isValidContactPhone = vendor.isValidVendor();

        Assert.assertFalse(isValidContactPhone);
        Assert.assertFalse(vendor.getErrors().isEmpty());
        Assert.assertNotNull(vendor.getErrors().get(CONTACT_PHONE_ATTRIBUTE));
    }

    @Test
    public void isValidVendorContactPersonPhoneDigitTest() {
        IVendor vendor = new Vendor();
        vendor.setContactPersonPhone(98765432L);

        boolean isValidContactPhone = vendor.isValidVendor();

        Assert.assertFalse(isValidContactPhone);
        Assert.assertFalse(vendor.getErrors().isEmpty());
        Assert.assertNotNull(vendor.getErrors().get(CONTACT_PHONE_ATTRIBUTE));
    }

    @Test
    public void allVendorPropertiesValidTest() {
        IVendor vendor = new Vendor();
        vendor.setName(TEST_VENDOR_NAME);
        vendor.setContactPersonName(TEST_CONTACT_NAME);
        vendor.setContactPersonEmail(TEST_CONTACT_EMAIL);
        vendor.setContactPersonPhone(TEST_CONTACT_PHONE);

        boolean isValidVendor = vendor.isValidVendor();

        Assert.assertTrue(isValidVendor);
        Assert.assertTrue(vendor.getErrors().isEmpty());
    }

    @Test
    public void getAllTest() throws Exception {
        IVendor vendor = spy(new Vendor());
        vendor.setManufacturerId(TEST_MANUFACTURER_ID);
        vendor.setName(TEST_VENDOR_NAME);

        List<IVendor> vendors = new ArrayList<>();
        vendors.add(vendor);

        PowerMockito.doReturn(vendorPersistence).when(vendor, GET_PERSISTENCE_METHOD);
        PowerMockito.doReturn(vendors).when(vendorPersistence).getAll(anyInt());

        List<IVendor> vendorsResult = vendor.getAll();
        Assert.assertNotNull(vendorsResult);
        Assert.assertEquals(1, vendorsResult.size());
        Assert.assertEquals(TEST_VENDOR_NAME, vendorsResult.get(0).getName());
    }

    @Test
    public void saveTest() throws Exception {
        IVendor vendor = spy(new Vendor());
        vendor.setManufacturerId(TEST_MANUFACTURER_ID);
        vendor.setName(TEST_VENDOR_NAME);

        PowerMockito.doReturn(vendorPersistence).when(vendor, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(vendorPersistence).save(vendor);
        PowerMockito.doReturn(1).when(vendor, GET_LOGGED_IN_MANUFACTURER_ID_METHOD);

        vendor.save();
        verifyPrivate(vendor).invoke(GET_PERSISTENCE_METHOD);
        verify(vendorPersistence, times(1)).save(vendor);
    }

    @Test
    public void loadSuccessTest() throws Exception {
        IVendor vendor = spy(new Vendor());
        vendor.setId(TEST_VENDOR_ID);
        vendor.setManufacturerId(TEST_MANUFACTURER_ID);
        vendor.setName(TEST_VENDOR_NAME);

        PowerMockito.doReturn(vendorPersistence).when(vendor, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(vendorPersistence).load(vendor);

        vendor.load();
        verifyPrivate(vendor).invoke(GET_PERSISTENCE_METHOD);
        verify(vendorPersistence, times(1)).load(vendor);
    }

    @Test
    public void loadFailTest() throws Exception {
        IVendor vendor = spy(new Vendor());
        vendor.setId(0);
        vendor.setManufacturerId(TEST_MANUFACTURER_ID);
        vendor.setName(TEST_VENDOR_NAME);

        PowerMockito.doReturn(vendorPersistence).when(vendor, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(vendorPersistence).load(vendor);

        vendor.load();
        verify(vendorPersistence, times(0)).load(vendor);
    }

    @Test
    public void updateTest() throws Exception {
        IVendor vendor = spy(new Vendor());
        vendor.setManufacturerId(TEST_MANUFACTURER_ID);
        vendor.setName(TEST_VENDOR_NAME);

        PowerMockito.doReturn(vendorPersistence).when(vendor, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(vendorPersistence).update(vendor);

        vendor.update();
        verifyPrivate(vendor).invoke(GET_PERSISTENCE_METHOD);
        verify(vendorPersistence, times(1)).update(vendor);
    }

    @Test
    public void deleteTest() throws Exception {
        IVendor vendor = spy(new Vendor());
        vendor.setId(TEST_VENDOR_ID);
        vendor.setManufacturerId(TEST_MANUFACTURER_ID);
        vendor.setName(TEST_VENDOR_NAME);

        PowerMockito.doReturn(vendorPersistence).when(vendor, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(vendorPersistence).delete(anyInt());

        vendor.delete();
        verifyPrivate(vendor).invoke(GET_PERSISTENCE_METHOD);
        verify(vendorPersistence, times(1)).delete(vendor.getId());
    }
}
