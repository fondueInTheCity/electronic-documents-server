package edu.fondue.electronicdocuments.dto.document;

import edu.fondue.electronicdocuments.models.Document;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDocumentViewDto {

    private Long id;

    private String name;

    private LocalDate createDate;

    private String state;

    public static OrganizationDocumentViewDto fromDocument(final Document document) {
        return OrganizationDocumentViewDto.builder()
                .id(document.getId())
                .name(document.getName())
                .state(document.getState().name())
                .createDate(LocalDate.now()).build();
    }
}
