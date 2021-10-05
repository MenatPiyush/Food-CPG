package com.food.cpg.salesorder;

import java.sql.Timestamp;
import java.util.List;

public interface ISalesOrder {
    String getOrderNumber();

    void setOrderNumber(String orderNumber);

    int getManufacturerId();

    void setManufacturerId(int manufacturerId);

    int getItemId();

    void setItemId(int itemId);

    int getPackageId();

    void setPackageId(int packageId);

    double getPackageCost();

    void setPackageCost(double packageCost);

    double getShippingCost();

    void setShippingCost(double shippingCost);

    double getTax();

    void setTax(double tax);

    double getTotalCost();

    void setTotalCost(double totalCost);

    boolean getIsForCharity();

    void setIsForCharity(boolean isForCharity);

    String getBuyerDetails();

    void setBuyerDetails(String buyerDetails);

    SalesOrderStatus getSalesOrderStatus();

    void setSalesOrderStatus(SalesOrderStatus salesOrderStatus);

    Timestamp getStatusChangeDate();

    void setStatusChangeDate(Timestamp statusChangeDate);

    List<ISalesOrder> getAllOpenOrders();

    List<ISalesOrder> getAllPackagedOrders();

    List<ISalesOrder> getAllShippedOrders();

    List<ISalesOrder> getAllPaidOrders();

    void load();

    void delete();

    void save();

    void calculateTotalCost();

    void moveOrderToNextStage();
}
