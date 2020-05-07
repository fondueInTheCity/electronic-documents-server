package edu.fondue.electronicdocuments.dto.notification;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserNotificationDto {

    private List<NotificationInfoDto> notifications;
}
