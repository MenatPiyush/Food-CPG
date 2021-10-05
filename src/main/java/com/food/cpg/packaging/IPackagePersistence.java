package com.food.cpg.packaging;

import java.util.List;

public interface IPackagePersistence {

    List<IPackage> getAll(int manufacturerId);

    void load(IPackage packages);

    void save(IPackage packages);

    void update(IPackage packages);

    void delete(int packageId);
}
