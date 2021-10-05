package com.food.cpg.salesorder;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.food.cpg.manufacturer.IManufacturer;
import com.food.cpg.manufacturer.IManufacturerPersistence;
import com.food.cpg.manufacturer.ManufacturerFactory;
import com.food.cpg.notification.INotification;
import com.food.cpg.notification.NotificationFactory;

@Component
public class SalesOrderWatcher {

    private static final int DUE_DAY_FOR_PAYMENT = 7;
    private static final String SALES_ORDER_PAYMENT_OVERDUE_MESSAGE = "Sales order %s payment due date was %s.";
    private static final String SALES_ORDER_SCHEDULING_CRON = "0 0 0 * * *";

    @Scheduled(cron = SALES_ORDER_SCHEDULING_CRON)
    public void checkSalesOrdersForDueDate() {
        List<IManufacturer> manufacturers = getManufacturerPersistence().getAll();

        for (IManufacturer manufacturer : manufacturers) {
            checkSalesOrdersForDueDateByManufacturer(manufacturer.getId());
        }
    }

    public void checkSalesOrdersForDueDateByManufacturer(int manufacturerId) {
        List<ISalesOrder> salesOrders = getSalesOrderPersistence().getAllShippedOrders(manufacturerId);

        for (ISalesOrder salesOrder : salesOrders) {
            if (isSalesOrderPaymentOverdue(salesOrder)) {
                Date salesOrderDueDate = calculateSalesOrderDueDate(salesOrder);
                String notificationContent = String.format(SALES_ORDER_PAYMENT_OVERDUE_MESSAGE, salesOrder.getOrderNumber(), salesOrderDueDate);

                INotification notification = NotificationFactory.instance().makeNotification(manufacturerId, notificationContent, Timestamp.from(Instant.now()));
                notification.send();
            }
        }
    }

    public boolean isSalesOrderPaymentOverdue(ISalesOrder salesOrder) {
        Date salesOrderDueDate = calculateSalesOrderDueDate(salesOrder);
        Date currentDate = new Date();

        if (currentDate.compareTo(salesOrderDueDate) > 0) {
            return true;
        }
        return false;
    }

    public Date calculateSalesOrderDueDate(ISalesOrder salesOrder) {
        Timestamp salesOrderShippedTimestamp = salesOrder.getStatusChangeDate();

        Calendar salesOrderDueDate = Calendar.getInstance();
        salesOrderDueDate.setTime(salesOrderShippedTimestamp);
        salesOrderDueDate.add(Calendar.DATE, DUE_DAY_FOR_PAYMENT);

        return salesOrderDueDate.getTime();
    }

    private ISalesOrderPersistence getSalesOrderPersistence() {
        return SalesOrderFactory.instance().makeSalesOrderPersistence();
    }

    private IManufacturerPersistence getManufacturerPersistence() {
        return ManufacturerFactory.instance().makeManufacturerPersistence();
    }
}
