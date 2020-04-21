package edu.fondue.electronicdocuments.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "organization_roles")
public class OrganizationRole {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "role_name")
    private String roleName;

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;
}
