package edu.fondue.electronicdocuments.dto.organization;

import edu.fondue.electronicdocuments.enums.OrganizationType;
import edu.fondue.electronicdocuments.models.Organization;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationCreateDto {

    private String name;

    private String ownerUsername;

    private String type;

    public static Organization toOrganization(final OrganizationCreateDto organizationCreateDto) {
        return Organization.builder()
                .name(organizationCreateDto.getName())
                .offers(new ArrayList<>())
                .users(new ArrayList<>())
                .organizationType(OrganizationType.valueOf(organizationCreateDto.getType()))
                .build();
    }
}
