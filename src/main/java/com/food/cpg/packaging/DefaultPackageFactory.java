package com.food.cpg.packaging;

import com.food.cpg.databasepersistence.PersistenceFactory;

public class DefaultPackageFactory extends PackageFactory {

    private final IPackagePersistence packagePersistence;

    public DefaultPackageFactory(PersistenceFactory persistenceFactory) {
        packagePersistence = persistenceFactory.getPackagesPersistence();
    }

    @Override
    public IPackage makePackage() {
        return new Package();
    }

    @Override
    public IPackagePersistence makePackagePersistence() {
        return packagePersistence;
    }
}
