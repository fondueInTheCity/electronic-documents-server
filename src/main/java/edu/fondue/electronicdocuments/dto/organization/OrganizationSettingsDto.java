package edu.fondue.electronicdocuments.dto.organization;

import edu.fondue.electronicdocuments.enums.OrganizationType;
import edu.fondue.electronicdocuments.models.Organization;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationSettingsDto {

    private Long id;

    private String name;

    private String organizationType;

    public static OrganizationSettingsDto fromOrganization(final Organization organization) {
        return OrganizationSettingsDto.builder()
                .id(organization.getId())
                .name(organization.getName())
                .organizationType(organization.getOrganizationType().name()).build();
    }

    public static void updateOrganization(final Organization organization,
                                          final OrganizationSettingsDto organizationSettingsDto) {
        organization.setName(organizationSettingsDto.getName());
        organization.setOrganizationType(OrganizationType.valueOf(organizationSettingsDto.getOrganizationType()));
    }
}
