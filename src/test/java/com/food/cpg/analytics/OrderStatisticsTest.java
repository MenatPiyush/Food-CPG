package com.food.cpg.analytics;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.food.cpg.manufactureorder.IManufactureOrder;
import com.food.cpg.manufactureorder.IManufactureOrderPersistence;
import com.food.cpg.purchaseorder.IPurchaseOrder;
import com.food.cpg.purchaseorder.IPurchaseOrderPersistence;
import com.food.cpg.salesorder.ISalesOrder;
import com.food.cpg.salesorder.ISalesOrderPersistence;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

@RunWith(PowerMockRunner.class)
@PrepareForTest(OrderStatistics.class)
public class OrderStatisticsTest {

    private static final String GET_PURCHASE_ORDER_PERSISTENCE_METHOD = "getPurchaseOrderPersistence";
    private static final String GET_MANUFACTURE_ORDER_PERSISTENCE_METHOD = "getManufactureOrderPersistence";
    private static final String GET_SALES_ORDER_PERSISTENCE_METHOD = "getSalesOrderPersistence";
    private static final String GET_MANUFACTURER_ID_METHOD_NAME = "getLoggedInManufacturerId";
    private static final Integer TEST_MANUFACTURER_ID = 1;
    private static final Integer TOTAL_PURCHASE_ORDER_NUMBERS = 1;
    private static final Integer TOTAL_MANUFACTURE_ORDER_NUMBERS = 1;
    private static final Integer TOTAL_SALES_ORDER_NUMBERS = 1;

    @Mock
    IPurchaseOrderPersistence purchaseOrderPersistence;

    @Mock
    IPurchaseOrder purchaseOrder;

    @Mock
    IManufactureOrderPersistence manufactureOrderPersistence;

    @Mock
    IManufactureOrder manufactureOrder;

    @Mock
    ISalesOrderPersistence salesOrderPersistence;

    @Mock
    ISalesOrder salesOrder;

    @Test
    public void generateOrderStatisticsTest() throws Exception {
        OrderStatistics orderStatistics = spy(new OrderStatistics());

        PowerMockito.doReturn(1).when(orderStatistics, GET_MANUFACTURER_ID_METHOD_NAME);

        PowerMockito.doReturn(purchaseOrderPersistence).when(orderStatistics, GET_PURCHASE_ORDER_PERSISTENCE_METHOD);
        PowerMockito.doReturn(manufactureOrderPersistence).when(orderStatistics, GET_MANUFACTURE_ORDER_PERSISTENCE_METHOD);
        PowerMockito.doReturn(salesOrderPersistence).when(orderStatistics, GET_SALES_ORDER_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(orderStatistics).generatePurchaseOrderStatistics(TEST_MANUFACTURER_ID);
        PowerMockito.doNothing().when(orderStatistics).generateManufactureOrderStatistics(TEST_MANUFACTURER_ID);
        PowerMockito.doNothing().when(orderStatistics).generateSalesOrderStatistics(TEST_MANUFACTURER_ID);
        PowerMockito.doNothing().when(orderStatistics).calculateTotalOrderNumber();

        orderStatistics.generateOrderStatistics();

        verifyPrivate(orderStatistics).invoke(GET_MANUFACTURER_ID_METHOD_NAME);
        verify(orderStatistics, times(1)).generatePurchaseOrderStatistics(anyInt());
        verify(orderStatistics, times(1)).generateManufactureOrderStatistics(anyInt());
        verify(orderStatistics, times(1)).generateSalesOrderStatistics(anyInt());

    }

    @Test
    public void generatePurchaseOrderStatisticsTest() throws Exception {
        OrderStatistics orderStatistics = spy(new OrderStatistics());

        List<IPurchaseOrder> openPurchaseOrders = new ArrayList<>();
        openPurchaseOrders.add(purchaseOrder);
        List<IPurchaseOrder> placedPurchaseOrders = new ArrayList<>();
        placedPurchaseOrders.add(purchaseOrder);
        List<IPurchaseOrder> receivedPurchaseOrders = new ArrayList<>();
        receivedPurchaseOrders.add(purchaseOrder);
        List<IPurchaseOrder> paidPurchaseOrders = new ArrayList<>();
        paidPurchaseOrders.add(purchaseOrder);

        PowerMockito.doReturn(purchaseOrderPersistence).when(orderStatistics, GET_PURCHASE_ORDER_PERSISTENCE_METHOD);
        PowerMockito.doReturn(openPurchaseOrders).when(purchaseOrderPersistence).getOpenPurchaseOrder(anyInt());
        PowerMockito.doReturn(purchaseOrderPersistence).when(orderStatistics, GET_PURCHASE_ORDER_PERSISTENCE_METHOD);
        PowerMockito.doReturn(placedPurchaseOrders).when(purchaseOrderPersistence).getPlacedPurchaseOrder(anyInt());
        PowerMockito.doReturn(purchaseOrderPersistence).when(orderStatistics, GET_PURCHASE_ORDER_PERSISTENCE_METHOD);
        PowerMockito.doReturn(receivedPurchaseOrders).when(purchaseOrderPersistence).getReceivedPurchaseOrder(anyInt());
        PowerMockito.doReturn(purchaseOrderPersistence).when(orderStatistics, GET_PURCHASE_ORDER_PERSISTENCE_METHOD);
        PowerMockito.doReturn(paidPurchaseOrders).when(purchaseOrderPersistence).getPaidPurchaseOrder(anyInt());


        orderStatistics.generatePurchaseOrderStatistics(anyInt());
        Assert.assertEquals(1, openPurchaseOrders.size());
        Assert.assertEquals(1, placedPurchaseOrders.size());
        Assert.assertEquals(1, receivedPurchaseOrders.size());
        Assert.assertEquals(1, paidPurchaseOrders.size());

        Assert.assertNotNull(orderStatistics.getTotalPurchaseOrders());

    }

    @Test
    public void generateManufactureOrderStatisticsTest() throws Exception {
        OrderStatistics orderStatistics = spy(new OrderStatistics());

        List<IManufactureOrder> openManufactureOrders = new ArrayList<>();
        openManufactureOrders.add(manufactureOrder);
        List<IManufactureOrder> manufacturedManufactureOrders = new ArrayList<>();
        manufacturedManufactureOrders.add(manufactureOrder);
        List<IManufactureOrder> packagedManufactureOrders = new ArrayList<>();
        packagedManufactureOrders.add(manufactureOrder);
        List<IManufactureOrder> storedManufactureOrders = new ArrayList<>();
        storedManufactureOrders.add(manufactureOrder);

        PowerMockito.doReturn(manufactureOrderPersistence).when(orderStatistics, GET_MANUFACTURE_ORDER_PERSISTENCE_METHOD);
        PowerMockito.doReturn(openManufactureOrders).when(manufactureOrderPersistence).getAllOpenOrders(anyInt());
        PowerMockito.doReturn(purchaseOrderPersistence).when(orderStatistics, GET_PURCHASE_ORDER_PERSISTENCE_METHOD);
        PowerMockito.doReturn(manufacturedManufactureOrders).when(manufactureOrderPersistence).getAllManufacturedOrders(anyInt());
        PowerMockito.doReturn(purchaseOrderPersistence).when(orderStatistics, GET_PURCHASE_ORDER_PERSISTENCE_METHOD);
        PowerMockito.doReturn(packagedManufactureOrders).when(manufactureOrderPersistence).getAllPackagedOrders(anyInt());
        PowerMockito.doReturn(purchaseOrderPersistence).when(orderStatistics, GET_PURCHASE_ORDER_PERSISTENCE_METHOD);
        PowerMockito.doReturn(storedManufactureOrders).when(manufactureOrderPersistence).getAllStoredOrders(anyInt());


        orderStatistics.generateManufactureOrderStatistics(anyInt());
        Assert.assertEquals(1, openManufactureOrders.size());
        Assert.assertEquals(1, manufacturedManufactureOrders.size());
        Assert.assertEquals(1, packagedManufactureOrders.size());
        Assert.assertEquals(1, storedManufactureOrders.size());

        Assert.assertNotNull(orderStatistics.getTotalManufactureOrders());

    }

    @Test
    public void generateSalesOrderStatisticsTest() throws Exception {
        OrderStatistics orderStatistics = spy(new OrderStatistics());

        List<ISalesOrder> openSalesOrders = new ArrayList<>();
        openSalesOrders.add(salesOrder);
        List<ISalesOrder> packagedSalesOrders = new ArrayList<>();
        packagedSalesOrders.add(salesOrder);
        List<ISalesOrder> shippedSalesOrders = new ArrayList<>();
        shippedSalesOrders.add(salesOrder);
        List<ISalesOrder> paidSalesOrders = new ArrayList<>();
        paidSalesOrders.add(salesOrder);

        PowerMockito.doReturn(salesOrderPersistence).when(orderStatistics, GET_SALES_ORDER_PERSISTENCE_METHOD);
        PowerMockito.doReturn(openSalesOrders).when(salesOrderPersistence).getAllOpenOrders(anyInt());
        PowerMockito.doReturn(purchaseOrderPersistence).when(orderStatistics, GET_PURCHASE_ORDER_PERSISTENCE_METHOD);
        PowerMockito.doReturn(packagedSalesOrders).when(salesOrderPersistence).getAllPackagedOrders(anyInt());
        PowerMockito.doReturn(purchaseOrderPersistence).when(orderStatistics, GET_PURCHASE_ORDER_PERSISTENCE_METHOD);
        PowerMockito.doReturn(shippedSalesOrders).when(salesOrderPersistence).getAllShippedOrders(anyInt());
        PowerMockito.doReturn(purchaseOrderPersistence).when(orderStatistics, GET_PURCHASE_ORDER_PERSISTENCE_METHOD);
        PowerMockito.doReturn(paidSalesOrders).when(salesOrderPersistence).getAllPaidOrders(anyInt());


        orderStatistics.generateSalesOrderStatistics(anyInt());
        Assert.assertEquals(1, openSalesOrders.size());
        Assert.assertEquals(1, packagedSalesOrders.size());
        Assert.assertEquals(1, shippedSalesOrders.size());
        Assert.assertEquals(1, paidSalesOrders.size());

        Assert.assertNotNull(orderStatistics.getTotalSalesOrders());

    }

    @Test
    public void calculateTotalOrderNumberTest() {
        OrderStatistics orderStatistics = spy(new OrderStatistics());

        orderStatistics.setTotalPurchaseOrders(TOTAL_PURCHASE_ORDER_NUMBERS);
        orderStatistics.setTotalManufactureOrders(TOTAL_MANUFACTURE_ORDER_NUMBERS);
        orderStatistics.setTotalSalesOrders(TOTAL_SALES_ORDER_NUMBERS);

        Integer totalOrderNumbers = orderStatistics.getTotalPurchaseOrders() + orderStatistics.getTotalManufactureOrders() + orderStatistics.getTotalSalesOrders();

        orderStatistics.setTotalOrders(totalOrderNumbers);

        orderStatistics.calculateTotalOrderNumber();

        Assert.assertNotNull(orderStatistics.getTotalOrders());

    }
}
