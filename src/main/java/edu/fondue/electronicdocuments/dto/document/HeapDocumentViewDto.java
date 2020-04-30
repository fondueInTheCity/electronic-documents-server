package edu.fondue.electronicdocuments.dto.document;

import edu.fondue.electronicdocuments.dto.organization.OrganizationRoleInfoDto;
import edu.fondue.electronicdocuments.models.Document;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeapDocumentViewDto {

    private Long id;

    private String name;

    private List<Long> roles;

    private List<OrganizationRoleInfoDto> allRoles;

    private Long organizationId;

    public static HeapDocumentViewDto fromDocument(final Document document) {
        return HeapDocumentViewDto.builder()
                .id(document.getId())
                .organizationId(document.getOrganizationId())
                .name(document.getName()).build();
    }
}
