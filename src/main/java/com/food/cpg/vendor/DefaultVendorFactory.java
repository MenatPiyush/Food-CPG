package com.food.cpg.vendor;

import com.food.cpg.databasepersistence.PersistenceFactory;

public class DefaultVendorFactory extends VendorFactory {

    private final IVendorPersistence vendorPersistence;

    public DefaultVendorFactory(PersistenceFactory persistenceFactory) {
        vendorPersistence = persistenceFactory.getVendorPersistence();
    }

    @Override
    public IVendor makeVendor() {
        return new Vendor();
    }

    @Override
    public IVendorPersistence makeVendorPersistence() {
        return vendorPersistence;
    }
}
