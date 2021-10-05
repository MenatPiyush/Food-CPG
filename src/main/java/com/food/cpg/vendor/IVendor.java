package com.food.cpg.vendor;

import java.util.List;
import java.util.Map;

public interface IVendor {
    Integer getId();

    void setId(Integer id);

    Integer getManufacturerId();

    void setManufacturerId(Integer manufacturerId);

    String getName();

    void setName(String name);

    String getAddress();

    void setAddress(String address);

    String getContactPersonName();

    void setContactPersonName(String contactPersonName);

    String getContactPersonEmail();

    void setContactPersonEmail(String contactPersonEmail);

    Long getContactPersonPhone();

    void setContactPersonPhone(Long contactPersonPhone);

    Map<String, String> getErrors();

    void setErrors(Map<String, String> errors);

    boolean isValidVendor();

    List<IVendor> getAll();

    void save();

    void load();

    void update();

    void delete();
}
