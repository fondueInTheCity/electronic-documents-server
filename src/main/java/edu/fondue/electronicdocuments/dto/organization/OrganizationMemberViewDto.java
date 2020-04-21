package edu.fondue.electronicdocuments.dto.organization;

import edu.fondue.electronicdocuments.models.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class OrganizationMemberViewDto {

    public static OrganizationMemberViewDto fromUser(final User user) {
        return OrganizationMemberViewDto.builder().build();
    }
}
