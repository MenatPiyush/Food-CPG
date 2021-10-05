package com.food.cpg.vendor;

public abstract class VendorFactory {

    private static VendorFactory vendorFactory;

    public static VendorFactory instance() {
        return vendorFactory;
    }

    public static void setVendorFactory(VendorFactory factory) {
        vendorFactory = factory;
    }

    public abstract IVendor makeVendor();

    public abstract IVendorPersistence makeVendorPersistence();
}
