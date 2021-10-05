package com.food.cpg.notification;

import java.sql.Timestamp;

import com.food.cpg.databasepersistence.PersistenceFactory;

public class DefaultNotificationFactory extends NotificationFactory {

    private final INotificationPersistence notificationPersistence;

    public DefaultNotificationFactory(PersistenceFactory persistenceFactory) {
        notificationPersistence = persistenceFactory.getNotificationPersistence();
    }

    @Override
    public INotification makeNotification(int userId, String content, Timestamp notificationDate) {
        return new Notification(userId, content, notificationDate);
    }

    @Override
    public INotificationPersistence makeNotificationPersistence() {
        return notificationPersistence;
    }
}
