package edu.fondue.electronicdocuments.dto.organization;

import edu.fondue.electronicdocuments.models.User;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationMemberDto {

    private Long memberId;

    private String firstName;

    private String middleName;

    private String lastName;

    public static OrganizationMemberDto fromUser(final User user) {
        return OrganizationMemberDto.builder()
                .memberId(user.getId())
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName()).build();
    }
}
