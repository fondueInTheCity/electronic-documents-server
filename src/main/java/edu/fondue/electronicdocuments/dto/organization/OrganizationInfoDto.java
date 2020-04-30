package edu.fondue.electronicdocuments.dto.organization;

import edu.fondue.electronicdocuments.models.Organization;
import edu.fondue.electronicdocuments.models.User;
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

    private Boolean subscribe;

    public static OrganizationInfoDto fromOrganization(final Organization organization, final User user) {
        return OrganizationInfoDto.builder()
                .id(organization.getId())
                .name(organization.getName())
                .ownerUsername(organization.getOwner().getUsername())
                .subscribe(organization.getUsers().contains(user))
                .type(organization.getOrganizationType().name()).build();
    }
}
