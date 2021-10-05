package com.food.cpg.manufactureorder;

import com.food.cpg.inventory.IItemInventory;
import com.food.cpg.inventory.InventoryFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.spy;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ManufactureManufacturedOrderStatus.class,InventoryFactory.class})
public class ManufactureManufacturedOrderStatusTest  {

    private static final String GET_INSTANCE_METHOD = "instance";
    private static final String GET_PERSISTENCE_METHOD = "getPersistence";
    private static final String GET_ITEM_INVENTORY_INSTANCE_METHOD_NAME = "getItemInventoryInstance";
    private static final String TEST_MANUFACTURE_ORDER_NUMBER = "MO-123";
    private static final Integer TEST_ITEM_ID = 1;
    private static final Double TEST_ITEM_QUANTITY = 1.00;

    @Mock
    IManufactureOrderPersistence manufactureOrderPersistence;

    @Mock
    InventoryFactory inventoryFactory;

    @Mock
    IItemInventory itemInventory;

    @Mock
    IManufactureOrder manufactureOrder;

    @Test
    public void getOrderStatusTest() {
        ManufactureManufacturedOrderStatus manufactureManufacturedOrderStatus = new ManufactureManufacturedOrderStatus();

        Assert.assertEquals(ManufactureOrderStatus.Status.MANUFACTURED, manufactureManufacturedOrderStatus.getOrderStatus());
    }

    @Test
    public void moveOrderTest() throws Exception {
        ManufactureManufacturedOrderStatus manufactureManufacturedOrderStatus = spy(new ManufactureManufacturedOrderStatus());

        manufactureOrder.setItemId(TEST_ITEM_ID);
        manufactureOrder.setItemQuantity(TEST_ITEM_QUANTITY);
        manufactureOrder.setOrderNumber(TEST_MANUFACTURE_ORDER_NUMBER);

        PowerMockito.doReturn(manufactureOrderPersistence).when(manufactureManufacturedOrderStatus, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(manufactureOrderPersistence).changeStatus(anyString(), anyString());
        PowerMockito.doNothing().when(manufactureManufacturedOrderStatus).increaseItemQuantity(any());
        PowerMockito.doReturn(TEST_MANUFACTURE_ORDER_NUMBER).when(manufactureOrder).getOrderNumber();

        manufactureManufacturedOrderStatus.moveOrder(manufactureOrder);
        verify(manufactureOrderPersistence, times(1)).changeStatus(anyString(), anyString());
    }

    @Test
    public void increaseItemQuantityTest() throws Exception {
        ManufactureManufacturedOrderStatus manufactureManufacturedOrderStatus = spy(new ManufactureManufacturedOrderStatus());

        PowerMockito.mockStatic(InventoryFactory.class);
        PowerMockito.doReturn(inventoryFactory).when(InventoryFactory.class, GET_INSTANCE_METHOD);
        when(inventoryFactory.makeItemInventory()).thenReturn(itemInventory);
        doNothing().when(itemInventory).increaseQuantity();

        PowerMockito.doReturn(TEST_MANUFACTURE_ORDER_NUMBER).when(manufactureOrder).getOrderNumber();
        PowerMockito.doReturn(TEST_ITEM_ID).when(manufactureOrder).getItemId();
        PowerMockito.doReturn(TEST_ITEM_QUANTITY).when(manufactureOrder).getItemQuantity();

        manufactureManufacturedOrderStatus.increaseItemQuantity(manufactureOrder);

        verify(itemInventory, times(1)).increaseQuantity();
        verify(manufactureManufacturedOrderStatus, times(1)).increaseItemQuantity(manufactureOrder);
    }
}