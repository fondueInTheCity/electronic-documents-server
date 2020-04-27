package edu.fondue.electronicdocuments.services;

import edu.fondue.electronicdocuments.dto.organization.CreateOrganizationRoleDto;
import edu.fondue.electronicdocuments.dto.organization.RenameOrganizationRoleDto;
import edu.fondue.electronicdocuments.models.Organization;
import edu.fondue.electronicdocuments.models.OrganizationRole;
import edu.fondue.electronicdocuments.repositories.OrganizationRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationRoleServiceImpl implements OrganizationRoleService {

    private final OrganizationRoleRepository repository;

    @Override
    public void save(final OrganizationRole organizationRole) {
        repository.save(organizationRole);
    }

    @Override
    public OrganizationRole create(final CreateOrganizationRoleDto createOrganizationRoleDto) {
        return OrganizationRole.builder()
                .roleName(createOrganizationRoleDto.getName())
                .build();
    }

    @Override
    public void rename(final RenameOrganizationRoleDto renameOrganizationRoleDto) {
        final OrganizationRole role = repository.getOne(renameOrganizationRoleDto.getId());

        role.setRoleName(renameOrganizationRoleDto.getNewName());

        repository.save(role);
    }

    @Override
    public void delete(final Long organizationRoleId) {
        repository.deleteById(organizationRoleId);
    }

    @Override
    public OrganizationRole create(final String roleName, final Organization organization) {
        return OrganizationRole.builder()
                .roleName(roleName)
                .organization(organization).build();
    }

    @Override
    public List<OrganizationRole> getRoles(Organization organization, List<String> roles) {
        return repository.findAllByOrganizationAndRoleNameIn(organization, roles);
    }
}
