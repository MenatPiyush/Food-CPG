package com.food.cpg.manufacturer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.food.cpg.authentication.AuthenticationFactory;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Manufacturer.class, AuthenticationFactory.class})
public class ManufacturerTest {

    private static final String EMPTY_STRING = "";
    private static final String COMPANY_NAME_ATTRIBUTE = "companyName";
    private static final String EMAIL_ATTRIBUTE = "email";
    private static final String PASSWORD_ATTRIBUTE = "password";
    private static final String CONTACT_ATTRIBUTE = "contact";
    private static final String CONTACT_LENGTH_ATTRIBUTE = "contactLength";
    private static final String ADDRESS_ATTRIBUTE = "address";
    private static final Integer TEST_ID = 1;
    private static final String TEST_COMPANY_NAME = "New Manufacturer";
    private static final String TEST_EMAIL = "rotesh@testmanufacturer.com";
    private static final String TEST_PASSWORD = "rotesh";
    private static final Long TEST_CONTACT = 9876567432L;
    private static final String TEST_ADDRESS = "Halifax";
    private static final String GET_PERSISTENCE_METHOD = "getPersistence";
    private static final String GET_INSTANCE_METHOD = "instance";

    @Mock
    IManufacturerPersistence manufacturerPersistence;

    @Mock
    AuthenticationFactory authenticationFactory;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    public void isValidCompanyNameTest() {
        IManufacturer manufacturer = new Manufacturer();
        manufacturer.setCompanyName(EMPTY_STRING);

        boolean isValidName = manufacturer.isValidManufacturer();

        Assert.assertFalse(isValidName);
        Assert.assertFalse(manufacturer.getErrors().isEmpty());
        Assert.assertNotNull(manufacturer.getErrors().get(COMPANY_NAME_ATTRIBUTE));
    }

    @Test
    public void isValidEmailTest() {
        IManufacturer manufacturer = new Manufacturer();
        manufacturer.setEmail(EMPTY_STRING);

        boolean isValidEmail = manufacturer.isValidManufacturer();

        Assert.assertFalse(isValidEmail);
        Assert.assertFalse(manufacturer.getErrors().isEmpty());
        Assert.assertNotNull(manufacturer.getErrors().get(EMAIL_ATTRIBUTE));
    }

    @Test
    public void isValidPasswordTest() {
        IManufacturer manufacturer = new Manufacturer();
        manufacturer.setPassword(EMPTY_STRING);

        boolean isValidPassword = manufacturer.isValidManufacturer();

        Assert.assertFalse(isValidPassword);
        Assert.assertFalse(manufacturer.getErrors().isEmpty());
        Assert.assertNotNull(manufacturer.getErrors().get(PASSWORD_ATTRIBUTE));
    }

    @Test
    public void isValidContactTest() {
        IManufacturer manufacturer = new Manufacturer();

        boolean isValidContact = manufacturer.isValidManufacturer();

        Assert.assertFalse(isValidContact);
        Assert.assertFalse(manufacturer.getErrors().isEmpty());
        Assert.assertNotNull(manufacturer.getErrors().get(CONTACT_ATTRIBUTE));
    }

    @Test
    public void isValidContactDigitTest() {
        IManufacturer manufacturer = new Manufacturer();
        manufacturer.setContact(98765432L);

        boolean isValidContactDigit = manufacturer.isValidManufacturer();

        Assert.assertFalse(isValidContactDigit);
        Assert.assertFalse(manufacturer.getErrors().isEmpty());
        Assert.assertNotNull(manufacturer.getErrors().get(CONTACT_LENGTH_ATTRIBUTE));
    }

    @Test
    public void isValidAddressTest() {
        IManufacturer manufacturer = new Manufacturer();
        manufacturer.setAddress(EMPTY_STRING);

        boolean isValidAddress = manufacturer.isValidManufacturer();

        Assert.assertFalse(isValidAddress);
        Assert.assertFalse(manufacturer.getErrors().isEmpty());
        Assert.assertNotNull(manufacturer.getErrors().get(ADDRESS_ATTRIBUTE));
    }

    @Test
    public void allManufacturerPropertiesValidTest() {
        IManufacturer manufacturer = new Manufacturer();
        manufacturer.setCompanyName(TEST_COMPANY_NAME);
        manufacturer.setEmail(TEST_EMAIL);
        manufacturer.setPassword(TEST_PASSWORD);
        manufacturer.setContact(TEST_CONTACT);
        manufacturer.setAddress(TEST_ADDRESS);

        boolean isValidManufacturer = manufacturer.isValidManufacturer();

        Assert.assertTrue(isValidManufacturer);
        Assert.assertTrue(manufacturer.getErrors().isEmpty());
    }

    @Test
    public void registerTest() throws Exception {
        IManufacturer manufacturer = spy(new Manufacturer());
        manufacturer.setId(TEST_ID);
        manufacturer.setCompanyName(TEST_COMPANY_NAME);
        manufacturer.setPassword(TEST_PASSWORD);

        PowerMockito.mockStatic(AuthenticationFactory.class);
        PowerMockito.doReturn(authenticationFactory).when(AuthenticationFactory.class, GET_INSTANCE_METHOD);
        when(authenticationFactory.makePasswordEncoder()).thenReturn(passwordEncoder);
        doReturn(TEST_PASSWORD).when(passwordEncoder).encode(anyString());

        PowerMockito.doReturn(manufacturerPersistence).when(manufacturer, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(manufacturerPersistence).register(manufacturer);
        PowerMockito.doNothing().when(manufacturerPersistence).createLoginAccount(manufacturer);

        manufacturer.register();
        verifyPrivate(manufacturer, times(2)).invoke(GET_PERSISTENCE_METHOD);
        verify(manufacturerPersistence, times(1)).register(manufacturer);
        verify(manufacturerPersistence, times(1)).createLoginAccount(manufacturer);
    }

    @Test
    public void loadTest() throws Exception {
        IManufacturer manufacturer = spy(new Manufacturer());
        manufacturer.setId(TEST_ID);
        manufacturer.setCompanyName(TEST_COMPANY_NAME);

        PowerMockito.doReturn(manufacturerPersistence).when(manufacturer, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(manufacturerPersistence).load(manufacturer);

        manufacturer.load();
        verifyPrivate(manufacturer).invoke(GET_PERSISTENCE_METHOD);
        verify(manufacturerPersistence, times(1)).load(manufacturer);
    }
}