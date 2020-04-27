package edu.fondue.electronicdocuments.dto.organization;

import edu.fondue.electronicdocuments.models.OrganizationRole;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationRoleInfoDto {

    private Long id;

    private String name;

    static public OrganizationRoleInfoDto fromOrganizationRole(final OrganizationRole organizationRole) {
        return OrganizationRoleInfoDto.builder()
                .id(organizationRole.getId())
                .name(organizationRole.getRoleName()).build();
    }
}
