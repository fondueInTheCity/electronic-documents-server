package edu.fondue.electronicdocuments.controllers;

import edu.fondue.electronicdocuments.dto.notification.UserNotificationDto;
import edu.fondue.electronicdocuments.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("users/{userId}")
    public UserNotificationDto getUserNotifications(@PathVariable final Long userId) {
        return notificationService.getUserNotifications(userId);
    }
}
