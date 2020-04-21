package edu.fondue.electronicdocuments.services;

import edu.fondue.electronicdocuments.dto.GenerateOrganizationJoinJwtDto;
import edu.fondue.electronicdocuments.dto.PrivateJoinTokenDto;
import edu.fondue.electronicdocuments.dto.organization.OrganizationCreateDto;
import edu.fondue.electronicdocuments.dto.organization.OrganizationViewDto;
import edu.fondue.electronicdocuments.enums.OrganizationType;
import edu.fondue.electronicdocuments.models.Organization;

import java.util.List;

public interface OrganizationService {

    OrganizationViewDto getView(Long id);

    void create(OrganizationCreateDto organizationCreateDto);

    void save(Organization organization);

    Organization get(Long id);

    Organization findByName(String name);

    List<Organization> getOrganizationsByType(OrganizationType type);

    PrivateJoinTokenDto generatePrivateJoinToken(GenerateOrganizationJoinJwtDto jwtDto);
}
