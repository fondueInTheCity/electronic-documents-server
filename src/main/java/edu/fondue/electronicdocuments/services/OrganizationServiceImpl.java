package edu.fondue.electronicdocuments.services;

import edu.fondue.electronicdocuments.configuration.security.JwtProvider;
import edu.fondue.electronicdocuments.dto.GenerateOrganizationJoinJwtDto;
import edu.fondue.electronicdocuments.dto.PrivateJoinTokenDto;
import edu.fondue.electronicdocuments.dto.organization.OrganizationCreateDto;
import edu.fondue.electronicdocuments.dto.organization.OrganizationViewDto;
import edu.fondue.electronicdocuments.enums.OrganizationType;
import edu.fondue.electronicdocuments.models.Organization;
import edu.fondue.electronicdocuments.repositories.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository repository;

    private final JwtProvider jwtProvider;

    @Override
    public OrganizationViewDto getView(final Long id) {
        return OrganizationViewDto.fromOrganization(get(id));
    }

    @Override
    public void create(final OrganizationCreateDto organizationCreateDto) {

    }

    @Override
    public void save(final Organization organization) {
        repository.save(organization);
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
}
