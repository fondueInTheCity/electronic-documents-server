package edu.fondue.electronicdocuments.services;

import edu.fondue.electronicdocuments.dto.UserDashboardDto;
import edu.fondue.electronicdocuments.dto.organization.OrganizationInfoDto;
import edu.fondue.electronicdocuments.models.User;

import java.util.List;

public interface UserService {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    void save(User user);

    UserDashboardDto getDashboard(String username);

    void updateDashboard(String username, UserDashboardDto userDashboardDto);

    List<OrganizationInfoDto> getOrganizations(String username);

    User getUser(String username);

    User find(Long id);
}
