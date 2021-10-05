package com.food.cpg.notification;

import java.util.List;

public interface INotificationPersistence {

    List<INotification> getAll(int userId);

    void send(INotification notification);
}