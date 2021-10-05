package com.food.cpg.notification;

import java.sql.Timestamp;

public abstract class NotificationFactory {
    private static NotificationFactory notificationFactory;

    public static NotificationFactory instance() {
        return notificationFactory;
    }

    public static void setNotificationFactory(NotificationFactory factory) {
        notificationFactory = factory;
    }

    public abstract INotification makeNotification(int userId, String content, Timestamp notificationDate);

    public abstract INotificationPersistence makeNotificationPersistence();
}
