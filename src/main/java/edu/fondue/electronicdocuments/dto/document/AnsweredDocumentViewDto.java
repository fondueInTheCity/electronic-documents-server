package edu.fondue.electronicdocuments.dto.document;

import edu.fondue.electronicdocuments.models.Document;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnsweredDocumentViewDto {

    private Long id;

    private String name;

    private String organizationName;

    private List<String> joins;

    public static AnsweredDocumentViewDto fromDocument(final Document document) {
        return AnsweredDocumentViewDto.builder()
                .id(document.getId())
                .name(document.getName()).build();
    }
}
