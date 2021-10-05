package com.food.cpg.manufactureorder;

public class ManufactureOpenOrderStatus extends ManufactureOrderStatus {

    public ManufactureOpenOrderStatus() {
        this.orderStatus = Status.OPEN;
    }

    @Override
    public void moveOrder(IManufactureOrder manufactureOrder) {
        String orderNumber = manufactureOrder.getOrderNumber();
        getPersistence().changeStatus(orderNumber, Status.MANUFACTURED.name());
    }
}
