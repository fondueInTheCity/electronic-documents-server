package edu.fondue.electronicdocuments.dto.organization;

import edu.fondue.electronicdocuments.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class OrganizationMemberViewDto {

    private Long memberId;

    private String firstName;

    private String middleName;

    private String lastName;

    private List<OrganizationRoleInfoDto> roles;

    public static OrganizationMemberViewDto fromUser(final User user) {
        return OrganizationMemberViewDto.builder()
                .memberId(user.getId())
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .build();
    }
}
