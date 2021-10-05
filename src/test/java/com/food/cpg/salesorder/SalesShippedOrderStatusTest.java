package com.food.cpg.salesorder;

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
@PrepareForTest(SalesShippedOrderStatus.class)
public class SalesShippedOrderStatusTest {

    private static final String GET_PERSISTENCE_METHOD_NAME = "getPersistence";
    private static final String TEST_SALES_ORDER_NUMBER = "SO-123";

    @Mock
    ISalesOrderPersistence salesOrderPersistence;
    @Mock
    ISalesOrder salesOrder;

    @Test
    public void getOrderStatusTest() {
        SalesShippedOrderStatus salesShippedOrderStatus = new SalesShippedOrderStatus();

        Assert.assertEquals(SalesOrderStatus.Status.SHIPPED, salesShippedOrderStatus.getOrderStatus());
    }

    @Test
    public void moveOrderTest() throws Exception {
        SalesShippedOrderStatus salesShippedOrderStatus = spy(new SalesShippedOrderStatus());

        PowerMockito.doReturn(salesOrderPersistence).when(salesShippedOrderStatus, GET_PERSISTENCE_METHOD_NAME);
        PowerMockito.doNothing().when(salesOrderPersistence).changeStatus(anyString(), anyString());
        PowerMockito.doReturn(TEST_SALES_ORDER_NUMBER).when(salesOrder).getOrderNumber();

        salesShippedOrderStatus.moveOrder(salesOrder);
        verify(salesOrderPersistence, times(1)).changeStatus(anyString(), anyString());
    }
}
