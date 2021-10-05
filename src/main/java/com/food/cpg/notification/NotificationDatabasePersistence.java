package com.food.cpg.notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;

public class NotificationDatabasePersistence implements INotificationPersistence {

    private final ICommonDatabaseOperation commonDatabaseOperation;

    public NotificationDatabasePersistence(ICommonDatabaseOperation commonDatabaseOperation) {
        this.commonDatabaseOperation = commonDatabaseOperation;
    }

    @Override
    public List<INotification> getAll(int userId) {
        List<INotification> notifications = new ArrayList<>();

        String sql = NotificationDatabaseQuery.SELECT_ALL_NOTIFICATIONS;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(userId);

        try (Connection connection = commonDatabaseOperation.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                commonDatabaseOperation.loadPlaceholderValues(preparedStatement, placeholderValues);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        String content = rs.getString(NotificationDatabaseColumn.CONTENT);
                        Timestamp notificationDate = rs.getTimestamp(NotificationDatabaseColumn.NOTIFICATION_DATE);

                        INotification notification = NotificationFactory.instance().makeNotification(userId, content, notificationDate);

                        notifications.add(notification);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return notifications;
    }

    @Override
    public void send(INotification notification) {
        String sql = NotificationDatabaseQuery.INSERT_NOTIFICATION;
        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(notification.getUserId());
        placeholderValues.add(notification.getContent());

        try {
            commonDatabaseOperation.executeUpdate(sql, placeholderValues);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}