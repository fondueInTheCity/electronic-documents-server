package edu.fondue.electronicdocuments.services;

import edu.fondue.electronicdocuments.dto.*;
import edu.fondue.electronicdocuments.dto.organization.*;
import edu.fondue.electronicdocuments.models.User;

import java.util.List;

public interface UserOrganizationService {

    OrganizationViewDto getOrganizationView(Long id);

    Long createOrganization(OrganizationCreateDto organizationCreateDto);

    boolean existsUserByUsername(String username);

    boolean existsUserByEmail(String email);

    void saveUser(User user);

    UserDashboardDto getUserDashboard(String username);

    void updateUserDashboard(String username, UserDashboardDto userDashboardDto);

    List<OrganizationInfoDto> getOrganizations(String username);

    List<OrganizationMemberDto> getOrganizationMembers(Long organizationId);

    OrganizationMemberViewDto getOrganizationMember(Long organizationId, Long userId);

    OrganizationSettingsDto getOrganizationSettings(Long organizationId);

    void updateOrganizationSettings(OrganizationSettingsDto organizationSettingsDto);

    OrganizationRequestsView getOffers(Long organizationId);

    void answeredOffer(OrganizationAnswerOfferDto organizationAnswerOfferDto);

    List<OrganizationInfoDto> getPublicOrganizations();

    PrivateJoinTokenDto generatePrivateJoinToken(GenerateOrganizationJoinJwtDto jwtDto);

    void joinPrivate(Long userId, PrivateJoinTokenDto privateJoinTokenDto);

    void privateJoin(PrivateJoinTokenDto privateJoinTokenDto);

    void createRequest(Long organizationId, Long userId);

    List<OrganizationRoleInfoDto> getOrganizationRoles(Long organizationId);

    void createOrganizationRole(CreateOrganizationRoleDto createOrganizationRoleDto);

    void deleteOrganizationRole(Long organizationRoleId);

    void renameOrganizationRole(RenameOrganizationRoleDto renameOrganizationRoleDto);

    void createOrganizationRequest(CreateOrganizationRequest createOrganizationRequest);

    UserRequestsViewDto getRequests(String username);

    void checkPermissions(Long organizationId) throws Exception;

    void addRole(Long organizationId, Long memberId, AddRoleDto addRoleDto);

    void deleteRole(Long organizationId, Long memberId, Long id);
}
