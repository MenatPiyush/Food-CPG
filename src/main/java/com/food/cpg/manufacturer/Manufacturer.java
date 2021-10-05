package com.food.cpg.manufacturer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import com.food.cpg.authentication.AuthenticationFactory;

public class Manufacturer implements IManufacturer {
    private Integer id;
    private String companyName;
    private String email;
    private String password;
    private Long contact;
    private String address;

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
    public String getCompanyName() {
        return companyName;
    }

    @Override
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Long getContact() {
        return contact;
    }

    @Override
    public void setContact(Long contact) {
        this.contact = contact;
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
    public Map<String, String> getErrors() {
        return errors;
    }

    @Override
    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    @Override
    public boolean isValidManufacturer() {
        errors = new HashMap<>();

        boolean isValid = true;

        if (StringUtils.isEmpty(getCompanyName())) {
            errors.put("companyName", "Company name is required.");
            isValid = false;
        }

        if (StringUtils.isEmpty(getEmail())) {
            errors.put("email", "Email address is required.");
            isValid = false;
        }

        if (StringUtils.isEmpty(getPassword())) {
            errors.put("password", "Password can not be empty.");
            isValid = false;
        }

        if (getContact() == null) {
            errors.put("contact", "Phone number is required.");
            isValid = false;
        }

        if (String.valueOf(getContact()).length() > 10 || String.valueOf(getContact()).length() < 10) {
            errors.put("contactLength", "Invalid phone number.");
            isValid = false;
        }

        if (StringUtils.isEmpty(getAddress())) {
            errors.put("address", "Address can not be empty.");
        }

        return isValid;
    }

    @Override
    public void register() {
        getPersistence().register(this);

        PasswordEncoder passwordEncoder = AuthenticationFactory.instance().makePasswordEncoder();
        String encodedPassword = passwordEncoder.encode(this.getPassword());
        this.setPassword(encodedPassword);

        getPersistence().createLoginAccount(this);
    }

    @Override
    public void load() {
        getPersistence().load(this);
    }

    private IManufacturerPersistence getPersistence() {
        return ManufacturerFactory.instance().makeManufacturerPersistence();
    }
}
