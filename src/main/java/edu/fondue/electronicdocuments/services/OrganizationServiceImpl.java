package edu.fondue.electronicdocuments.services;

import edu.fondue.electronicdocuments.configuration.security.JwtProvider;
import edu.fondue.electronicdocuments.dto.GenerateOrganizationJoinJwtDto;
import edu.fondue.electronicdocuments.dto.PrivateJoinTokenDto;
import edu.fondue.electronicdocuments.dto.organization.CreateOrganizationRoleDto;
import edu.fondue.electronicdocuments.dto.organization.OrganizationCreateDto;
import edu.fondue.electronicdocuments.dto.organization.OrganizationViewDto;
import edu.fondue.electronicdocuments.dto.organization.RenameOrganizationRoleDto;
import edu.fondue.electronicdocuments.enums.OrganizationType;
import edu.fondue.electronicdocuments.models.Organization;
import edu.fondue.electronicdocuments.models.OrganizationRole;
import edu.fondue.electronicdocuments.repositories.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private static final List<String> DEFAULT_ROLES = Arrays.asList("User", "Admin");

    private static final List<String> DEFAULT_USER_ROLES = Collections.singletonList("User");

    private final OrganizationRepository repository;

    private final JwtProvider jwtProvider;

    private final OrganizationRoleService organizationRoleService;

    @Override
    public OrganizationViewDto getView(final Long id) {
        return OrganizationViewDto.fromOrganization(get(id));
    }

    @Override
    public void create(final OrganizationCreateDto organizationCreateDto) {

    }

    @Override
    public Long save(final Organization organization) {
        final Organization newOrganization = repository.save(organization);
        return newOrganization.getId();
    }

    @Override
    public Organization get(final Long id) {
        return repository.findById(id).get();
    }

    @Override
    public Organization findByName(final String name) {
        return repository.findOrganizationByName(name);
    }

    @Override
    public List<Organization> getOrganizationsByType(final OrganizationType type) {
        return repository.findAllByOrganizationType(type);
    }

    @Override
    public PrivateJoinTokenDto generatePrivateJoinToken(final GenerateOrganizationJoinJwtDto jwtDto) {
        return jwtProvider.generatePrivateJoinJwtToken(jwtDto);
    }

    @Override
    public void createOrganizationRole(final CreateOrganizationRoleDto createOrganizationRoleDto) {
        final OrganizationRole organizationRole = organizationRoleService.create(createOrganizationRoleDto);
        final Organization organization = repository.findById(createOrganizationRoleDto.getOrganizationId()).get();

        organizationRole.setOrganization(organization);
        organization.getOrganizationRoles().add(organizationRole);

        organizationRoleService.save(organizationRole);
        repository.save(organization);
    }

    @Override
    public void deleteOrganizationRole(final Long organizationRoleId) {
        organizationRoleService.delete(organizationRoleId);
    }

    @Override
    public void renameOrganizationRole(final RenameOrganizationRoleDto renameOrganizationRoleDto) {
        organizationRoleService.rename(renameOrganizationRoleDto);
    }

    @Override
    public void createDefaultRoles(final Organization organization) {
        final List<OrganizationRole> roles = DEFAULT_ROLES.stream()
                .map(roleName -> organizationRoleService.create(roleName, organization))
                .collect(toList());

        organization.setOrganizationRoles(roles);
        roles.forEach(organizationRoleService::save);
    }

    @Override
    public List<OrganizationRole> getDefaultOrganizationRoles(final Organization organization) {
        return organizationRoleService.getRoles(organization, DEFAULT_USER_ROLES);
    }
}
