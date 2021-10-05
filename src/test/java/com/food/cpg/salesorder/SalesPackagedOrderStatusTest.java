package com.food.cpg.salesorder;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.food.cpg.inventory.IItemInventory;
import com.food.cpg.inventory.InventoryFactory;
import com.food.cpg.packaging.IPackage;
import com.food.cpg.packaging.PackageFactory;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.spy;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SalesPackagedOrderStatus.class, InventoryFactory.class, PackageFactory.class})
public class SalesPackagedOrderStatusTest {

    private static final String GET_PERSISTENCE_METHOD_NAME = "getPersistence";
    private static final String GET_INSTANCE_METHOD = "instance";
    private static final String TEST_SALES_ORDER_NUMBER = "SO-123";
    private static final Integer TEST_ITEM_ID = 1;
    private static final Double TEST_ITEM_QUANTITY = 1.00;
    private static final Integer TEST_PACKAGE_ID = 1;


    @Mock
    ISalesOrderPersistence salesOrderPersistence;

    @Mock
    ISalesOrder salesOrder;

    @Mock
    IPackage iPackage;

    @Mock
    InventoryFactory inventoryFactory;

    @Mock
    IItemInventory itemInventory;

    @Mock
    PackageFactory packageFactory;


    @Test
    public void getOrderStatusTest() {
        SalesPackagedOrderStatus salesPackagedOrderStatus = new SalesPackagedOrderStatus();

        Assert.assertEquals(SalesOrderStatus.Status.PACKAGED, salesPackagedOrderStatus.getOrderStatus());
    }

    @Test
    public void moveOrderTest() throws Exception {
        SalesPackagedOrderStatus salesPackagedOrderStatus = spy(new SalesPackagedOrderStatus());

        PowerMockito.doReturn(salesOrderPersistence).when(salesPackagedOrderStatus, GET_PERSISTENCE_METHOD_NAME);
        PowerMockito.doNothing().when(salesOrderPersistence).changeStatus(anyString(), anyString());
        PowerMockito.doNothing().when(salesPackagedOrderStatus).decreaseItemQuantity(any());
        PowerMockito.doReturn(TEST_SALES_ORDER_NUMBER).when(salesOrder).getOrderNumber();

        salesPackagedOrderStatus.moveOrder(salesOrder);
        verify(salesOrderPersistence, times(1)).changeStatus(anyString(), anyString());
    }


    @Test
    public void decreaseItemQuantityTest() throws Exception {
        SalesPackagedOrderStatus salesPackagedOrderStatus = spy(new SalesPackagedOrderStatus());

        PowerMockito.mockStatic(InventoryFactory.class);
        PowerMockito.doReturn(inventoryFactory).when(InventoryFactory.class, GET_INSTANCE_METHOD);
        when(inventoryFactory.makeItemInventory()).thenReturn(itemInventory);
        doNothing().when(itemInventory).decreaseQuantity();

        PowerMockito.doReturn(TEST_SALES_ORDER_NUMBER).when(salesOrder).getOrderNumber();
        PowerMockito.doReturn(TEST_ITEM_ID).when(salesOrder).getItemId();
        PowerMockito.doReturn(TEST_PACKAGE_ID).when(salesOrder).getPackageId();

        PowerMockito.mockStatic(PackageFactory.class);
        PowerMockito.doReturn(packageFactory).when(PackageFactory.class, GET_INSTANCE_METHOD);
        when(packageFactory.makePackage()).thenReturn(iPackage);
        doNothing().when(iPackage).load();
        PowerMockito.doReturn(TEST_ITEM_QUANTITY).when(iPackage).getQuantity();

        salesPackagedOrderStatus.decreaseItemQuantity(salesOrder);

        verify(itemInventory, times(1)).decreaseQuantity();
        verify(iPackage, times(1)).load();
        verify(salesPackagedOrderStatus, times(1)).decreaseItemQuantity(salesOrder);
    }
}
