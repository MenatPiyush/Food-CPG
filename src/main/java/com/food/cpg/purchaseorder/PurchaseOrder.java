package com.food.cpg.purchaseorder;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.food.cpg.authentication.AuthenticationSessionDetails;

public class PurchaseOrder implements IPurchaseOrder {
    private static final String PO_ORDER_TIME_FORMAT = "ddMMHHmmssSSS";
    private static final String PO_PREFIX = "PO-";

    private String orderNumber;
    private Integer manufacturerId;
    private Integer vendorId;
    private Timestamp statusChangeDate;
    private Double totalCost;
    private List<PurchaseOrderRawMaterial> purchaseOrderRawMaterials;
    private PurchaseOrderStatus purchaseOrderStatus;

    public PurchaseOrder() {
        String generatedOrderNumber = generateOrderNumber();
        setOrderNumber(generatedOrderNumber);
        setTotalCost(0.0);
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
    public Integer getVendorId() {
        return vendorId;
    }

    @Override
    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    @Override
    public Double getTotalCost() {
        return totalCost;
    }

    @Override
    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
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
    public List<PurchaseOrderRawMaterial> getPurchaseOrderRawMaterials() {
        return purchaseOrderRawMaterials;
    }

    @Override
    public void setPurchaseOrderRawMaterials(List<PurchaseOrderRawMaterial> purchaseOrderRawMaterials) {
        this.purchaseOrderRawMaterials = purchaseOrderRawMaterials;
    }

    @Override
    public PurchaseOrderStatus getPurchaseOrderStatus() {
        return purchaseOrderStatus;
    }

    @Override
    public void setPurchaseOrderStatus(PurchaseOrderStatus purchaseOrderStatus) {
        this.purchaseOrderStatus = purchaseOrderStatus;
    }

    @Override
    public void addPurchaseOrderRawMaterials(PurchaseOrderRawMaterial purchaseOrderRawMaterial) {
        if (this.purchaseOrderRawMaterials == null) {
            this.purchaseOrderRawMaterials = new ArrayList<>();
        }
        purchaseOrderRawMaterial.setPurchaseOrderNumber(this.getOrderNumber());
        this.purchaseOrderRawMaterials.add(purchaseOrderRawMaterial);
        this.calculateTotalCost();
    }

    @Override
    public void save() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        this.setManufacturerId(loggedInManufacturerId);

        getPersistence().save(this);
        for (IPurchaseOrderRawMaterial purchaseOrderRawMaterial : getPurchaseOrderRawMaterials()) {
            purchaseOrderRawMaterial.save();
        }
    }

    @Override
    public void calculateTotalCost() {
        double poRawMaterialCost = 0.0;

        for (IPurchaseOrderRawMaterial purchaseOrderRawMaterial : getPurchaseOrderRawMaterials()) {
            poRawMaterialCost += purchaseOrderRawMaterial.getRawMaterialCost();
        }

        this.setTotalCost(poRawMaterialCost);
    }

    @Override
    public List<IPurchaseOrder> getAllOpenOrders() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        return getPersistence().getOpenPurchaseOrder(loggedInManufacturerId);
    }

    @Override
    public List<IPurchaseOrder> getAllPlacedOrders() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        return getPersistence().getPlacedPurchaseOrder(loggedInManufacturerId);
    }

    @Override
    public List<IPurchaseOrder> getAllReceivedOrders() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        return getPersistence().getReceivedPurchaseOrder(loggedInManufacturerId);
    }

    @Override
    public void delete() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        this.setManufacturerId(loggedInManufacturerId);
        getPersistence().delete(this);
    }

    @Override
    public void load() {
        getPersistence().load(this);
    }

    @Override
    public void moveOrderToNextStage() {
        this.getPurchaseOrderStatus().moveOrder(this);
    }

    private String generateOrderNumber() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(PO_ORDER_TIME_FORMAT);
        LocalDateTime currentDateTime = LocalDateTime.now();
        String formattedCurrentDateTime = dateTimeFormatter.format(currentDateTime);

        return PO_PREFIX + formattedCurrentDateTime;
    }

    private IPurchaseOrderPersistence getPersistence() {
        return PurchaseOrderFactory.instance().makePurchaseOrderPersistence();
    }

    private int getLoggedInManufacturerId() {
        AuthenticationSessionDetails authenticationSessionDetails = AuthenticationSessionDetails.getInstance();
        return authenticationSessionDetails.getAuthenticatedUserId();
    }
}