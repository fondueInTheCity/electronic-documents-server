package edu.fondue.electronicdocuments.dto;

import edu.fondue.electronicdocuments.models.User;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDashboardDto {

    private String username;

    private String firstName;

    private String middleName;

    private String lastName;

    private String publicInfo;

    private String email;

    public static UserDashboardDto fromUser(final User user) {
        return UserDashboardDto.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .publicInfo("public info in progress")
                .email(user.getEmail()).build();
    }

    public static void updateUser(final User user, final UserDashboardDto userDashboardDto) {
        user.setUsername(userDashboardDto.getUsername());
        user.setFirstName(userDashboardDto.getFirstName());
        user.setMiddleName(userDashboardDto.getMiddleName());
        user.setLastName(userDashboardDto.getLastName());
//        user.setPublicInfo(userDashboardDto.getPublicInfo());
        user.setEmail(userDashboardDto.getEmail());
    }
}
