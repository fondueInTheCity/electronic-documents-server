package edu.fondue.electronicdocuments.dto.organization;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrganizationRoleDto {

    private Long organizationId;

    private String name;
}
