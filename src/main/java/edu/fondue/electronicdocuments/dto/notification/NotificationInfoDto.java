package edu.fondue.electronicdocuments.dto.notification;

import edu.fondue.electronicdocuments.models.Notification;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationInfoDto {

    private String type;

    private String message;

    private String linkText;

    private String path;

    public static  NotificationInfoDto fromNotification(final Notification notification) {
        return NotificationInfoDto.builder()
                .type(notification.getType().name())
                .message(notification.getMessage())
                .linkText(notification.getType().getLinkText())
                .path(notification.getLink()).build();
    }
}
