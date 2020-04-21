package edu.fondue.electronicdocuments.dto.organization;

import edu.fondue.electronicdocuments.dto.document.DocumentInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class OrganizationDocumentsInfoDto {

    private List<DocumentInfoDto> heapDocuments;

    private List<DocumentInfoDto> waitingDocuments;

    private List<DocumentInfoDto> progressDocuments;

    private List<DocumentInfoDto> answeredDocuments;
}
