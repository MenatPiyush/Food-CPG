package com.food.cpg.notification;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Notification.class)
public class NotificationTest {

    private static final int TEST_USER_ID = 1;
    private static final String TEST_CONTENT_ID = "Test notification content";
    private static final Timestamp TEST_NOTIFICATION_DATE = Timestamp.from(Instant.now());
    private static final String GET_PERSISTENCE_METHOD = "getPersistence";

    @Mock
    INotificationPersistence notificationPersistence;

    @Test
    public void getAllTest() throws Exception {
        Notification notification = spy(new Notification(TEST_USER_ID, TEST_CONTENT_ID, TEST_NOTIFICATION_DATE));

        List<INotification> notifications = new ArrayList<>();
        notifications.add(notification);

        PowerMockito.doReturn(notificationPersistence).when(notification, GET_PERSISTENCE_METHOD);
        PowerMockito.doReturn(notifications).when(notificationPersistence).getAll(anyInt());

        List<INotification> notificationsResult = notification.getAll();

        verifyPrivate(notification).invoke(GET_PERSISTENCE_METHOD);
        Assert.assertNotNull(notificationsResult);
        Assert.assertEquals(1, notifications.size());
        Assert.assertEquals(TEST_USER_ID, notifications.get(0).getUserId());
        Assert.assertEquals(TEST_CONTENT_ID, notifications.get(0).getContent());
        Assert.assertEquals(TEST_NOTIFICATION_DATE, notifications.get(0).getNotificationDate());
    }

    @Test
    public void sendTest() throws Exception {
        Notification notification = spy(new Notification(TEST_USER_ID, TEST_CONTENT_ID, TEST_NOTIFICATION_DATE));

        PowerMockito.doReturn(notificationPersistence).when(notification, GET_PERSISTENCE_METHOD);
        PowerMockito.doNothing().when(notificationPersistence).send(notification);

        notification.send();

        verifyPrivate(notification).invoke(GET_PERSISTENCE_METHOD);
        verify(notificationPersistence, times(1)).send(notification);
    }
}
