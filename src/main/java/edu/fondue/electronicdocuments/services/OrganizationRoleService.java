package edu.fondue.electronicdocuments.services;

import edu.fondue.electronicdocuments.dto.organization.CreateOrganizationRoleDto;
import edu.fondue.electronicdocuments.dto.organization.RenameOrganizationRoleDto;
import edu.fondue.electronicdocuments.models.Organization;
import edu.fondue.electronicdocuments.models.OrganizationRole;

import java.util.List;

public interface OrganizationRoleService {

    void save(OrganizationRole organizationRole);

    OrganizationRole create(CreateOrganizationRoleDto createOrganizationRoleDto);

    void rename(RenameOrganizationRoleDto renameOrganizationRoleDto);

    void delete(Long organizationRoleId);

    OrganizationRole create(String roleName, Organization organization);

    List<OrganizationRole> getRoles(Organization organization, List<String> defaultRoles);
}
