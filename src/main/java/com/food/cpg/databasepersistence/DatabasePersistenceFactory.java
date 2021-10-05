package com.food.cpg.databasepersistence;

import com.food.cpg.applicationhandlers.ApplicationBeanHandler;
import com.food.cpg.inventory.IItemInventoryPersistence;
import com.food.cpg.inventory.IRawMaterialInventoryPersistence;
import com.food.cpg.inventory.ItemInventoryDatabasePersistence;
import com.food.cpg.inventory.RawMaterialInventoryDatabasePersistence;
import com.food.cpg.item.IItemPersistence;
import com.food.cpg.item.IItemRawMaterialPersistence;
import com.food.cpg.item.ItemDatabasePersistence;
import com.food.cpg.item.ItemRawMaterialDatabasePersistence;
import com.food.cpg.manufactureorder.IManufactureOrderPersistence;
import com.food.cpg.manufactureorder.ManufactureOrderDatabasePersistence;
import com.food.cpg.manufacturer.IManufacturerPersistence;
import com.food.cpg.manufacturer.ManufacturerDatabasePersistence;
import com.food.cpg.notification.INotificationPersistence;
import com.food.cpg.notification.NotificationDatabasePersistence;
import com.food.cpg.packaging.IPackagePersistence;
import com.food.cpg.packaging.PackageDatabasePersistence;
import com.food.cpg.purchaseorder.IPurchaseOrderPersistence;
import com.food.cpg.purchaseorder.IPurchaseOrderRawMaterialPersistence;
import com.food.cpg.purchaseorder.PurchaseOrderDatabasePersistence;
import com.food.cpg.purchaseorder.PurchaseOrderRawMaterialDatabasePersistence;
import com.food.cpg.rawmaterial.IRawMaterialPersistence;
import com.food.cpg.rawmaterial.RawMaterialDatabasePersistence;
import com.food.cpg.registration.IRegistrationPersistence;
import com.food.cpg.registration.RegistrationDatabasePersistence;
import com.food.cpg.salesorder.ISalesOrderPersistence;
import com.food.cpg.salesorder.SalesOrderDatabasePersistence;
import com.food.cpg.vendor.IVendorPersistence;
import com.food.cpg.vendor.VendorDatabasePersistence;

public class DatabasePersistenceFactory extends PersistenceFactory {

    ICommonDatabaseOperation commonDatabaseOperation = ApplicationBeanHandler.getBean(ICommonDatabaseOperation.class);

    @Override
    public IVendorPersistence getVendorPersistence() {
        return new VendorDatabasePersistence(commonDatabaseOperation);
    }

    @Override
    public IManufacturerPersistence getManufacturerPersistence() {
        return new ManufacturerDatabasePersistence(commonDatabaseOperation);
    }

    @Override
    public IRegistrationPersistence getRegistrationPersistence() {
        return new RegistrationDatabasePersistence(commonDatabaseOperation);
    }

    @Override
    public IRawMaterialPersistence getRawMaterialPersistence() {
        return new RawMaterialDatabasePersistence(commonDatabaseOperation);
    }

    @Override
    public IItemPersistence getItemPersistence() {
        return new ItemDatabasePersistence(commonDatabaseOperation);
    }

    @Override
    public IItemRawMaterialPersistence getItemRawMaterialPersistence() {
        return new ItemRawMaterialDatabasePersistence(commonDatabaseOperation);
    }

    @Override
    public IPurchaseOrderPersistence getPurchaseOrderPersistence() {
        return new PurchaseOrderDatabasePersistence(commonDatabaseOperation);
    }

    @Override
    public IPurchaseOrderRawMaterialPersistence getPurchaseOrderRawMaterialPersistence() {
        return new PurchaseOrderRawMaterialDatabasePersistence(commonDatabaseOperation);
    }

    @Override
    public IPackagePersistence getPackagesPersistence() {
        return new PackageDatabasePersistence(commonDatabaseOperation);
    }

    @Override
    public IRawMaterialInventoryPersistence getRawMaterialInventoryPersistence() {
        return new RawMaterialInventoryDatabasePersistence(commonDatabaseOperation);
    }

    @Override
    public IItemInventoryPersistence getItemInventoryPersistence() {
        return new ItemInventoryDatabasePersistence(commonDatabaseOperation);
    }

    @Override
    public IManufactureOrderPersistence getManufactureOrderPersistence() {
        return new ManufactureOrderDatabasePersistence(commonDatabaseOperation);
    }

    @Override
    public ISalesOrderPersistence getSalesOrderPersistence() {
        return new SalesOrderDatabasePersistence(commonDatabaseOperation);
    }

    @Override
    public INotificationPersistence getNotificationPersistence() {
        return new NotificationDatabasePersistence(commonDatabaseOperation);
    }
}
