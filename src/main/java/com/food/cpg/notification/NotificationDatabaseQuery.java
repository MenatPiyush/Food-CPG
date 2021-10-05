package com.food.cpg.notification;

public class NotificationDatabaseQuery {

    public static final String SELECT_ALL_NOTIFICATIONS = "select * from notifications where user_id = ? order by notification_date desc";
    public static final String INSERT_NOTIFICATION = "insert into notifications (user_id, content) values (?, ?)";

    private NotificationDatabaseQuery() {
    }
}
