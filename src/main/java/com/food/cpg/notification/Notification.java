package com.food.cpg.notification;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.apache.logging.log4j.util.Strings;

import com.food.cpg.authentication.AuthenticationSessionDetails;

public class Notification implements INotification {
    private final int userId;
    private final String content;
    private final Timestamp notificationDate;

    public Notification() {
        this(0, Strings.EMPTY, Timestamp.from(Instant.now()));
    }

    public Notification(int userId, String content, Timestamp notificationDate) {
        this.userId = userId;
        this.content = content;
        this.notificationDate = notificationDate;
    }

    public int getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getNotificationDate() {
        return notificationDate;
    }

    public List<INotification> getAll() {
        int loggedInManufacturerId = getLoggedInManufacturerId();
        return getPersistence().getAll(loggedInManufacturerId);
    }

    public void send() {
        getPersistence().send(this);
    }

    private INotificationPersistence getPersistence() {
        return NotificationFactory.instance().makeNotificationPersistence();
    }

    private int getLoggedInManufacturerId() {
        AuthenticationSessionDetails authenticationSessionDetails = AuthenticationSessionDetails.getInstance();
        return authenticationSessionDetails.getAuthenticatedUserId();
    }
}
