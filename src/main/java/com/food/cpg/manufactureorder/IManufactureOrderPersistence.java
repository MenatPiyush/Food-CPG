package com.food.cpg.manufactureorder;

import java.util.List;

public interface IManufactureOrderPersistence {

    List<IManufactureOrder> getAllOpenOrders(int manufacturerId);

    List<IManufactureOrder> getAllManufacturedOrders(int manufacturerId);

    List<IManufactureOrder> getAllPackagedOrders(int manufacturerId);

    List<IManufactureOrder> getAllStoredOrders(int manufacturerId);

    List<IManufactureOrder> getAllOrders(int manufacturerId, String orderStatus);


    void load(IManufactureOrder manufactureOrder);

    void save(IManufactureOrder manufactureOrder);

    void delete(String orderNumber);

    Double loadItemCost(int itemId);

    void changeStatus(String orderNumber, String orderStatus);

}
