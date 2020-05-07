package edu.fondue.electronicdocuments.dto.document;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDocumentsInfoDto {

    private List<DocumentInfoDto> heapDocuments;

    private List<DocumentInfoDto> waitingDocuments;

    private List<DocumentInfoDto> progressDocuments;

    private List<DocumentInfoDto> answeredDocuments;

    private List<DocumentInfoDto> joinToMeDocuments;
}
