package edu.fondue.electronicdocuments.dto.document;

import edu.fondue.electronicdocuments.dto.ProgressDocumentAnswerDto;
import edu.fondue.electronicdocuments.models.Document;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PendingDocumentViewDto {

    private Long id;

    private String name;

    private String organizationName;

    private List<ProgressDocumentAnswerDto> answers;

    public static PendingDocumentViewDto fromDocument(final Document document) {
        return PendingDocumentViewDto.builder()
                .id(document.getId())
                .name(document.getName()).build();
    }
}
