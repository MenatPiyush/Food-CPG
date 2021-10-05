package com.food.cpg.packaging;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;

public class PackageDatabasePersistence implements IPackagePersistence {
    private final ICommonDatabaseOperation commonDatabaseOperation;

    public PackageDatabasePersistence(ICommonDatabaseOperation commonDatabaseOperation) {
        this.commonDatabaseOperation = commonDatabaseOperation;
    }

    @Override
    public List<IPackage> getAll(int manufacturerId) {
        List<IPackage> packagesList = new ArrayList<>();

        String sql = PackageDatabaseQuery.SELECT_ALL_PACKAGE;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(manufacturerId);

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        IPackage packages = PackageFactory.instance().makePackage();
                        loadPackagesDetailsFromResultSet(rs, packages);

                        packagesList.add(packages);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return packagesList;
    }

    @Override
    public void load(IPackage packages) {
        String sql = PackageDatabaseQuery.LOAD_PACKAGE;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(packages.getPackageId());

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        loadPackagesDetailsFromResultSet(rs, packages);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(IPackage packages) {
        String sql = PackageDatabaseQuery.INSERT_PACKAGE;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(packages.getItemId());
        placeholderValues.add(packages.getPackageName());
        placeholderValues.add(packages.getQuantity());
        placeholderValues.add(packages.getManufacturingCost());
        placeholderValues.add(packages.getWholesaleCost());
        placeholderValues.add(packages.getRetailCost());
        placeholderValues.add(packages.getManufacturerId());

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(IPackage packages) {
        String sql = PackageDatabaseQuery.UPDATE_PACKAGE;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(packages.getItemId());
        placeholderValues.add(packages.getPackageName());
        placeholderValues.add(packages.getQuantity());
        placeholderValues.add(packages.getManufacturingCost());
        placeholderValues.add(packages.getWholesaleCost());
        placeholderValues.add(packages.getRetailCost());
        placeholderValues.add(packages.getPackageId());

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int packageId) {
        String sql = PackageDatabaseQuery.DELETE_PACKAGE;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(packageId);

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadPackagesDetailsFromResultSet(ResultSet resultSet, IPackage packages) throws SQLException {
        packages.setPackageId(resultSet.getInt(PackageDatabaseColumn.PACKAGE_ID));
        packages.setItemId(resultSet.getInt(PackageDatabaseColumn.ITEM_ID));
        packages.setPackageName(resultSet.getString(PackageDatabaseColumn.PACKAGE_NAME));
        packages.setQuantity(resultSet.getDouble(PackageDatabaseColumn.QUANTITY));
        packages.setManufacturingCost(resultSet.getDouble(PackageDatabaseColumn.MANUFACTURING_COST));
        packages.setWholesaleCost(resultSet.getDouble(PackageDatabaseColumn.WHOLESALE_COST));
        packages.setRetailCost(resultSet.getDouble(PackageDatabaseColumn.RETAIL_COST));
    }
}
