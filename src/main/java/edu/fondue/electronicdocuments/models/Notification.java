package edu.fondue.electronicdocuments.models;

import edu.fondue.electronicdocuments.enums.NotificationType;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "link")
    private String link;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column(name = "message")
    private String message;

    @Column(name = "user_id")
    private Long userId;
}
