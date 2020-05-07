package edu.fondue.electronicdocuments.dto.organization;

import edu.fondue.electronicdocuments.models.Organization;
import edu.fondue.electronicdocuments.models.User;
import lombok.*;

import static edu.fondue.electronicdocuments.enums.OrganizationRequestState.IN_ORGANIZATION;
import static edu.fondue.electronicdocuments.enums.OrganizationRequestState.NO_REQUEST;

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

    private String subscribe;

    public static OrganizationInfoDto fromOrganization(final Organization organization, final User user) {
        return OrganizationInfoDto.builder()
                .id(organization.getId())
                .name(organization.getName())
                .ownerUsername(organization.getOwner().getUsername())
                .subscribe((organization.getUsers().contains(user) ? IN_ORGANIZATION : NO_REQUEST).name())
                .type(organization.getOrganizationType().name()).build();
    }
}
