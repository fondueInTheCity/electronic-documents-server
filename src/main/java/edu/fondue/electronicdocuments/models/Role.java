package edu.fondue.electronicdocuments.models;

import edu.fondue.electronicdocuments.enums.RoleName;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @NaturalId
    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private RoleName roleName;
}
