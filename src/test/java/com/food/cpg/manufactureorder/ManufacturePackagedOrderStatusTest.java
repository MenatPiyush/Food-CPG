package com.food.cpg.manufactureorder;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.spy;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ManufactureManufacturedOrderStatus.class)
public class ManufacturePackagedOrderStatusTest{

    private static final String GET_PERSISTENCE_METHOD_NAME = "getPersistence";
    private static final String TEST_ORDER_NO = "Test order no";

    @Mock
    IManufactureOrderPersistence manufactureOrderPersistence;

    @Test
    public void getOrderStatusTest() {
        ManufacturePackagedOrderStatus manufacturePackagedOrderStatus = new ManufacturePackagedOrderStatus();

        Assert.assertEquals(ManufactureOrderStatus.Status.PACKAGED, manufacturePackagedOrderStatus.getOrderStatus());
    }

    @Test
    public void moveOrderTest() throws Exception {
        ManufacturePackagedOrderStatus manufacturePackagedOrderStatus = spy(new ManufacturePackagedOrderStatus());
        IManufactureOrder manufactureOrder = new ManufactureOrder();
        manufactureOrder.setOrderNumber(TEST_ORDER_NO);
        PowerMockito.doReturn(manufactureOrderPersistence).when(manufacturePackagedOrderStatus, GET_PERSISTENCE_METHOD_NAME);
        PowerMockito.doNothing().when(manufactureOrderPersistence).changeStatus(anyString(), anyString());

        manufacturePackagedOrderStatus.moveOrder(manufactureOrder);
        verify(manufactureOrderPersistence, times(1)).changeStatus(anyString(), anyString());
    }

}