package com.food.cpg.manufactureorder;

import com.food.cpg.inventory.IItemInventory;
import com.food.cpg.inventory.InventoryFactory;

public class ManufactureManufacturedOrderStatus extends ManufactureOrderStatus {

    public ManufactureManufacturedOrderStatus() {
        this.orderStatus = Status.MANUFACTURED;
    }

    @Override
    public void moveOrder(IManufactureOrder manufactureOrder) {
        String orderNumber = manufactureOrder.getOrderNumber();
        getPersistence().changeStatus(orderNumber, Status.PACKAGED.name());

        increaseItemQuantity(manufactureOrder);
    }

    public void increaseItemQuantity(IManufactureOrder manufactureOrder) {
        IItemInventory itemInventory = InventoryFactory.instance().makeItemInventory();
        Integer itemID = manufactureOrder.getItemId();
        Double itemQuantity = manufactureOrder.getItemQuantity();

        itemInventory.setItemId(itemID);
        itemInventory.setItemQuantity(itemQuantity);
        itemInventory.increaseQuantity();
    }


}
