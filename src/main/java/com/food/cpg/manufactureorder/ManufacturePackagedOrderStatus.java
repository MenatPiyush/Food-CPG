package com.food.cpg.manufactureorder;

public class ManufacturePackagedOrderStatus extends ManufactureOrderStatus {

    public ManufacturePackagedOrderStatus() {
        this.orderStatus = Status.PACKAGED;
    }

    @Override
    public void moveOrder(IManufactureOrder manufactureOrder) {
        String orderNumber = manufactureOrder.getOrderNumber();

        getPersistence().changeStatus(orderNumber, Status.STORED.name());
    }
}
