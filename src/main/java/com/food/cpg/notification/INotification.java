package com.food.cpg.notification;

import java.sql.Timestamp;
import java.util.List;

public interface INotification {
    int getUserId();

    String getContent();

    Timestamp getNotificationDate();

    List<INotification> getAll();

    void send();
}
