package com.food.cpg.purchaseorder;

import java.sql.Timestamp;
import java.util.List;

public interface IPurchaseOrder {
    String getOrderNumber();

    void setOrderNumber(String orderNumber);

    Integer getManufacturerId();

    void setManufacturerId(Integer manufacturerId);

    Integer getVendorId();

    void setVendorId(Integer vendorId);

    Double getTotalCost();

    void setTotalCost(Double totalCost);

    Timestamp getStatusChangeDate();

    void setStatusChangeDate(Timestamp statusChangeDate);

    List<PurchaseOrderRawMaterial> getPurchaseOrderRawMaterials();

    void setPurchaseOrderRawMaterials(List<PurchaseOrderRawMaterial> purchaseOrderRawMaterials);

    PurchaseOrderStatus getPurchaseOrderStatus();

    void setPurchaseOrderStatus(PurchaseOrderStatus purchaseOrderStatus);

    void addPurchaseOrderRawMaterials(PurchaseOrderRawMaterial purchaseOrderRawMaterial);

    void save();

    void calculateTotalCost();

    List<IPurchaseOrder> getAllOpenOrders();

    List<IPurchaseOrder> getAllPlacedOrders();

    List<IPurchaseOrder> getAllReceivedOrders();

    void delete();

    void load();

    void moveOrderToNextStage();
}
