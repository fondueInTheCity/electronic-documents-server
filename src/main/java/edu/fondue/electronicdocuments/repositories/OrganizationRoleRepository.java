package edu.fondue.electronicdocuments.repositories;

import edu.fondue.electronicdocuments.models.Organization;
import edu.fondue.electronicdocuments.models.OrganizationRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrganizationRoleRepository extends JpaRepository<OrganizationRole, Long> {

    List<OrganizationRole> findAllByOrganizationAndRoleNameIn(Organization organization, List<String> roles);
}
