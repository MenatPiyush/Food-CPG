package com.food.cpg.vendor;

import java.util.List;

public interface IVendorPersistence {

    List<IVendor> getAll(int manufacturerId);

    void load(IVendor vendor);

    void save(IVendor vendor);

    void update(IVendor vendor);

    void delete(int vendorId);
}