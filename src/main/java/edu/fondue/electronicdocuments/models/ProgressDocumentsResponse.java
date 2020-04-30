package edu.fondue.electronicdocuments.models;

import edu.fondue.electronicdocuments.enums.DocumentAnswer;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "progress_documents_response")
public class ProgressDocumentsResponse {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "answer")
    private DocumentAnswer answer;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "document_id")
    private Long documentId;
}
