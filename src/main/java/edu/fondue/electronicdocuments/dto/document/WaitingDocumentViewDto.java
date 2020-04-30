package edu.fondue.electronicdocuments.dto.document;

import edu.fondue.electronicdocuments.models.Document;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WaitingDocumentViewDto {

    private Long id;

    private Long organizationId;

    private String organizationName;

    private String name;

    private Long count;

    public static WaitingDocumentViewDto fromDocument(final Document document) {
        return WaitingDocumentViewDto.builder()
                .id(document.getId())
                .organizationId(document.getOrganizationId())
                .name(document.getName()).build();
    }
}
