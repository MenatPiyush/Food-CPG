package com.food.cpg.analytics;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.food.cpg.authentication.AuthenticationSessionDetails;
import com.food.cpg.salesorder.ISalesOrder;
import com.food.cpg.salesorder.ISalesOrderPersistence;
import com.food.cpg.salesorder.SalesOrderFactory;

public class SalesPerformance {

    private String month;
    private int totalOrders;
    private double totalSales;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }


    public Map<String, SalesPerformance> generateSalesPerformance() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        Map<String, SalesPerformance> salesPerformances = new HashMap<>();

        List<ISalesOrder> salesOrders = getSalesOrderPersistence().getAllPackagedOrders(loggedInManufacturerId);

        for (ISalesOrder salesOrder : salesOrders) {
            String monthname = getMonthFromDate(salesOrder.getStatusChangeDate());
            if (salesPerformances.containsKey(monthname)) {
                SalesPerformance salesPerformance = salesPerformances.get(monthname);
                salesPerformance.setTotalOrders(salesPerformance.getTotalOrders() + 1);
                salesPerformance.setTotalSales(salesPerformance.getTotalSales() + salesOrder.getTotalCost());
                salesPerformances.replace(monthname, salesPerformance);
            } else {
                SalesPerformance salesPerformance = new SalesPerformance();
                salesPerformance.setMonth(monthname);
                salesPerformance.setTotalOrders(1);
                salesPerformance.setTotalSales(salesOrder.getTotalCost());
                salesPerformances.put(monthname, salesPerformance);
            }

        }
        return salesPerformances;
    }

    private ISalesOrderPersistence getSalesOrderPersistence() {
        return SalesOrderFactory.instance().makeSalesOrderPersistence();
    }

    private int getLoggedInManufacturerId() {
        AuthenticationSessionDetails authenticationSessionDetails = AuthenticationSessionDetails.getInstance();
        return authenticationSessionDetails.getAuthenticatedUserId();
    }

    public String getMonthFromDate(Date date) {
        int monthNumber = date.getMonth();
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[monthNumber];
    }
}
