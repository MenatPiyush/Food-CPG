package com.food.cpg.databasepersistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import com.food.cpg.applicationhandlers.ApplicationBeanHandler;
import com.mysql.cj.exceptions.ConnectionIsClosedException;

@Repository
@DependsOn("ApplicationBeanHandler")
public class CommonDatabaseOperation implements ICommonDatabaseOperation {

    DataSource dataSource = ApplicationBeanHandler.getBean(DataSource.class);

    @Override
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException exception) {
            throw new ConnectionIsClosedException(exception);
        }
    }

    @Override
    public void loadPlaceholderValues(PreparedStatement preparedStatement, List<Object> placeholderValues) throws SQLException {
        int index = 0;
        for (Object placeHolderValue : placeholderValues) {
            preparedStatement.setObject(++index, placeHolderValue);
        }
    }

    @Override
    public void executeUpdate(String sql, List<Object> placeholderValues) throws SQLException {
        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                int index = 0;
                for (Object placeHolderValue : placeholderValues) {
                    statement.setObject(++index, placeHolderValue);
                }
                statement.executeUpdate();
            }
        }
    }

    @Override
    public Integer executeUpdateGetId(String sql, List<Object> placeholderValues) throws SQLException {
        Integer id = null;
        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                int index = 0;
                for (Object placeHolderValue : placeholderValues) {
                    statement.setObject(++index, placeHolderValue);
                }
                statement.executeUpdate();

                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            }
        }
        return id;
    }
}
