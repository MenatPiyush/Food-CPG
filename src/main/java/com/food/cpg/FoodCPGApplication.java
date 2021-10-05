package com.food.cpg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.food.cpg.authentication.AuthenticationFactory;
import com.food.cpg.authentication.DefaultAuthenticationFactory;
import com.food.cpg.databasepersistence.PersistenceFactory;
import com.food.cpg.inventory.DefaultInventoryFactory;
import com.food.cpg.inventory.InventoryFactory;
import com.food.cpg.item.DefaultItemFactory;
import com.food.cpg.item.ItemFactory;
import com.food.cpg.manufactureorder.DefaultManufactureOrderFactory;
import com.food.cpg.manufactureorder.ManufactureOrderFactory;
import com.food.cpg.manufacturer.DefaultManufacturerFactory;
import com.food.cpg.manufacturer.ManufacturerFactory;
import com.food.cpg.notification.DefaultNotificationFactory;
import com.food.cpg.notification.NotificationFactory;
import com.food.cpg.packaging.DefaultPackageFactory;
import com.food.cpg.packaging.PackageFactory;
import com.food.cpg.purchaseorder.DefaultPurchaseOrderFactory;
import com.food.cpg.purchaseorder.PurchaseOrderFactory;
import com.food.cpg.rawmaterial.DefaultRawMaterialFactory;
import com.food.cpg.rawmaterial.RawMaterialFactory;
import com.food.cpg.registration.DefaultRegistrationFactory;
import com.food.cpg.registration.RegistrationFactory;
import com.food.cpg.salesorder.DefaultSalesOrderFactory;
import com.food.cpg.salesorder.SalesOrderFactory;
import com.food.cpg.vendor.DefaultVendorFactory;
import com.food.cpg.vendor.VendorFactory;

@SpringBootApplication
@EnableScheduling
public class FoodCPGApplication {

    private static final Logger LOG = LoggerFactory.getLogger(FoodCPGApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(FoodCPGApplication.class, args);
        configureFactories();
        LOG.info("Food CPG Application Started Successfully.");
    }

    private static void configureFactories() {
        PersistenceFactory persistenceFactory = PersistenceFactory.getPersistenceFactory();

        AuthenticationFactory.setAuthenticationFactory(new DefaultAuthenticationFactory());
        ManufacturerFactory.setManufacturerFactory(new DefaultManufacturerFactory(persistenceFactory));
        RawMaterialFactory.setRawMaterialFactory(new DefaultRawMaterialFactory(persistenceFactory));
        VendorFactory.setVendorFactory(new DefaultVendorFactory(persistenceFactory));
        RegistrationFactory.setRegistrationFactory(new DefaultRegistrationFactory(persistenceFactory));
        ItemFactory.setItemFactory(new DefaultItemFactory(persistenceFactory));
        PackageFactory.setPackageFactory(new DefaultPackageFactory(persistenceFactory));
        InventoryFactory.setInventoryFactory(new DefaultInventoryFactory(persistenceFactory));
        NotificationFactory.setNotificationFactory(new DefaultNotificationFactory(persistenceFactory));
        PackageFactory.setPackageFactory(new DefaultPackageFactory(persistenceFactory));
        PurchaseOrderFactory.setPurchaseOrderFactory(new DefaultPurchaseOrderFactory(persistenceFactory));
        ManufactureOrderFactory.setManufactureOrderFactory(new DefaultManufactureOrderFactory(persistenceFactory));
        SalesOrderFactory.setSalesOrderFactory(new DefaultSalesOrderFactory(persistenceFactory));
    }
}