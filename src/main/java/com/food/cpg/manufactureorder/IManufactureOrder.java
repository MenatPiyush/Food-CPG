package com.food.cpg.manufactureorder;

import java.sql.Timestamp;
import java.util.List;

public interface IManufactureOrder {

    String getOrderNumber();

    void setOrderNumber(String orderNumber);

    Integer getManufacturerId();

    void setManufacturerId(Integer manufacturerId);

    Integer getItemId();

    void setItemId(Integer itemId);

    Double getItemQuantity();

    void setItemQuantity(Double itemQuantity);

    String getItemQuantityUOM();

    void setItemQuantityUOM(String itemQuantityUOM);

    String getOrderStatus();

    void setOrderStatus(String orderStatus);

    Timestamp getOrderCreationDate();

    void setOrderCreationDate(Timestamp orderCreationDate);

    Timestamp getOrderManufacturedDate();

    void setOrderManufacturedDate(Timestamp orderManufacturedDate);

    Timestamp getOrderPackedDate();

    void setOrderPackedDate(Timestamp orderPackedDate);

    Double getManufacturingCost();

    void setManufacturingCost(Double manufacturingCost);

    Double getTax();

    void setTax(Double tax);

    Double getCost();

    void setCost(Double cost);

    ManufactureOrderStatus getManufactureOrderStatus();

    void setManufactureOrderStatus(ManufactureOrderStatus manufactureOrderStatus);

    Timestamp getStatusChangeDate();

    void setStatusChangeDate(Timestamp statusChangeDate);

    List<IManufactureOrder> getAllOpenOrders();

    List<IManufactureOrder> getAllManufacturedOrders();

    List<IManufactureOrder> getAllPackagedOrders();

    List<IManufactureOrder> getAllStoredOrders();

    void load();

    void delete();

    void save();

    void calculateTotalCost();

    void moveOrderToNextStage();

}
