package com.food.cpg.purchaseorder;

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
@PrepareForTest(PurchaseOpenOrderStatus.class)
public class PurchaseOpenOrderStatusTest {
    private static final String GET_PERSISTENCE_METHOD_NAME = "getPersistence";

    @Mock
    IPurchaseOrderPersistence purchaseOrderPersistence;

    @Test
    public void getOrderStatusTest() {
        PurchaseOpenOrderStatus purchaseOpenOrderStatus = new PurchaseOpenOrderStatus();

        Assert.assertEquals(PurchaseOrderStatus.Status.OPEN, purchaseOpenOrderStatus.getOrderStatus());
    }

    @Test
    public void moveOrderTest() throws Exception {
        PurchaseOpenOrderStatus purchaseOpenOrderStatus = spy(new PurchaseOpenOrderStatus());
        PurchaseOrder purchaseOrder = spy(new PurchaseOrder());

        PowerMockito.doReturn(purchaseOrderPersistence).when(purchaseOpenOrderStatus, GET_PERSISTENCE_METHOD_NAME);
        PowerMockito.doNothing().when(purchaseOrderPersistence).changeStatus(anyString(), anyString());

        purchaseOpenOrderStatus.moveOrder(purchaseOrder);
        verify(purchaseOrderPersistence, times(1)).changeStatus(anyString(), anyString());
    }
}
