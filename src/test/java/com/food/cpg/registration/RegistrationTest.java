package com.food.cpg.registration;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.food.cpg.manufacturer.IManufacturer;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;


@RunWith(PowerMockRunner.class)
@PrepareForTest(Registration.class)
public class RegistrationTest {

    private static final String GET_PERSISTENCE_METHOD = "getPersistence";
    private static final String TEST_CONTACT_EMAIL = "rotesh@testregistration.com";
    private static final String TEST_MANUFACTURER_COMPANY_NAME = "Test manufacturer";

    @Mock
    IRegistrationPersistence registrationPersistence;

    @Mock
    IManufacturer manufacturer;

    @Test
    public void getAllTest() throws Exception {
        IRegistration registration = spy(new Registration());
        manufacturer.setCompanyName(TEST_MANUFACTURER_COMPANY_NAME);
        manufacturer.setEmail(TEST_CONTACT_EMAIL);
        registration.setManufacturer(manufacturer);

        List<IRegistration> registrations = new ArrayList<>();
        registrations.add(registration);

        PowerMockito.doReturn(registrationPersistence).when(registration, GET_PERSISTENCE_METHOD);
        PowerMockito.doReturn(registrations).when(registrationPersistence).getAll();

        List<IRegistration> registrationResult = registration.getAll();
        Assert.assertNotNull(registrationResult);
        Assert.assertEquals(1, registrationResult.size());
        Assert.assertEquals(manufacturer, registrationResult.get(0).getManufacturer());
    }

    @Test
    public void approveTest() throws Exception {

        IRegistration registration = spy(new Registration());

        PowerMockito.doReturn(registrationPersistence).when(registration, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(registrationPersistence).approve(anyString());

        registration.approve(TEST_CONTACT_EMAIL);
        verifyPrivate(registration).invoke(GET_PERSISTENCE_METHOD);
        verify(registrationPersistence, times(1)).approve(TEST_CONTACT_EMAIL);
    }

    @Test
    public void blockTest() throws Exception {
        IRegistration registration = spy(new Registration());

        PowerMockito.doReturn(registrationPersistence).when(registration, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(registrationPersistence).block(anyString());

        registration.block(TEST_CONTACT_EMAIL);
        verifyPrivate(registration).invoke(GET_PERSISTENCE_METHOD);
        verify(registrationPersistence, times(1)).block(TEST_CONTACT_EMAIL);
    }
}