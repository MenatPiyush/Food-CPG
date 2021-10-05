package com.food.cpg.manufactureorder;

import com.food.cpg.authentication.AuthenticationSessionDetails;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ManufactureOrder implements IManufactureOrder {

    private static final String MO_TIME_FORMAT = "ddMMHHmm";
    private static final String MO_PREFIX = "MO-";

    private String orderNumber;
    private Integer manufacturerId;
    private Integer itemId;
    private Double itemQuantity;
    private String itemQuantityUOM;
    private String orderStatus;
    private Timestamp orderCreationDate;
    private Timestamp orderManufacturedDate;
    private Timestamp orderPackedDate;
    private Double manufacturingCost;
    private Double tax;
    private Double cost;
    private ManufactureOrderStatus manufactureOrderStatus;
    private Timestamp statusChangeDate;

    public ManufactureOrder() {
        String generatedOrderNumber = generateOrderNumber();
        setOrderNumber(generatedOrderNumber);
    }

    public static String getMoTimeFormat() {
        return MO_TIME_FORMAT;
    }

    public static String getMoPrefix() {
        return MO_PREFIX;
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
    public Integer getManufacturerId() {
        return manufacturerId;
    }

    @Override
    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    @Override
    public Integer getItemId() {
        return itemId;
    }

    @Override
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    @Override
    public Double getItemQuantity() {
        return itemQuantity;
    }

    @Override
    public void setItemQuantity(Double itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    @Override
    public String getItemQuantityUOM() {
        return itemQuantityUOM;
    }

    @Override
    public void setItemQuantityUOM(String itemQuantityUOM) {
        this.itemQuantityUOM = itemQuantityUOM;
    }

    @Override
    public String getOrderStatus() {
        return orderStatus;
    }

    @Override
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public Timestamp getOrderCreationDate() {
        return orderCreationDate;
    }

    @Override
    public void setOrderCreationDate(Timestamp orderCreationDate) {
        this.orderCreationDate = orderCreationDate;
    }

    @Override
    public Timestamp getOrderManufacturedDate() {
        return orderManufacturedDate;
    }

    @Override
    public void setOrderManufacturedDate(Timestamp orderManufacturedDate) {
        this.orderManufacturedDate = orderManufacturedDate;
    }

    @Override
    public Timestamp getOrderPackedDate() {
        return orderPackedDate;
    }

    @Override
    public void setOrderPackedDate(Timestamp orderPackedDate) {
        this.orderPackedDate = orderPackedDate;
    }

    @Override
    public Double getManufacturingCost() {
        return manufacturingCost;
    }

    @Override
    public void setManufacturingCost(Double manufacturingCost) {
        this.manufacturingCost = manufacturingCost;
    }

    @Override
    public Double getTax() {
        return tax;
    }

    @Override
    public void setTax(Double tax) {
        this.tax = tax;
    }

    @Override
    public Double getCost() {
        return cost;
    }

    @Override
    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Override
    public ManufactureOrderStatus getManufactureOrderStatus() {
        return manufactureOrderStatus;
    }

    @Override
    public void setManufactureOrderStatus(ManufactureOrderStatus manufactureOrderStatus) {
        this.manufactureOrderStatus = manufactureOrderStatus;
    }

    @Override
    public Timestamp getStatusChangeDate() {
        return statusChangeDate;
    }

    @Override
    public void setStatusChangeDate(Timestamp statusChangeDate) {
        this.statusChangeDate = statusChangeDate;
    }

    private String generateOrderNumber() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(MO_TIME_FORMAT);
        LocalDateTime currentDateTime = LocalDateTime.now();
        String formattedCurrentDateTime = dateTimeFormatter.format(currentDateTime);

        return MO_PREFIX + formattedCurrentDateTime;
    }

    @Override
    public List<IManufactureOrder> getAllOpenOrders() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        return getPersistence().getAllOpenOrders(loggedInManufacturerId);
    }

    @Override
    public List<IManufactureOrder> getAllManufacturedOrders() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        return getPersistence().getAllManufacturedOrders(loggedInManufacturerId);
    }

    @Override
    public List<IManufactureOrder> getAllPackagedOrders() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        return getPersistence().getAllPackagedOrders(loggedInManufacturerId);
    }

    @Override
    public List<IManufactureOrder> getAllStoredOrders() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        return getPersistence().getAllStoredOrders(loggedInManufacturerId);
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
        Double totalCost = 0.0;
        totalCost = getPersistence().loadItemCost(this.itemId);
        this.setCost(totalCost);
        Double costForAllQauntities = this.getCost() * this.getItemQuantity();
        totalCost = costForAllQauntities;
        totalCost += this.getManufacturingCost();
        Double tax = this.getTax();
        totalCost += (totalCost * tax / 100);
        this.setCost(totalCost);
    }

    @Override
    public void moveOrderToNextStage() {
        this.getManufactureOrderStatus().moveOrder(this);
    }

    private IManufactureOrderPersistence getPersistence() {
        return ManufactureOrderFactory.instance().makeManufactureOrderPersistence();
    }

    private int getLoggedInManufacturerId() {
        AuthenticationSessionDetails authenticationSessionDetails = AuthenticationSessionDetails.getInstance();
        return authenticationSessionDetails.getAuthenticatedUserId();
    }
}
