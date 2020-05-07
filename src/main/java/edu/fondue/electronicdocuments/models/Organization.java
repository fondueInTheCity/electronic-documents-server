package edu.fondue.electronicdocuments.models;

import edu.fondue.electronicdocuments.enums.OrganizationType;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "organizations", uniqueConstraints = {
})
public class Organization {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "organization")
    private List<Offer> offers;

    @ManyToMany(mappedBy = "organizations")
    private List<User> users;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private OrganizationType organizationType;

    @OneToMany(mappedBy = "organization")
    private List<OrganizationRole> organizationRoles;
}
