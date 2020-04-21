package edu.fondue.electronicdocuments.repositories;

import edu.fondue.electronicdocuments.enums.OrganizationType;
import edu.fondue.electronicdocuments.models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository  extends JpaRepository<Organization, Long> {

    Organization findOrganizationByName(String name);

    List<Organization> findAllByOrganizationType(OrganizationType type);
}
