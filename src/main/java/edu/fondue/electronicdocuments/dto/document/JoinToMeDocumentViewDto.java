package edu.fondue.electronicdocuments.dto.document;

import edu.fondue.electronicdocuments.models.Document;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoinToMeDocumentViewDto {

    private Long id;

    private String name;

    private String organizationName;

    private Long organizationId;

    public static JoinToMeDocumentViewDto fromDocument(final Document document) {
        return JoinToMeDocumentViewDto.builder()
                .id(document.getId())
                .name(document.getName())
                .organizationId(document.getOrganizationId()).build();
    }
}
