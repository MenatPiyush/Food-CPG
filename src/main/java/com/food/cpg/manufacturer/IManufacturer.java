package com.food.cpg.manufacturer;

import java.util.Map;

public interface IManufacturer {
    Integer getId();

    void setId(Integer id);

    String getCompanyName();

    void setCompanyName(String companyName);

    String getEmail();

    void setEmail(String email);

    String getPassword();

    void setPassword(String password);

    Long getContact();

    void setContact(Long contact);

    String getAddress();

    void setAddress(String address);

    Map<String, String> getErrors();

    void setErrors(Map<String, String> errors);

    boolean isValidManufacturer();

    void register();

    void load();
}
