package com.food.cpg.salesorder;

import java.util.List;

public interface ISalesOrderPersistence {

    List<ISalesOrder> getAllOpenOrders(int manufacturerId);

    List<ISalesOrder> getAllPackagedOrders(int manufacturerId);

    List<ISalesOrder> getAllShippedOrders(int manufacturerId);

    List<ISalesOrder> getAllPaidOrders(int manufacturerId);

    void load(ISalesOrder salesOrder);

    void save(ISalesOrder salesOrder);

    void delete(String orderNumber);

    void changeStatus(String orderNumber, String orderStatus);
}