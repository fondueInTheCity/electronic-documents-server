package edu.fondue.electronicdocuments.dto.document;

import edu.fondue.electronicdocuments.models.Document;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentInfoDto {

    private Long id;

    private String name;

    private Long ownerId;

    private Long organizationId;

    private String documentState;

    public static DocumentInfoDto fromDocument(final Document document) {
        return DocumentInfoDto.builder()
                .id(document.getId())
                .name(document.getName())
                .organizationId(document.getOrganizationId())
                .ownerId(document.getOwnerId())
                .documentState(document.getState().name()).build();
    }
}
