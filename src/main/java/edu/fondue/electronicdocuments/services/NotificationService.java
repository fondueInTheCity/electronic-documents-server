package edu.fondue.electronicdocuments.services;

import edu.fondue.electronicdocuments.dto.notification.UserNotificationDto;

public interface NotificationService {

    UserNotificationDto getUserNotifications(Long userId);
}
