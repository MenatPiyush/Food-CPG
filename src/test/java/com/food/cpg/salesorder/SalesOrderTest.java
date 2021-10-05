package com.food.cpg.salesorder;

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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SalesOrder.class)
public class SalesOrderTest {

    private static final double DELTA = 1e-15;
    private static final String TEST_ORDER_NUMBER = "SO-123456";
    private static final String ORDER_NUMBER_PREFIX = "SO-";
    private static final int TEST_MANUFACTURER_ID = 1;
    private static final Double TEST_PACKAGE_COST = 200.00;
    private static final Double TEST_SHIPPING_COST = 200.00;
    private static final Double TEST_SALES_ORDER_TAX = 10.00;
    private static final double TEST_SALES_ORDER_COST = 10.0;
    private static final String GET_PERSISTENCE_METHOD = "getPersistence";
    private static final String GET_MANUFACTURER_ID_METHOD = "getLoggedInManufacturerId";

    @Mock
    ISalesOrderPersistence salesOrderPersistence;

    @Mock
    SalesOrderStatus salesOrderStatus;

    @Test
    public void getAllOpenOrdersTest() throws Exception {
        ISalesOrder salesOrder = spy(new SalesOrder());
        salesOrder.setManufacturerId(TEST_MANUFACTURER_ID);
        salesOrder.setTotalCost(TEST_SALES_ORDER_COST);

        List<ISalesOrder> salesOrders = new ArrayList<>();
        salesOrders.add(salesOrder);

        PowerMockito.doReturn(salesOrderPersistence).when(salesOrder, GET_PERSISTENCE_METHOD);
        PowerMockito.doReturn(salesOrders).when(salesOrderPersistence).getAllOpenOrders(anyInt());
        PowerMockito.doReturn(1).when(salesOrder, GET_MANUFACTURER_ID_METHOD);

        List<ISalesOrder> salesOrdersResult = salesOrder.getAllOpenOrders();

        verifyPrivate(salesOrder).invoke(GET_PERSISTENCE_METHOD);
        verifyPrivate(salesOrder).invoke(GET_MANUFACTURER_ID_METHOD);
        verify(salesOrderPersistence, times(1)).getAllOpenOrders(anyInt());
        Assert.assertNotNull(salesOrdersResult);
        Assert.assertEquals(1, salesOrdersResult.size());
        Assert.assertEquals(TEST_MANUFACTURER_ID, salesOrdersResult.get(0).getManufacturerId());
        Assert.assertEquals(TEST_SALES_ORDER_COST, salesOrdersResult.get(0).getTotalCost(), DELTA);
    }

    @Test
    public void getAllPackagedOrdersTest() throws Exception {
        ISalesOrder salesOrder = spy(new SalesOrder());
        salesOrder.setManufacturerId(TEST_MANUFACTURER_ID);
        salesOrder.setTotalCost(TEST_SALES_ORDER_COST);

        List<ISalesOrder> salesOrders = new ArrayList<>();
        salesOrders.add(salesOrder);

        PowerMockito.doReturn(salesOrderPersistence).when(salesOrder, GET_PERSISTENCE_METHOD);
        PowerMockito.doReturn(salesOrders).when(salesOrderPersistence).getAllPackagedOrders(anyInt());
        PowerMockito.doReturn(1).when(salesOrder, GET_MANUFACTURER_ID_METHOD);

        List<ISalesOrder> salesOrdersResult = salesOrder.getAllPackagedOrders();

        verifyPrivate(salesOrder).invoke(GET_PERSISTENCE_METHOD);
        verifyPrivate(salesOrder).invoke(GET_MANUFACTURER_ID_METHOD);
        verify(salesOrderPersistence, times(1)).getAllPackagedOrders(anyInt());
        Assert.assertNotNull(salesOrdersResult);
        Assert.assertEquals(1, salesOrdersResult.size());
        Assert.assertEquals(TEST_MANUFACTURER_ID, salesOrdersResult.get(0).getManufacturerId());
        Assert.assertEquals(TEST_SALES_ORDER_COST, salesOrdersResult.get(0).getTotalCost(), DELTA);
    }

    @Test
    public void getAllShippedOrdersTest() throws Exception {
        ISalesOrder salesOrder = spy(new SalesOrder());
        salesOrder.setManufacturerId(TEST_MANUFACTURER_ID);
        salesOrder.setTotalCost(TEST_SALES_ORDER_COST);

        List<ISalesOrder> salesOrders = new ArrayList<>();
        salesOrders.add(salesOrder);

        PowerMockito.doReturn(salesOrderPersistence).when(salesOrder, GET_PERSISTENCE_METHOD);
        PowerMockito.doReturn(salesOrders).when(salesOrderPersistence).getAllShippedOrders(anyInt());
        PowerMockito.doReturn(1).when(salesOrder, GET_MANUFACTURER_ID_METHOD);

        List<ISalesOrder> salesOrdersResult = salesOrder.getAllShippedOrders();

        verifyPrivate(salesOrder).invoke(GET_PERSISTENCE_METHOD);
        verifyPrivate(salesOrder).invoke(GET_MANUFACTURER_ID_METHOD);
        verify(salesOrderPersistence, times(1)).getAllShippedOrders(anyInt());
        Assert.assertNotNull(salesOrdersResult);
        Assert.assertEquals(1, salesOrdersResult.size());
        Assert.assertEquals(TEST_MANUFACTURER_ID, salesOrdersResult.get(0).getManufacturerId());
        Assert.assertEquals(TEST_SALES_ORDER_COST, salesOrdersResult.get(0).getTotalCost(), DELTA);
    }

    @Test
    public void getAllPaidOrdersTest() throws Exception {
        ISalesOrder salesOrder = spy(new SalesOrder());
        salesOrder.setManufacturerId(TEST_MANUFACTURER_ID);
        salesOrder.setTotalCost(TEST_SALES_ORDER_COST);

        List<ISalesOrder> salesOrders = new ArrayList<>();
        salesOrders.add(salesOrder);

        PowerMockito.doReturn(salesOrderPersistence).when(salesOrder, GET_PERSISTENCE_METHOD);
        PowerMockito.doReturn(salesOrders).when(salesOrderPersistence).getAllPaidOrders(anyInt());
        PowerMockito.doReturn(1).when(salesOrder, GET_MANUFACTURER_ID_METHOD);

        List<ISalesOrder> salesOrdersResult = salesOrder.getAllPaidOrders();

        verifyPrivate(salesOrder).invoke(GET_PERSISTENCE_METHOD);
        verifyPrivate(salesOrder).invoke(GET_MANUFACTURER_ID_METHOD);
        verify(salesOrderPersistence, times(1)).getAllPaidOrders(anyInt());
        Assert.assertNotNull(salesOrdersResult);
        Assert.assertEquals(1, salesOrdersResult.size());
        Assert.assertEquals(TEST_MANUFACTURER_ID, salesOrdersResult.get(0).getManufacturerId());
        Assert.assertEquals(TEST_SALES_ORDER_COST, salesOrdersResult.get(0).getTotalCost(), DELTA);
    }

    @Test
    public void loadTest() throws Exception {
        ISalesOrder salesOrder = spy(new SalesOrder());
        salesOrder.setManufacturerId(TEST_MANUFACTURER_ID);
        salesOrder.setTotalCost(TEST_SALES_ORDER_COST);

        PowerMockito.doReturn(salesOrderPersistence).when(salesOrder, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(salesOrderPersistence).load(salesOrder);

        salesOrder.load();

        verifyPrivate(salesOrder).invoke(GET_PERSISTENCE_METHOD);
        verify(salesOrderPersistence, times(1)).load(salesOrder);
    }

    @Test
    public void deleteTest() throws Exception {
        ISalesOrder salesOrder = spy(new SalesOrder());
        salesOrder.setOrderNumber(TEST_ORDER_NUMBER);
        salesOrder.setManufacturerId(TEST_MANUFACTURER_ID);
        salesOrder.setTotalCost(TEST_SALES_ORDER_COST);

        PowerMockito.doReturn(salesOrderPersistence).when(salesOrder, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(salesOrderPersistence).delete(anyString());

        salesOrder.delete();

        verifyPrivate(salesOrder).invoke(GET_PERSISTENCE_METHOD);
        verify(salesOrderPersistence, times(1)).delete(salesOrder.getOrderNumber());
    }

    @Test
    public void moveOrderToNextStageTest() throws Exception {
        ISalesOrder salesOrder = spy(new SalesOrder());
        salesOrder.setOrderNumber(TEST_ORDER_NUMBER);
        salesOrder.setManufacturerId(TEST_MANUFACTURER_ID);
        salesOrder.setSalesOrderStatus(salesOrderStatus);

        PowerMockito.doNothing().when(salesOrderStatus).moveOrder(salesOrder);

        salesOrder.moveOrderToNextStage();

        verify(salesOrderStatus, times(1)).moveOrder(salesOrder);
    }

    @Test
    public void generateOrderNumberTest() throws Exception {
        ISalesOrder salesOrder = new SalesOrder();

        Assert.assertNotNull(salesOrder.getOrderNumber());
        Assert.assertTrue(salesOrder.getOrderNumber().contains(ORDER_NUMBER_PREFIX));
    }

    @Test
    public void saveTest() throws Exception {
        ISalesOrder salesOrder = spy(new SalesOrder());
        salesOrder.setManufacturerId(TEST_MANUFACTURER_ID);

        PowerMockito.doReturn(salesOrderPersistence).when(salesOrder, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(salesOrderPersistence).save(salesOrder);
        PowerMockito.doReturn(1).when(salesOrder, GET_MANUFACTURER_ID_METHOD);

        salesOrder.save();
        verifyPrivate(salesOrder).invoke(GET_PERSISTENCE_METHOD);
        verify(salesOrderPersistence, times(1)).save(salesOrder);
    }

    @Test
    public void calculateTotalCostTest() throws Exception {
        ISalesOrder salesOrder = spy(new SalesOrder());
        salesOrder.setOrderNumber(TEST_ORDER_NUMBER);
        salesOrder.setPackageCost(TEST_PACKAGE_COST);
        salesOrder.setShippingCost(TEST_SHIPPING_COST);
        salesOrder.setTax(TEST_SALES_ORDER_TAX);

        PowerMockito.doReturn(salesOrderPersistence).when(salesOrder, GET_PERSISTENCE_METHOD);

        salesOrder.calculateTotalCost();
        Assert.assertEquals(440.0, salesOrder.getTotalCost(), DELTA);
    }
}
