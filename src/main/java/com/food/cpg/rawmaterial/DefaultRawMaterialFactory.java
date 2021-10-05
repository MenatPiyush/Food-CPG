package com.food.cpg.rawmaterial;

import com.food.cpg.databasepersistence.PersistenceFactory;

public class DefaultRawMaterialFactory extends RawMaterialFactory {

    private final IRawMaterialPersistence rawMaterialPersistence;

    public DefaultRawMaterialFactory(PersistenceFactory persistenceFactory) {
        rawMaterialPersistence = persistenceFactory.getRawMaterialPersistence();
    }

    @Override
    public IRawMaterial makeRawMaterial() {
        return new RawMaterial();
    }

    @Override
    public IRawMaterialPersistence makeRawMaterialPersistence() {
        return rawMaterialPersistence;
    }
}
