package com.food.cpg.salesorder;

import com.food.cpg.inventory.IItemInventory;
import com.food.cpg.inventory.InventoryFactory;
import com.food.cpg.packaging.IPackage;
import com.food.cpg.packaging.PackageFactory;

public class SalesPackagedOrderStatus extends SalesOrderStatus {

    public SalesPackagedOrderStatus() {
        this.orderStatus = Status.PACKAGED;
    }

    @Override
    public void moveOrder(ISalesOrder salesOrder) {
        getPersistence().changeStatus(salesOrder.getOrderNumber(), Status.SHIPPED.name());
        decreaseItemQuantity(salesOrder);
    }


    public void decreaseItemQuantity(ISalesOrder salesOrder) {
        IItemInventory itemInventory = InventoryFactory.instance().makeItemInventory();
        Integer itemID = salesOrder.getItemId();
        Integer packageId = salesOrder.getPackageId();

        IPackage iPackage = PackageFactory.instance().makePackage();
        iPackage.setPackageId(packageId);

        iPackage.load();
        Double quantity = iPackage.getQuantity();

        itemInventory.setItemId(itemID);
        itemInventory.setItemQuantity(quantity);
        itemInventory.decreaseQuantity();
    }

}