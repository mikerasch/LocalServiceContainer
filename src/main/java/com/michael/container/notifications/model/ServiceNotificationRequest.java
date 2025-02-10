package com.michael.container.notifications.model;

import com.michael.container.notifications.enums.NotificationType;

public record ServiceNotificationRequest(
    NotificationType notificationType,
    String applicationName,
    String ip,
    int applicationVersion,
    int port) {}
