package edu.fondue.electronicdocuments.models;

import edu.fondue.electronicdocuments.enums.DocumentState;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "path")
    private String path;

    @Column(name = "organization_id")
    private Long organizationId;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private DocumentState state;

    @Column(name = "name")
    private String name;

    @Column(name = "answer")
    private Boolean answer;
}
