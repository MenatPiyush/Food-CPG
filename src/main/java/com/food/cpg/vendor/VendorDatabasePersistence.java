package com.food.cpg.vendor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;

public class VendorDatabasePersistence implements IVendorPersistence {

    private final ICommonDatabaseOperation commonDatabaseOperation;

    public VendorDatabasePersistence(ICommonDatabaseOperation commonDatabaseOperation) {
        this.commonDatabaseOperation = commonDatabaseOperation;
    }

    @Override
    public List<IVendor> getAll(int manufacturerId) {
        List<IVendor> vendorList = new ArrayList<>();

        String sql = VendorDatabaseQuery.SELECT_ALL_VENDOR;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(manufacturerId);

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        IVendor vendor = VendorFactory.instance().makeVendor();
                        loadVendorDetailsFromResultSet(rs, vendor);

                        vendorList.add(vendor);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return vendorList;
    }

    @Override
    public void load(IVendor vendor) {
        String sql = VendorDatabaseQuery.LOAD_VENDOR;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(vendor.getId());

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        loadVendorDetailsFromResultSet(rs, vendor);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(IVendor vendor) {
        String sql = VendorDatabaseQuery.INSERT_VENDOR;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(vendor.getName());
        placeholderValues.add(vendor.getAddress());
        placeholderValues.add(vendor.getContactPersonName());
        placeholderValues.add(vendor.getContactPersonEmail());
        placeholderValues.add(vendor.getContactPersonPhone());
        placeholderValues.add(vendor.getManufacturerId());

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(IVendor vendor) {
        String sql = VendorDatabaseQuery.UPDATE_VENDOR;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(vendor.getName());
        placeholderValues.add(vendor.getAddress());
        placeholderValues.add(vendor.getContactPersonName());
        placeholderValues.add(vendor.getContactPersonEmail());
        placeholderValues.add(vendor.getContactPersonPhone());
        placeholderValues.add(vendor.getId());

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int vendorId) {
        String sql = VendorDatabaseQuery.DELETE_VENDOR;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(vendorId);

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadVendorDetailsFromResultSet(ResultSet resultSet, IVendor vendor) throws SQLException {
        vendor.setId(resultSet.getInt(VendorDatabaseColumn.VENDOR_ID));
        vendor.setName(resultSet.getString(VendorDatabaseColumn.VENDOR_NAME));
        vendor.setAddress(resultSet.getString(VendorDatabaseColumn.VENDOR_ADDRESS));
        vendor.setContactPersonName(resultSet.getString(VendorDatabaseColumn.CONTACT_PERSON_NAME));
        vendor.setContactPersonEmail(resultSet.getString(VendorDatabaseColumn.CONTACT_PERSON_EMAIL));
        vendor.setContactPersonPhone(resultSet.getLong(VendorDatabaseColumn.CONTACT_PERSON_PHONE));
    }
}