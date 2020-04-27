package edu.fondue.electronicdocuments.dto.organization;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RenameOrganizationRoleDto {

    private Long id;

    private String newName;
}
