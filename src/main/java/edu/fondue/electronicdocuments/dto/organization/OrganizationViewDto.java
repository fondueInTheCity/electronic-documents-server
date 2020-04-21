package edu.fondue.electronicdocuments.dto.organization;

import edu.fondue.electronicdocuments.models.Organization;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationViewDto {

    private Long id;

    private String name;

    private String ownerUsername;

    private String organizationType;

    public static OrganizationViewDto fromOrganization(final Organization organization) {
        return OrganizationViewDto.builder()
                .id(organization.getId())
                .name(organization.getName())
                .ownerUsername(organization.getOwner().getUsername())
                .organizationType(organization.getOrganizationType().name()).build();
    }
}
