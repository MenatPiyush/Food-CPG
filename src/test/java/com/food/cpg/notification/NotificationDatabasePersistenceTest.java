package com.food.cpg.notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.food.cpg.databasepersistence.ICommonDatabaseOperation;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(NotificationFactory.class)
public class NotificationDatabasePersistenceTest {

    private static final int TEST_USER_ID = 1;
    private static final String TEST_CONTENT_ID = "Test notification content";
    private static final String GET_INSTANCE_METHOD = "instance";

    @Mock
    ICommonDatabaseOperation commonDatabaseOperation;

    @Mock
    Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    ResultSet resultSet;

    @Mock
    INotification notification;

    @Mock
    NotificationFactory notificationFactory;

    @Before
    public void setUp() throws SQLException {
        when(commonDatabaseOperation.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    public void getAllTest() throws Exception {
        PowerMockito.mockStatic(NotificationFactory.class);
        PowerMockito.doReturn(notificationFactory).when(NotificationFactory.class, GET_INSTANCE_METHOD);
        when(notificationFactory.makeNotification(anyInt(), anyString(), anyObject())).thenReturn(notification);
        when(resultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE);
        doNothing().when(commonDatabaseOperation).loadPlaceholderValues(anyObject(), anyList());

        List<Object> placeholderValues = new ArrayList<>();
        placeholderValues.add(1);

        NotificationDatabasePersistence notificationPersistence = new NotificationDatabasePersistence(commonDatabaseOperation);

        notificationPersistence.getAll(TEST_USER_ID);

        verify(commonDatabaseOperation, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(commonDatabaseOperation, times(1)).loadPlaceholderValues(preparedStatement, placeholderValues);
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(3)).next();
        verify(resultSet, times(2)).getString(anyString());
        verify(resultSet, times(2)).getTimestamp(anyString());
    }

    @Test
    public void saveTest() throws SQLException {
        doNothing().when(commonDatabaseOperation).executeUpdate(anyString(), anyList());
        when(notification.getUserId()).thenReturn(TEST_USER_ID);
        when(notification.getContent()).thenReturn(TEST_CONTENT_ID);

        NotificationDatabasePersistence notificationPersistence = new NotificationDatabasePersistence(commonDatabaseOperation);

        notificationPersistence.send(notification);

        verify(commonDatabaseOperation, times(1)).executeUpdate(anyString(), anyList());
        verify(notification, times(1)).getUserId();
        verify(notification, times(1)).getContent();
    }
}
