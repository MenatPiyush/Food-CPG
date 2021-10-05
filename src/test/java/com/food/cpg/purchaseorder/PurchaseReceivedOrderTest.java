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
@PrepareForTest(PurchaseReceivedOrderStatus.class)
public class PurchaseReceivedOrderTest {
    private static final String GET_PERSISTENCE_METHOD_NAME = "getPersistence";

    @Mock
    IPurchaseOrderPersistence purchaseOrderPersistence;

    @Test
    public void getOrderStatusTest() {
        PurchaseReceivedOrderStatus purchaseReceivedOrderStatus = new PurchaseReceivedOrderStatus();

        Assert.assertEquals(PurchaseOrderStatus.Status.RECEIVED, purchaseReceivedOrderStatus.getOrderStatus());
    }

    @Test
    public void moveOrderTest() throws Exception {
        PurchaseReceivedOrderStatus purchaseReceivedOrderStatus = spy(new PurchaseReceivedOrderStatus());
        PurchaseOrder purchaseOrder = spy(new PurchaseOrder());

        PowerMockito.doReturn(purchaseOrderPersistence).when(purchaseReceivedOrderStatus, GET_PERSISTENCE_METHOD_NAME);
        PowerMockito.doNothing().when(purchaseOrderPersistence).changeStatus(anyString(), anyString());

        purchaseReceivedOrderStatus.moveOrder(purchaseOrder);
        verify(purchaseOrderPersistence, times(1)).changeStatus(anyString(), anyString());
    }
}
