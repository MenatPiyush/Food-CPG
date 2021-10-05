package com.food.cpg.manufactureorder;


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
@PrepareForTest(ManufactureOrder.class)
public class ManufactureOrderTest {

    private static final double DELTA = 1e-15;
    private static final String TEST_ORDER_NUMBER = "MO-123456";
    private static final Integer TEST_ITEM_ID = 1;
    private static final Double TEST_MANUFACTURING_COST = 10.00;
    private static final Double TEST_MANUFACTURE_ORDER_TAX = 10.00;
    private static final Double TEST_MANUFACTURE_ORDER_ITEM_QUANTITY= 1.00;
    private static final String ORDER_NUMBER_PREFIX = "MO-";
    private static final Double TEST_MANUFACTURE_ORDER_COST = 10.00;
    private static final Integer TEST_MANUFACTURER_ID = 1;
    private static final String GET_PERSISTENCE_METHOD= "getPersistence";
    private static final String GET_MANUFACTURER_ID_METHOD = "getLoggedInManufacturerId";

    @Mock
    IManufactureOrderPersistence manufactureOrderPersistence;

    @Mock
    ManufactureOrderStatus manufactureOrderStatus;

    @Test
    public void getAllOpenOrdersTest() throws Exception {
        IManufactureOrder manufactureOrder = spy(new ManufactureOrder());
        manufactureOrder.setManufacturerId(TEST_MANUFACTURER_ID);
        manufactureOrder.setCost(TEST_MANUFACTURE_ORDER_COST);

        List<IManufactureOrder> manufactureOrders = new ArrayList<>();
        manufactureOrders.add(manufactureOrder);

        PowerMockito.doReturn(manufactureOrderPersistence).when(manufactureOrder, GET_PERSISTENCE_METHOD);
        PowerMockito.doReturn(manufactureOrders).when(manufactureOrderPersistence).getAllOpenOrders(anyInt());
        PowerMockito.doReturn(1).when(manufactureOrder, GET_MANUFACTURER_ID_METHOD);

        List<IManufactureOrder> manufactureOrdersResult = manufactureOrder.getAllOpenOrders();

        verifyPrivate(manufactureOrder).invoke(GET_PERSISTENCE_METHOD);
        verifyPrivate(manufactureOrder).invoke(GET_MANUFACTURER_ID_METHOD);
        verify(manufactureOrderPersistence, times(1)).getAllOpenOrders(anyInt());
        Assert.assertNotNull(manufactureOrdersResult);
        Assert.assertEquals(1, manufactureOrdersResult.size());
        Assert.assertEquals(TEST_MANUFACTURER_ID, manufactureOrdersResult.get(0).getManufacturerId());
        Assert.assertEquals(TEST_MANUFACTURE_ORDER_COST, manufactureOrdersResult.get(0).getCost(), DELTA);
    }

    @Test
    public void getAllManufacturedOrdersTest() throws Exception {
        IManufactureOrder manufactureOrder = spy(new ManufactureOrder());
        manufactureOrder.setManufacturerId(TEST_MANUFACTURER_ID);
        manufactureOrder.setCost(TEST_MANUFACTURE_ORDER_COST);

        List<IManufactureOrder> manufactureOrders = new ArrayList<>();
        manufactureOrders.add(manufactureOrder);

        PowerMockito.doReturn(manufactureOrderPersistence).when(manufactureOrder, GET_PERSISTENCE_METHOD);
        PowerMockito.doReturn(manufactureOrders).when(manufactureOrderPersistence).getAllManufacturedOrders(anyInt());
        PowerMockito.doReturn(1).when(manufactureOrder, GET_MANUFACTURER_ID_METHOD);

        List<IManufactureOrder> manufactureOrdersResult = manufactureOrder.getAllManufacturedOrders();

        verifyPrivate(manufactureOrder).invoke(GET_PERSISTENCE_METHOD);
        verifyPrivate(manufactureOrder).invoke(GET_MANUFACTURER_ID_METHOD);
        verify(manufactureOrderPersistence, times(1)).getAllManufacturedOrders(anyInt());
        Assert.assertNotNull(manufactureOrdersResult);
        Assert.assertEquals(1, manufactureOrdersResult.size());
        Assert.assertEquals(TEST_MANUFACTURER_ID, manufactureOrdersResult.get(0).getManufacturerId());
        Assert.assertEquals(TEST_MANUFACTURE_ORDER_COST, manufactureOrdersResult.get(0).getCost(), DELTA);
    }

    @Test
    public void getAllPackagedOrdersTest() throws Exception {
        IManufactureOrder manufactureOrder = spy(new ManufactureOrder());
        manufactureOrder.setManufacturerId(TEST_MANUFACTURER_ID);
        manufactureOrder.setCost(TEST_MANUFACTURE_ORDER_COST);

        List<IManufactureOrder> manufactureOrders = new ArrayList<>();
        manufactureOrders.add(manufactureOrder);

        PowerMockito.doReturn(manufactureOrderPersistence).when(manufactureOrder, GET_PERSISTENCE_METHOD);
        PowerMockito.doReturn(manufactureOrders).when(manufactureOrderPersistence).getAllPackagedOrders(anyInt());
        PowerMockito.doReturn(1).when(manufactureOrder, GET_MANUFACTURER_ID_METHOD);

        List<IManufactureOrder> manufactureOrdersResult = manufactureOrder.getAllPackagedOrders();

        verifyPrivate(manufactureOrder).invoke(GET_PERSISTENCE_METHOD);
        verifyPrivate(manufactureOrder).invoke(GET_MANUFACTURER_ID_METHOD);
        verify(manufactureOrderPersistence, times(1)).getAllPackagedOrders(anyInt());
        Assert.assertNotNull(manufactureOrdersResult);
        Assert.assertEquals(1, manufactureOrdersResult.size());
        Assert.assertEquals(TEST_MANUFACTURER_ID, manufactureOrdersResult.get(0).getManufacturerId());
        Assert.assertEquals(TEST_MANUFACTURE_ORDER_COST, manufactureOrdersResult.get(0).getCost(), DELTA);
    }

    @Test
    public void getAllStoredOrdersTest() throws Exception {
        IManufactureOrder manufactureOrder = spy(new ManufactureOrder());
        manufactureOrder.setManufacturerId(TEST_MANUFACTURER_ID);
        manufactureOrder.setCost(TEST_MANUFACTURE_ORDER_COST);

        List<IManufactureOrder> manufactureOrders = new ArrayList<>();
        manufactureOrders.add(manufactureOrder);

        PowerMockito.doReturn(manufactureOrderPersistence).when(manufactureOrder, GET_PERSISTENCE_METHOD);
        PowerMockito.doReturn(manufactureOrders).when(manufactureOrderPersistence).getAllStoredOrders(anyInt());
        PowerMockito.doReturn(1).when(manufactureOrder, GET_MANUFACTURER_ID_METHOD);

        List<IManufactureOrder> manufactureOrdersResult = manufactureOrder.getAllStoredOrders();

        verifyPrivate(manufactureOrder).invoke(GET_PERSISTENCE_METHOD);
        verifyPrivate(manufactureOrder).invoke(GET_MANUFACTURER_ID_METHOD);
        verify(manufactureOrderPersistence, times(1)).getAllStoredOrders(anyInt());
        Assert.assertNotNull(manufactureOrdersResult);
        Assert.assertEquals(1, manufactureOrdersResult.size());
        Assert.assertEquals(TEST_MANUFACTURER_ID, manufactureOrdersResult.get(0).getManufacturerId());
        Assert.assertEquals(TEST_MANUFACTURE_ORDER_COST, manufactureOrdersResult.get(0).getCost(), DELTA);
    }

    @Test
    public void loadTest() throws Exception {
        IManufactureOrder manufactureOrder = spy(new ManufactureOrder());
        manufactureOrder.setManufacturerId(TEST_MANUFACTURER_ID);
        manufactureOrder.setCost(TEST_MANUFACTURE_ORDER_COST);

        PowerMockito.doReturn(manufactureOrderPersistence).when(manufactureOrder, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(manufactureOrderPersistence).load(manufactureOrder);

        manufactureOrder.load();

        verifyPrivate(manufactureOrder).invoke(GET_PERSISTENCE_METHOD);
        verify(manufactureOrderPersistence, times(1)).load(manufactureOrder);
    }

    @Test
    public void deleteTest() throws Exception {
        IManufactureOrder manufactureOrder = spy(new ManufactureOrder());
        manufactureOrder.setOrderNumber(TEST_ORDER_NUMBER);
        manufactureOrder.setManufacturerId(TEST_MANUFACTURER_ID);
        manufactureOrder.setCost(TEST_MANUFACTURE_ORDER_COST);

        PowerMockito.doReturn(manufactureOrderPersistence).when(manufactureOrder, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(manufactureOrderPersistence).delete(anyString());

        manufactureOrder.delete();

        verifyPrivate(manufactureOrder).invoke(GET_PERSISTENCE_METHOD);
        verify(manufactureOrderPersistence, times(1)).delete(manufactureOrder.getOrderNumber());
    }

    @Test
    public void moveOrderToNextStageTest() throws Exception {
        IManufactureOrder manufactureOrder = spy(new ManufactureOrder());
        manufactureOrder.setOrderNumber(TEST_ORDER_NUMBER);
        manufactureOrder.setManufacturerId(TEST_MANUFACTURER_ID);
        manufactureOrder.setManufactureOrderStatus(manufactureOrderStatus);

        PowerMockito.doNothing().when(manufactureOrderStatus).moveOrder(manufactureOrder);

        manufactureOrder.moveOrderToNextStage();

        verify(manufactureOrderStatus, times(1)).moveOrder(manufactureOrder);
    }


    @Test
    public void generateOrderNumberTest() throws Exception {
        IManufactureOrder manufactureOrder = new ManufactureOrder();

        Assert.assertNotNull(manufactureOrder.getOrderNumber());
        Assert.assertTrue(manufactureOrder.getOrderNumber().contains(ORDER_NUMBER_PREFIX));
    }

    @Test
    public void saveTest() throws Exception {
        IManufactureOrder manufactureOrder = spy(new ManufactureOrder());
        manufactureOrder.setManufacturerId(TEST_MANUFACTURER_ID);

        PowerMockito.doReturn(manufactureOrderPersistence).when(manufactureOrder, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(manufactureOrderPersistence).save(manufactureOrder);
        PowerMockito.doReturn(1).when(manufactureOrder, GET_MANUFACTURER_ID_METHOD);

        manufactureOrder.save();
        verifyPrivate(manufactureOrder).invoke(GET_PERSISTENCE_METHOD);
        verify(manufactureOrderPersistence, times(1)).save(manufactureOrder);
    }

    @Test
    public void calculateTotalCostTest() throws Exception{
        IManufactureOrder manufactureOrder = spy(new ManufactureOrder());
        manufactureOrder.setOrderNumber(TEST_ORDER_NUMBER);
        manufactureOrder.setManufacturingCost(TEST_MANUFACTURING_COST);
        manufactureOrder.setTax(TEST_MANUFACTURE_ORDER_TAX);
        manufactureOrder.setItemId(TEST_ITEM_ID);
        manufactureOrder.setItemQuantity(TEST_MANUFACTURE_ORDER_ITEM_QUANTITY);

        PowerMockito.doReturn(manufactureOrderPersistence).when(manufactureOrder, GET_PERSISTENCE_METHOD);
        PowerMockito.doReturn(30.0).when(manufactureOrderPersistence).loadItemCost(TEST_ITEM_ID);

        manufactureOrder.calculateTotalCost();
        verifyPrivate(manufactureOrder).invoke(GET_PERSISTENCE_METHOD);
        verify(manufactureOrderPersistence, times(1)).loadItemCost(TEST_ITEM_ID);
        Assert.assertNotNull(manufactureOrder.getCost());
        Assert.assertEquals(44.0, manufactureOrder.getCost().doubleValue(), DELTA);
    }


}