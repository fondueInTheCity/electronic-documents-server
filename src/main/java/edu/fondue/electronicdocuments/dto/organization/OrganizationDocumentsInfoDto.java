package edu.fondue.electronicdocuments.dto.organization;

import edu.fondue.electronicdocuments.dto.document.DocumentInfoDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDocumentsInfoDto {

    private List<DocumentInfoDto> documents;
}
