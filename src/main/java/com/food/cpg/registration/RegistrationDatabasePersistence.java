package com.food.cpg.registration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;
import com.food.cpg.manufacturer.IManufacturer;
import com.food.cpg.manufacturer.ManufacturerFactory;

public class RegistrationDatabasePersistence implements IRegistrationPersistence {

    private final ICommonDatabaseOperation commonDatabaseOperation;

    public RegistrationDatabasePersistence(ICommonDatabaseOperation commonDatabaseOperation) {
        this.commonDatabaseOperation = commonDatabaseOperation;
    }

    @Override
    public List<IRegistration> getAll() {
        List<IRegistration> registrations = new ArrayList<>();

        String sql = RegistrationDatabaseQuery.SELECT_ALL_REGISTRATIONS;

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        IManufacturer manufacturer = ManufacturerFactory.instance().makeManufacturer();
                        manufacturer.setId(rs.getInt(RegistrationDatabaseColumn.REGISTRATION_MANUFACTURER_ID));
                        manufacturer.setCompanyName(rs.getString(RegistrationDatabaseColumn.REGISTRATION_MANUFACTURER_COMPANY_NAME));
                        manufacturer.setEmail(rs.getString(RegistrationDatabaseColumn.REGISTRATION_MANUFACTURER_EMAIL));
                        manufacturer.setContact(rs.getLong(RegistrationDatabaseColumn.REGISTRATION_MANUFACTURER_CONTACT));
                        manufacturer.setAddress(rs.getString(RegistrationDatabaseColumn.REGISTRATION_MANUFACTURER_ADDRESS));

                        IRegistration registration = RegistrationFactory.instance().makeRegistration();
                        registration.setManufacturer(manufacturer);
                        registration.setStatus(rs.getString(RegistrationDatabaseColumn.REGISTRATION_STATUS));

                        registrations.add(registration);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return registrations;
    }

    @Override
    public void approve(String email) {
        String sql = RegistrationDatabaseQuery.APPROVE_REGISTRATION;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(email);

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void block(String email) {
        String sql = RegistrationDatabaseQuery.BLOCK_REGISTRATION;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(email);

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}