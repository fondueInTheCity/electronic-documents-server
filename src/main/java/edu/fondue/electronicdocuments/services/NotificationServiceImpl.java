package edu.fondue.electronicdocuments.services;

import edu.fondue.electronicdocuments.dto.notification.NotificationInfoDto;
import edu.fondue.electronicdocuments.dto.notification.UserNotificationDto;
import edu.fondue.electronicdocuments.repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;

    @Override
    public UserNotificationDto getUserNotifications(final Long userId) {
        final List<NotificationInfoDto> list = repository.getAllByUserId(userId).stream()
                .map(NotificationInfoDto::fromNotification)
                .collect(toList());
        return UserNotificationDto.builder()
                .notifications(list).build();
    }
}
