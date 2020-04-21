package edu.fondue.electronicdocuments.dto.document;

import edu.fondue.electronicdocuments.enums.DocumentState;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeDocumentStateDto {

    private DocumentState to;
}
