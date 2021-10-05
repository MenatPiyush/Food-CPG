package com.food.cpg.manufacturer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;

public class ManufacturerDatabasePersistence implements IManufacturerPersistence {

    private final ICommonDatabaseOperation commonDatabaseOperation;

    public ManufacturerDatabasePersistence(ICommonDatabaseOperation commonDatabaseOperation) {
        this.commonDatabaseOperation = commonDatabaseOperation;
    }

    @Override
    public List<IManufacturer> getAll() {
        List<IManufacturer> manufacturers = new ArrayList<>();

        String sql = ManufacturerDatabaseQuery.SELECT_ALL_MANUFACTURER;

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        IManufacturer manufacturer = ManufacturerFactory.instance().makeManufacturer();
                        loadManufacturerDetailsFromResultSet(rs, manufacturer);

                        manufacturers.add(manufacturer);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return manufacturers;
    }

    @Override
    public IManufacturer get(String manufacturerEmail) {
        IManufacturer manufacturer = new Manufacturer();
        manufacturer.setEmail(manufacturerEmail);
        load(manufacturer);
        return manufacturer;
    }

    @Override
    public void load(IManufacturer manufacturer) {
        String sql = ManufacturerDatabaseQuery.LOAD_MANUFACTURER;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(manufacturer.getEmail());

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        loadManufacturerDetailsFromResultSet(rs, manufacturer);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void register(IManufacturer manufacturer) {
        String sql = ManufacturerDatabaseQuery.INSERT_MANUFACTURER;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(manufacturer.getCompanyName());
        placeholderValues.add(manufacturer.getEmail());
        placeholderValues.add(manufacturer.getContact());
        placeholderValues.add(manufacturer.getAddress());

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createLoginAccount(IManufacturer manufacturer) {
        String sql = ManufacturerDatabaseQuery.CREATE_MANUFACTURER_LOGIN_ACCOUNT;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(manufacturer.getEmail());
        placeholderValues.add(manufacturer.getPassword());

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadManufacturerDetailsFromResultSet(ResultSet resultSet, IManufacturer manufacturer) throws SQLException {
        manufacturer.setId(resultSet.getInt(ManufacturerDatabaseColumn.MANUFACTURER_ID));
        manufacturer.setCompanyName(resultSet.getString(ManufacturerDatabaseColumn.MANUFACTURER_COMPANY_NAME));
        manufacturer.setEmail(resultSet.getString(ManufacturerDatabaseColumn.MANUFACTURER_EMAIL));
        manufacturer.setContact(resultSet.getLong(ManufacturerDatabaseColumn.MANUFACTURER_CONTACT));
        manufacturer.setAddress(resultSet.getString(ManufacturerDatabaseColumn.MANUFACTURER_ADDRESS));
    }
}