package com.food.cpg.vendor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.food.cpg.authentication.AuthenticationSessionDetails;

public class Vendor implements IVendor {

    private Integer id;
    private Integer manufacturerId;
    private String name;
    private String address;
    private String contactPersonName;
    private String contactPersonEmail;
    private Long contactPersonPhone;

    private Map<String, String> errors = new HashMap<>();

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getManufacturerId() {
        return manufacturerId;
    }

    @Override
    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getContactPersonName() {
        return contactPersonName;
    }

    @Override
    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    @Override
    public String getContactPersonEmail() {
        return contactPersonEmail;
    }

    @Override
    public void setContactPersonEmail(String contactPersonEmail) {
        this.contactPersonEmail = contactPersonEmail;
    }

    @Override
    public Long getContactPersonPhone() {
        return contactPersonPhone;
    }

    @Override
    public void setContactPersonPhone(Long contactPersonPhone) {
        this.contactPersonPhone = contactPersonPhone;
    }

    @Override
    public Map<String, String> getErrors() {
        return errors;
    }

    @Override
    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    @Override
    public boolean isValidVendor() {
        errors = new HashMap<>();

        boolean isValid = true;

        if (StringUtils.isEmpty(getName())) {
            errors.put("name", "Vendor name is required.");
            isValid = false;
        }

        if (StringUtils.isEmpty(getContactPersonName())) {
            errors.put("contactPersonName", "Contact person is required.");
            isValid = false;
        }

        if (StringUtils.isEmpty(getContactPersonEmail())) {
            errors.put("contactPersonEmail", "Email is required.");
            isValid = false;
        }

        if (getContactPersonPhone() == null) {
            errors.put("contactPersonPhone", "Phone number is required.");
            isValid = false;
        }

        if (String.valueOf(getContactPersonPhone()).length() > 10 || String.valueOf(getContactPersonPhone()).length() < 10) {
            errors.put("contactPersonPhone", "Invalid phone number.");
            isValid = false;
        }

        return isValid;
    }

    @Override
    public List<IVendor> getAll() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        return getPersistence().getAll(loggedInManufacturerId);
    }

    @Override
    public void save() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        this.setManufacturerId(loggedInManufacturerId);

        getPersistence().save(this);
    }

    @Override
    public void load() {
        if (this.getId() > 0) {
            getPersistence().load(this);
        }
    }

    @Override
    public void update() {
        getPersistence().update(this);
    }

    @Override
    public void delete() {
        getPersistence().delete(this.getId());
    }

    private IVendorPersistence getPersistence() {
        return VendorFactory.instance().makeVendorPersistence();
    }

    private int getLoggedInManufacturerId() {
        AuthenticationSessionDetails authenticationSessionDetails = AuthenticationSessionDetails.getInstance();
        return authenticationSessionDetails.getAuthenticatedUserId();
    }
}
