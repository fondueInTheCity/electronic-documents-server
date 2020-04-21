package edu.fondue.electronicdocuments.dto.document;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDocumentsInfoDto {

    private List<DocumentInfoDto> documentsInfo;
}
