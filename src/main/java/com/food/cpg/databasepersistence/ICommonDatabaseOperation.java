package com.food.cpg.databasepersistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface ICommonDatabaseOperation {

    Connection getConnection();

    void loadPlaceholderValues(PreparedStatement preparedStatement, List<Object> placeholderValues) throws SQLException;

    void executeUpdate(String sql, List<Object> placeholderValues) throws SQLException;

    Integer executeUpdateGetId(String sql, List<Object> placeholderValues) throws SQLException;
}
