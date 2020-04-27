package edu.fondue.electronicdocuments.dto.organization;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrganizationRequest {

    private Long organizationId;

    private String username;
}
