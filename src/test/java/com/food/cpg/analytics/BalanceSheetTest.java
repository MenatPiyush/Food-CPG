package com.food.cpg.analytics;

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

import com.food.cpg.purchaseorder.IPurchaseOrder;
import com.food.cpg.purchaseorder.IPurchaseOrderPersistence;
import com.food.cpg.salesorder.ISalesOrder;
import com.food.cpg.salesorder.ISalesOrderPersistence;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.powermock.api.mockito.PowerMockito.spy;

@RunWith(PowerMockRunner.class)
@PrepareForTest(BalanceSheet.class)
public class BalanceSheetTest {

    private static final double DELTA = 1e-15;
    private static final String GET_PURCHASE_ORDER_PERSISTENCE_METHOD = "getPurchaseOrderPersistence";
    private static final String GET_SALES_ORDER_PERSISTENCE_METHOD = "getSalesOrderPersistence";

    @Mock
    IPurchaseOrderPersistence purchaseOrderPersistence;

    @Mock
    ISalesOrderPersistence salesOrderPersistence;

    @Mock
    IPurchaseOrder purchaseOrder;

    @Mock
    ISalesOrder salesOrder;

    @Test
    public void generateBalanceSheetTest() throws Exception {
        BalanceSheet balanceSheet = spy(new BalanceSheet());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -2);

        List<IPurchaseOrder> purchaseOrders = new ArrayList<>();
        purchaseOrders.add(purchaseOrder);

        List<ISalesOrder> salesOrders = new ArrayList<>();
        salesOrders.add(salesOrder);

        PowerMockito.doReturn(purchaseOrderPersistence).when(balanceSheet, GET_PURCHASE_ORDER_PERSISTENCE_METHOD);
        PowerMockito.doReturn(purchaseOrders).when(purchaseOrderPersistence).getReceivedPurchaseOrder(anyInt());
        PowerMockito.doReturn(new Timestamp(calendar.getTimeInMillis())).when(purchaseOrder).getStatusChangeDate();
        PowerMockito.doReturn(10.0).when(purchaseOrder).getTotalCost();

        PowerMockito.doReturn(salesOrderPersistence).when(balanceSheet, GET_SALES_ORDER_PERSISTENCE_METHOD);
        PowerMockito.doReturn(salesOrders).when(salesOrderPersistence).getAllPaidOrders(anyInt());
        PowerMockito.doReturn(new Timestamp(calendar.getTimeInMillis())).when(salesOrder).getStatusChangeDate();
        PowerMockito.doReturn(30.0).when(salesOrder).getTotalCost();
        PowerMockito.doReturn(false).when(salesOrder).getIsForCharity();

        balanceSheet.generateBalanceSheet();

        Assert.assertEquals(10.0, balanceSheet.getExpenditure(), DELTA);
        Assert.assertEquals(30.0, balanceSheet.getRevenue(), DELTA);
        Assert.assertEquals(0.0, balanceSheet.getAmountUsedForCharity(), DELTA);
        Assert.assertEquals(20.0, balanceSheet.getNetIncome(), DELTA);
    }
}
