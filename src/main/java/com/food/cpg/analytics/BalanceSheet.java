package com.food.cpg.analytics;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.food.cpg.authentication.AuthenticationSessionDetails;
import com.food.cpg.purchaseorder.IPurchaseOrder;
import com.food.cpg.purchaseorder.IPurchaseOrderPersistence;
import com.food.cpg.purchaseorder.PurchaseOrderFactory;
import com.food.cpg.salesorder.ISalesOrder;
import com.food.cpg.salesorder.ISalesOrderPersistence;
import com.food.cpg.salesorder.SalesOrderFactory;

import static java.util.Calendar.DATE;

public class BalanceSheet {

    private static final int DAYS_BETWEEN_START_DATE_AND_TODAY = 7;
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private double expenditure;
    private double revenue;
    private double amountUsedForCharity;
    private double netIncome;

    @DateTimeFormat(pattern = DATE_FORMAT)
    private Date startDate;

    @DateTimeFormat(pattern = DATE_FORMAT)
    private Date endDate;

    public BalanceSheet() {
        initializeBalanceSheet();
    }

    public double getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(double expenditure) {
        this.expenditure = expenditure;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public double getAmountUsedForCharity() {
        return amountUsedForCharity;
    }

    public void setAmountUsedForCharity(double amountUsedForCharity) {
        this.amountUsedForCharity = amountUsedForCharity;
    }

    public double getNetIncome() {
        return netIncome;
    }

    public void setNetIncome(double netIncome) {
        this.netIncome = netIncome;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void initializeBalanceSheet() {
        Date endDateValue = new Date();

        Calendar startDateValue = Calendar.getInstance();
        startDateValue.setTime(endDateValue);
        startDateValue.add(DATE, (DAYS_BETWEEN_START_DATE_AND_TODAY * -1));

        this.setStartDate(startDateValue.getTime());
        this.setEndDate(endDateValue);
    }

    public void generateBalanceSheet() {
        int loggedInManufacturerId = getLoggedInManufacturerId();

        List<IPurchaseOrder> purchaseOrders = getPurchaseOrderPersistence().getReceivedPurchaseOrder(loggedInManufacturerId);

        List<ISalesOrder> salesOrders = getSalesOrderPersistence().getAllPaidOrders(loggedInManufacturerId);

        double totalExpenditure = 0.0;
        for (IPurchaseOrder purchaseOrder : purchaseOrders) {
            if (isDateInRange(purchaseOrder.getStatusChangeDate())) {
                totalExpenditure += purchaseOrder.getTotalCost();
            }
        }
        this.setExpenditure(totalExpenditure);

        double totalRevenue = 0.0;
        double totalAmountUsedForCharity = 0.0;
        for (ISalesOrder salesOrder : salesOrders) {
            if (isDateInRange(salesOrder.getStatusChangeDate())) {
                if (salesOrder.getIsForCharity()) {
                    totalAmountUsedForCharity += salesOrder.getTotalCost();
                }
                totalRevenue += salesOrder.getTotalCost();
            }
        }
        this.setRevenue(totalRevenue);
        this.setAmountUsedForCharity(totalAmountUsedForCharity);

        double finalNetIncome = totalRevenue - totalAmountUsedForCharity - totalExpenditure;
        this.setNetIncome(finalNetIncome);
    }

    public boolean isDateInRange(Date date) {
        if (date.compareTo(getStartDate()) >= 0 && date.compareTo(getEndDate()) <= 0) {
            return true;
        }
        return false;
    }

    private IPurchaseOrderPersistence getPurchaseOrderPersistence() {
        return PurchaseOrderFactory.instance().makePurchaseOrderPersistence();
    }

    private ISalesOrderPersistence getSalesOrderPersistence() {
        return SalesOrderFactory.instance().makeSalesOrderPersistence();
    }

    private int getLoggedInManufacturerId() {
        AuthenticationSessionDetails authenticationSessionDetails = AuthenticationSessionDetails.getInstance();
        return authenticationSessionDetails.getAuthenticatedUserId();
    }
}
