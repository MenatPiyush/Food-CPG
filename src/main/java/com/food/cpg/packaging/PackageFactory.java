package com.food.cpg.packaging;

public abstract class PackageFactory {

    private static PackageFactory packageFactory;

    public static PackageFactory instance() {
        return packageFactory;
    }

    public static void setPackageFactory(PackageFactory packages) {
        packageFactory = packages;
    }

    public abstract IPackage makePackage();

    public abstract IPackagePersistence makePackagePersistence();
}
