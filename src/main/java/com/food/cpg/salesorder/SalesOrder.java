package com.food.cpg.salesorder;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.food.cpg.authentication.AuthenticationSessionDetails;

public class SalesOrder implements ISalesOrder {

    private static final String SO_TIME_FORMAT = "ddMMHHmm";
    private static final String SO_PREFIX = "SO-";
    private String orderNumber;
    private SalesOrderStatus salesOrderStatus;
    private int manufacturerId;
    private int itemId;
    private int packageId;
    private double packageCost;
    private double shippingCost;
    private double tax;
    private double totalCost;
    private boolean isForCharity = false;
    private String buyerDetails;
    private Timestamp statusChangeDate;


    public SalesOrder() {
        String generatedOrderNumber = generateOrderNumber();
        setOrderNumber(generatedOrderNumber);
    }

    @Override
    public String getOrderNumber() {
        return orderNumber;
    }

    @Override
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public int getManufacturerId() {
        return manufacturerId;
    }

    @Override
    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    @Override
    public int getItemId() {
        return itemId;
    }

    @Override
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @Override
    public int getPackageId() {
        return packageId;
    }

    @Override
    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    @Override
    public double getPackageCost() {
        return packageCost;
    }

    @Override
    public void setPackageCost(double packageCost) {
        this.packageCost = packageCost;
    }

    @Override
    public double getShippingCost() {
        return shippingCost;
    }

    @Override
    public void setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }

    @Override
    public double getTax() {
        return tax;
    }

    @Override
    public void setTax(double tax) {
        this.tax = tax;
    }

    @Override
    public double getTotalCost() {
        return totalCost;
    }

    @Override
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public boolean getIsForCharity() {
        return isForCharity;
    }

    @Override
    public void setIsForCharity(boolean isForCharity) {
        this.isForCharity = isForCharity;
    }

    @Override
    public String getBuyerDetails() {
        return buyerDetails;
    }

    @Override
    public void setBuyerDetails(String buyerDetails) {
        this.buyerDetails = buyerDetails;
    }

    @Override
    public SalesOrderStatus getSalesOrderStatus() {
        return salesOrderStatus;
    }

    @Override
    public void setSalesOrderStatus(SalesOrderStatus salesOrderStatus) {
        this.salesOrderStatus = salesOrderStatus;
    }

    @Override
    public Timestamp getStatusChangeDate() {
        return statusChangeDate;
    }

    @Override
    public void setStatusChangeDate(Timestamp statusChangeDate) {
        this.statusChangeDate = statusChangeDate;
    }

    @Override
    public List<ISalesOrder> getAllOpenOrders() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        return getPersistence().getAllOpenOrders(loggedInManufacturerId);
    }

    @Override
    public List<ISalesOrder> getAllPackagedOrders() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        return getPersistence().getAllPackagedOrders(loggedInManufacturerId);
    }

    @Override
    public List<ISalesOrder> getAllShippedOrders() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        return getPersistence().getAllShippedOrders(loggedInManufacturerId);
    }

    private String generateOrderNumber() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(SO_TIME_FORMAT);
        LocalDateTime currentDateTime = LocalDateTime.now();
        String formattedCurrentDateTime = dateTimeFormatter.format(currentDateTime);

        return SO_PREFIX + formattedCurrentDateTime;
    }

    @Override
    public List<ISalesOrder> getAllPaidOrders() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        return getPersistence().getAllPaidOrders(loggedInManufacturerId);
    }

    @Override
    public void load() {
        getPersistence().load(this);
    }

    @Override
    public void delete() {
        getPersistence().delete(this.getOrderNumber());
    }

    @Override
    public void save() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        this.setManufacturerId(loggedInManufacturerId);
        getPersistence().save(this);
    }

    @Override
    public void calculateTotalCost() {
        double cost;
        cost = this.getPackageCost();
        cost += this.getShippingCost();
        cost += (cost * this.getTax() / 100);
        this.setTotalCost(cost);
    }

    @Override
    public void moveOrderToNextStage() {
        this.getSalesOrderStatus().moveOrder(this);
    }

    private ISalesOrderPersistence getPersistence() {
        return SalesOrderFactory.instance().makeSalesOrderPersistence();
    }

    private int getLoggedInManufacturerId() {
        AuthenticationSessionDetails authenticationSessionDetails = AuthenticationSessionDetails.getInstance();
        return authenticationSessionDetails.getAuthenticatedUserId();
    }
}
