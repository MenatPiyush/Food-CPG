package com.food.cpg.notification;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NotificationController {

    private static final String NOTIFICATION_LIST_END_POINT = "/notifications";
    private static final String NOTIFICATION_LIST_ROUTE = "notification/notifications";
    private static final String VIEW_NOTIFICATIONS_KEY = "notifications";

    @GetMapping(NOTIFICATION_LIST_END_POINT)
    public String showNotifications(Notification notification, Model model) {
        List<INotification> notifications = notification.getAll();
        model.addAttribute(VIEW_NOTIFICATIONS_KEY, notifications);

        return NOTIFICATION_LIST_ROUTE;
    }
}
