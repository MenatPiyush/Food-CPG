package com.food.cpg.salesorder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.food.cpg.manufacturer.IManufacturer;
import com.food.cpg.manufacturer.IManufacturerPersistence;
import com.food.cpg.notification.INotification;
import com.food.cpg.notification.NotificationFactory;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SalesOrderWatcher.class, NotificationFactory.class})
public class SalesOrderWatcherTest {

    private static final int DUE_DAY_FOR_PAYMENT = 7;
    private static final int TEST_MANUFACTURER_ID = 1;
    private static final String GET_MANUFACTURER_PERSISTENCE_METHOD = "getManufacturerPersistence";
    private static final String GET_SALES_ORDER_PERSISTENCE_METHOD = "getSalesOrderPersistence";
    private static final String GET_INSTANCE_METHOD = "instance";

    @Mock
    INotification notification;

    @Mock
    NotificationFactory notificationFactory;

    @Mock
    IManufacturerPersistence manufacturerPersistence;

    @Mock
    ISalesOrderPersistence salesOrderPersistence;

    @Mock
    IManufacturer manufacturer;

    @Mock
    ISalesOrder salesOrder;

    @Test
    public void checkSalesOrdersForDueDateTest() throws Exception {
        SalesOrderWatcher salesOrderWatcher = spy(new SalesOrderWatcher());

        List<IManufacturer> manufacturers = new ArrayList<>();
        manufacturers.add(manufacturer);

        doNothing().when(salesOrderWatcher).checkSalesOrdersForDueDateByManufacturer(anyInt());
        PowerMockito.doReturn(manufacturerPersistence).when(salesOrderWatcher, GET_MANUFACTURER_PERSISTENCE_METHOD);
        when(manufacturerPersistence.getAll()).thenReturn(manufacturers);
        when(manufacturer.getId()).thenReturn(TEST_MANUFACTURER_ID);

        salesOrderWatcher.checkSalesOrdersForDueDate();

        verify(salesOrderWatcher, times(1)).checkSalesOrdersForDueDateByManufacturer(TEST_MANUFACTURER_ID);
        verify(manufacturer, times(1)).getId();
        verify(manufacturerPersistence, times(1)).getAll();
    }

    @Test
    public void checkSalesOrdersForDueDateByManufacturerTest() throws Exception {
        SalesOrderWatcher salesOrderWatcher = spy(new SalesOrderWatcher());

        List<ISalesOrder> salesOrders = new ArrayList<>();
        salesOrders.add(salesOrder);

        Date currentDate = new Date();

        doReturn(Boolean.TRUE).when(salesOrderWatcher).isSalesOrderPaymentOverdue(anyObject());
        doReturn(currentDate).when(salesOrderWatcher).calculateSalesOrderDueDate(anyObject());

        PowerMockito.mockStatic(NotificationFactory.class);
        PowerMockito.doReturn(notificationFactory).when(NotificationFactory.class, GET_INSTANCE_METHOD);
        when(notificationFactory.makeNotification(anyInt(), anyString(), anyObject())).thenReturn(notification);
        doNothing().when(notification).send();

        PowerMockito.doReturn(salesOrderPersistence).when(salesOrderWatcher, GET_SALES_ORDER_PERSISTENCE_METHOD);
        when(salesOrderPersistence.getAllShippedOrders(anyInt())).thenReturn(salesOrders);

        salesOrderWatcher.checkSalesOrdersForDueDateByManufacturer(TEST_MANUFACTURER_ID);

        verifyPrivate(salesOrderWatcher).invoke(GET_SALES_ORDER_PERSISTENCE_METHOD);
        verify(salesOrderPersistence, times(1)).getAllShippedOrders(anyInt());
        verify(salesOrderWatcher, times(1)).isSalesOrderPaymentOverdue(any());
        verify(salesOrderWatcher, times(1)).calculateSalesOrderDueDate(any());
        verify(notificationFactory, times(1)).makeNotification(anyInt(), anyString(), any());
        verify(notification, times(1)).send();
    }

    @Test
    public void isSalesOrderPaymentOverdueTrueTest() {
        SalesOrderWatcher salesOrderWatcher = spy(new SalesOrderWatcher());

        Calendar salesOrderDueDate = Calendar.getInstance();
        salesOrderDueDate.setTime(new Timestamp(System.currentTimeMillis() - 1000));

        doReturn(salesOrderDueDate.getTime()).when(salesOrderWatcher).calculateSalesOrderDueDate(any());

        boolean isPaymentOverdue = salesOrderWatcher.isSalesOrderPaymentOverdue(salesOrder);

        Assert.assertTrue(isPaymentOverdue);
    }

    @Test
    public void isSalesOrderPaymentOverdueFalseTest() {
        SalesOrderWatcher salesOrderWatcher = spy(new SalesOrderWatcher());

        Calendar salesOrderDueDate = Calendar.getInstance();
        salesOrderDueDate.setTime(new Timestamp(System.currentTimeMillis() + 1000));

        doReturn(salesOrderDueDate.getTime()).when(salesOrderWatcher).calculateSalesOrderDueDate(any());

        boolean isPaymentOverdue = salesOrderWatcher.isSalesOrderPaymentOverdue(salesOrder);

        Assert.assertFalse(isPaymentOverdue);
    }

    @Test
    public void calculateSalesOrderDueDate() {
        SalesOrderWatcher salesOrderWatcher = spy(new SalesOrderWatcher());

        Date currentDate = new Date();
        Calendar expectedDueDate = Calendar.getInstance();
        expectedDueDate.setTime(currentDate);
        expectedDueDate.add(Calendar.DATE, DUE_DAY_FOR_PAYMENT);

        when(salesOrder.getStatusChangeDate()).thenReturn(new Timestamp(currentDate.getTime()));

        Date actualDueDate = salesOrderWatcher.calculateSalesOrderDueDate(salesOrder);

        Assert.assertEquals(expectedDueDate.getTime(), actualDueDate);
    }
}
