package edu.fondue.electronicdocuments.services;

import edu.fondue.electronicdocuments.configuration.security.JwtProvider;
import edu.fondue.electronicdocuments.dto.UserDashboardDto;
import edu.fondue.electronicdocuments.dto.organization.OrganizationInfoDto;
import edu.fondue.electronicdocuments.models.User;
import edu.fondue.electronicdocuments.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final JwtProvider jwtProvider;

    @Override
    public boolean existsByUsername(final String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(final String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Long save(final User user) {
        return repository.save(user).getId();
    }

    @Override
    public UserDashboardDto getDashboard(final String username) {
        return UserDashboardDto.fromUser(repository.findByUsername(username));
    }

    @Override
    public void updateDashboard(final String username, final UserDashboardDto userDashboardDto) {
        final User user = repository.findByUsername(username);
        UserDashboardDto.updateUser(user, userDashboardDto);

        repository.save(user);

//        return jwtProvider.generateJwtToken(userDashboardDto.getUsername());
    }

    @Override
    public List<OrganizationInfoDto> getOrganizations(final String username) {
        final User user = repository.findByUsername(username);
        return repository.findByUsername(username).getOrganizations().stream()
                .map(organization -> OrganizationInfoDto.fromOrganization(organization, user))
                .collect(toList());
    }

    @Override
    public User getUser(final String username) {
        return repository.findByUsername(username);
    }

    @Override
    public User find(final Long id) {
        return repository.findById(id).get();
    }
}
