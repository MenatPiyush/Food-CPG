package com.food.cpg.analytics;

import java.util.List;

import com.food.cpg.authentication.AuthenticationSessionDetails;
import com.food.cpg.manufactureorder.IManufactureOrder;
import com.food.cpg.manufactureorder.IManufactureOrderPersistence;
import com.food.cpg.manufactureorder.ManufactureOrderFactory;
import com.food.cpg.purchaseorder.IPurchaseOrder;
import com.food.cpg.purchaseorder.IPurchaseOrderPersistence;
import com.food.cpg.purchaseorder.PurchaseOrderFactory;
import com.food.cpg.salesorder.ISalesOrder;
import com.food.cpg.salesorder.ISalesOrderPersistence;
import com.food.cpg.salesorder.SalesOrderFactory;

public class OrderStatistics {

    private Integer totalOrders;
    private Integer totalOpenPurchaseOrders;
    private Integer totalPlacedPurchaseOrders;
    private Integer totalReceivedPurchaseOrders;
    private Integer totalPaidPurchaseOrders;
    private Integer totalPurchaseOrders;
    private Integer totalOpenManufactureOrders;
    private Integer totalManufacturedManufactureOrders;
    private Integer totalPackagedManufactureOrders;
    private Integer totalStoredManufactureOrders;
    private Integer totalManufactureOrders;
    private Integer totalOpenSalesOrders;
    private Integer totalPackagedSalesOrders;
    private Integer totalShippedSalesOrders;
    private Integer totalPaidSalesOrders;
    private Integer totalSalesOrders;

    public Integer getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Integer totalOrders) {
        this.totalOrders = totalOrders;
    }

    public Integer getTotalOpenPurchaseOrders() {
        return totalOpenPurchaseOrders;
    }

    public void setTotalOpenPurchaseOrders(Integer totalOpenPurchaseOrders) {
        this.totalOpenPurchaseOrders = totalOpenPurchaseOrders;
    }

    public Integer getTotalPlacedPurchaseOrders() {
        return totalPlacedPurchaseOrders;
    }

    public void setTotalPlacedPurchaseOrders(Integer totalPlacedPurchaseOrders) {
        this.totalPlacedPurchaseOrders = totalPlacedPurchaseOrders;
    }

    public Integer getTotalReceivedPurchaseOrders() {
        return totalReceivedPurchaseOrders;
    }

    public void setTotalReceivedPurchaseOrders(Integer totalReceivedPurchaseOrders) {
        this.totalReceivedPurchaseOrders = totalReceivedPurchaseOrders;
    }

    public Integer getTotalPaidPurchaseOrders() {
        return totalPaidPurchaseOrders;
    }

    public void setTotalPaidPurchaseOrders(Integer totalPaidPurchaseOrders) {
        this.totalPaidPurchaseOrders = totalPaidPurchaseOrders;
    }

    public Integer getTotalOpenManufactureOrders() {
        return totalOpenManufactureOrders;
    }

    public void setTotalOpenManufactureOrders(Integer totalOpenManufactureOrders) {
        this.totalOpenManufactureOrders = totalOpenManufactureOrders;
    }

    public Integer getTotalManufacturedManufactureOrders() {
        return totalManufacturedManufactureOrders;
    }

    public void setTotalManufacturedManufactureOrders(Integer totalManufacturedManufactureOrders) {
        this.totalManufacturedManufactureOrders = totalManufacturedManufactureOrders;
    }

    public Integer getTotalPackagedManufactureOrders() {
        return totalPackagedManufactureOrders;
    }

    public void setTotalPackagedManufactureOrders(Integer totalPackagedManufactureOrders) {
        this.totalPackagedManufactureOrders = totalPackagedManufactureOrders;
    }

    public Integer getTotalOpenSalesOrder() {
        return totalOpenSalesOrders;
    }

    public void setTotalOpenSalesOrder(Integer totalOpenSalesOrder) {
        this.totalOpenSalesOrders = totalOpenSalesOrder;
    }

    public Integer getTotalPackagedSalesOrder() {
        return totalPackagedSalesOrders;
    }

    public void setTotalPackagedSalesOrder(Integer totalPackagedSalesOrder) {
        this.totalPackagedSalesOrders = totalPackagedSalesOrder;
    }

    public Integer getTotalShippedSalesOrder() {
        return totalShippedSalesOrders;
    }

    public void setTotalShippedSalesOrder(Integer totalShippedSalesOrder) {
        this.totalShippedSalesOrders = totalShippedSalesOrder;
    }

    public Integer getTotalPaidSalesOrder() {
        return totalPaidSalesOrders;
    }

    public void setTotalPaidSalesOrder(Integer totalPaidSalesOrder) {
        this.totalPaidSalesOrders = totalPaidSalesOrder;
    }

    public Integer getTotalPurchaseOrders() {
        return totalPurchaseOrders;
    }

    public void setTotalPurchaseOrders(Integer totalPurchaseOrders) {
        this.totalPurchaseOrders = totalPurchaseOrders;
    }

    public Integer getTotalStoredManufactureOrders() {
        return totalStoredManufactureOrders;
    }

    public void setTotalStoredManufactureOrders(Integer totalStoredManufactureOrders) {
        this.totalStoredManufactureOrders = totalStoredManufactureOrders;
    }

    public Integer getTotalManufactureOrders() {
        return totalManufactureOrders;
    }

    public void setTotalManufactureOrders(Integer totalManufactureOrders) {
        this.totalManufactureOrders = totalManufactureOrders;
    }

    public Integer getTotalOpenSalesOrders() {
        return totalOpenSalesOrders;
    }

    public void setTotalOpenSalesOrders(Integer totalOpenSalesOrders) {
        this.totalOpenSalesOrders = totalOpenSalesOrders;
    }

    public Integer getTotalPackagedSalesOrders() {
        return totalPackagedSalesOrders;
    }

    public void setTotalPackagedSalesOrders(Integer totalPackagedSalesOrders) {
        this.totalPackagedSalesOrders = totalPackagedSalesOrders;
    }

    public Integer getTotalShippedSalesOrders() {
        return totalShippedSalesOrders;
    }

    public void setTotalShippedSalesOrders(Integer totalShippedSalesOrders) {
        this.totalShippedSalesOrders = totalShippedSalesOrders;
    }

    public Integer getTotalPaidSalesOrders() {
        return totalPaidSalesOrders;
    }

    public void setTotalPaidSalesOrders(Integer totalPaidSalesOrders) {
        this.totalPaidSalesOrders = totalPaidSalesOrders;
    }

    public Integer getTotalSalesOrders() {
        return totalSalesOrders;
    }

    public void setTotalSalesOrders(Integer totalSalesOrders) {
        this.totalSalesOrders = totalSalesOrders;
    }

    public void generateOrderStatistics() {
        int loggedInManufacturerId = getLoggedInManufacturerId();

        generatePurchaseOrderStatistics(loggedInManufacturerId);
        generateManufactureOrderStatistics(loggedInManufacturerId);
        generateSalesOrderStatistics(loggedInManufacturerId);

        calculateTotalOrderNumber();

    }

    public void generatePurchaseOrderStatistics(int loggedInManufacturerId) {
        List<IPurchaseOrder> openPurchaseOrders = getPurchaseOrderPersistence().getOpenPurchaseOrder(loggedInManufacturerId);
        List<IPurchaseOrder> placedPurchaseOrders = getPurchaseOrderPersistence().getPlacedPurchaseOrder(loggedInManufacturerId);
        List<IPurchaseOrder> receivedPurchaseOrders = getPurchaseOrderPersistence().getReceivedPurchaseOrder(loggedInManufacturerId);
        List<IPurchaseOrder> paidPurchaseOrders = getPurchaseOrderPersistence().getPaidPurchaseOrder(loggedInManufacturerId);

        totalOpenPurchaseOrders = openPurchaseOrders.size();
        totalPlacedPurchaseOrders = placedPurchaseOrders.size();
        totalReceivedPurchaseOrders = receivedPurchaseOrders.size();
        totalPaidPurchaseOrders = paidPurchaseOrders.size();

        totalPurchaseOrders = totalOpenPurchaseOrders + totalPlacedPurchaseOrders + totalReceivedPurchaseOrders + totalPaidPurchaseOrders;
    }

    public void generateManufactureOrderStatistics(int loggedInManufacturerId) {
        List<IManufactureOrder> openManufactureOrders = getManufactureOrderPersistence().getAllOpenOrders(loggedInManufacturerId);
        List<IManufactureOrder> manufacturedManufactureOrders = getManufactureOrderPersistence().getAllManufacturedOrders(loggedInManufacturerId);
        List<IManufactureOrder> packagedManufactureOrders = getManufactureOrderPersistence().getAllPackagedOrders(loggedInManufacturerId);
        List<IManufactureOrder> storedManufactureOrders = getManufactureOrderPersistence().getAllStoredOrders(loggedInManufacturerId);

        totalOpenManufactureOrders = openManufactureOrders.size();
        totalManufacturedManufactureOrders = manufacturedManufactureOrders.size();
        totalPackagedManufactureOrders = packagedManufactureOrders.size();
        totalStoredManufactureOrders = storedManufactureOrders.size();

        totalManufactureOrders = totalOpenManufactureOrders + totalManufacturedManufactureOrders + totalPackagedManufactureOrders + totalStoredManufactureOrders;
    }

    public void generateSalesOrderStatistics(int loggedInManufacturerId) {
        List<ISalesOrder> openSalesOrders = getSalesOrderPersistence().getAllOpenOrders(loggedInManufacturerId);
        List<ISalesOrder> packagedSalesOrders = getSalesOrderPersistence().getAllPackagedOrders(loggedInManufacturerId);
        List<ISalesOrder> shippedSalesOrders = getSalesOrderPersistence().getAllShippedOrders(loggedInManufacturerId);
        List<ISalesOrder> paidSalesOrders = getSalesOrderPersistence().getAllPaidOrders(loggedInManufacturerId);

        totalOpenSalesOrders = openSalesOrders.size();
        totalPackagedSalesOrders = packagedSalesOrders.size();
        totalShippedSalesOrders = shippedSalesOrders.size();
        totalPaidSalesOrders = paidSalesOrders.size();

        totalSalesOrders = totalOpenSalesOrders + totalPackagedSalesOrders + totalShippedSalesOrders + totalPaidSalesOrders;
    }

    public void calculateTotalOrderNumber() {

        totalOrders = totalPurchaseOrders + totalManufactureOrders + totalSalesOrders;
    }

    private IManufactureOrderPersistence getManufactureOrderPersistence() {
        return ManufactureOrderFactory.instance().makeManufactureOrderPersistence();
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
