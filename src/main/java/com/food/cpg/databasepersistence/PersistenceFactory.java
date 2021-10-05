package com.food.cpg.databasepersistence;

import com.food.cpg.inventory.IItemInventoryPersistence;
import com.food.cpg.inventory.IRawMaterialInventoryPersistence;
import com.food.cpg.item.IItemPersistence;
import com.food.cpg.item.IItemRawMaterialPersistence;
import com.food.cpg.manufactureorder.IManufactureOrderPersistence;
import com.food.cpg.manufacturer.IManufacturerPersistence;
import com.food.cpg.notification.INotificationPersistence;
import com.food.cpg.packaging.IPackagePersistence;
import com.food.cpg.purchaseorder.IPurchaseOrderPersistence;
import com.food.cpg.purchaseorder.IPurchaseOrderRawMaterialPersistence;
import com.food.cpg.rawmaterial.IRawMaterialPersistence;
import com.food.cpg.registration.IRegistrationPersistence;
import com.food.cpg.salesorder.ISalesOrderPersistence;
import com.food.cpg.vendor.IVendorPersistence;

public abstract class PersistenceFactory {

    public static PersistenceFactory getPersistenceFactory() {
        return new DatabasePersistenceFactory();
    }

    public abstract IVendorPersistence getVendorPersistence();

    public abstract IManufacturerPersistence getManufacturerPersistence();

    public abstract IRegistrationPersistence getRegistrationPersistence();

    public abstract IRawMaterialPersistence getRawMaterialPersistence();

    public abstract IItemPersistence getItemPersistence();

    public abstract IItemRawMaterialPersistence getItemRawMaterialPersistence();

    public abstract IPurchaseOrderPersistence getPurchaseOrderPersistence();

    public abstract IPurchaseOrderRawMaterialPersistence getPurchaseOrderRawMaterialPersistence();

    public abstract IManufactureOrderPersistence getManufactureOrderPersistence();

    public abstract IPackagePersistence getPackagesPersistence();

    public abstract IRawMaterialInventoryPersistence getRawMaterialInventoryPersistence();

    public abstract IItemInventoryPersistence getItemInventoryPersistence();

    public abstract ISalesOrderPersistence getSalesOrderPersistence();

    public abstract INotificationPersistence getNotificationPersistence();
}
