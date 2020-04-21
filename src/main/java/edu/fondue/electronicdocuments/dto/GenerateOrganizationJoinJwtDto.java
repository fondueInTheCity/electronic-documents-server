package edu.fondue.electronicdocuments.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenerateOrganizationJoinJwtDto {

    private String username;

    private Long organizationId;
}
