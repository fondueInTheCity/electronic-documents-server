package edu.fondue.electronicdocuments.dto.organization;

import edu.fondue.electronicdocuments.models.Organization;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationInfoDto {

    private Long id;

    private String name;

    private String ownerUsername;

    private String type;

    public static OrganizationInfoDto fromOrganization(final Organization organization) {
        return OrganizationInfoDto.builder()
                .id(organization.getId())
                .name(organization.getName())
                .ownerUsername(organization.getOwner().getUsername())
                .type(organization.getOrganizationType().name()).build();
    }
}
