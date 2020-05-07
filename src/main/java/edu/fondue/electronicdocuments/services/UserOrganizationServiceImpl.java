package edu.fondue.electronicdocuments.services;

import edu.fondue.electronicdocuments.configuration.UserPrinciple;
import edu.fondue.electronicdocuments.configuration.security.JwtProvider;
import edu.fondue.electronicdocuments.dto.*;
import edu.fondue.electronicdocuments.dto.organization.*;
import edu.fondue.electronicdocuments.enums.OfferType;
import edu.fondue.electronicdocuments.models.Offer;
import edu.fondue.electronicdocuments.models.Organization;
import edu.fondue.electronicdocuments.models.OrganizationRole;
import edu.fondue.electronicdocuments.models.User;
import edu.fondue.electronicdocuments.utils.Properties;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static edu.fondue.electronicdocuments.enums.OrganizationRequestState.NO_REQUEST;
import static edu.fondue.electronicdocuments.enums.OrganizationRequestState.WAITING;
import static edu.fondue.electronicdocuments.enums.OrganizationType.PUBLIC;
import static java.lang.String.format;
import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class UserOrganizationServiceImpl implements UserOrganizationService {

    private final UserService userService;

    private final OrganizationService organizationService;

    private final StorageService storageService;

    private final OfferService offerService;

    private final JwtProvider jwtProvider;

    private final Properties properties;


    @Override
    public OrganizationViewDto getOrganizationView(final Long id) {
        return organizationService.getView(id);
    }

    @Override
    @Transactional
    public Long createOrganization(final OrganizationCreateDto organizationCreateDto) {
        final Organization organization = OrganizationCreateDto.toOrganization(organizationCreateDto);
        final User owner = userService.getUser(organizationCreateDto.getOwnerUsername());

        organization.setOwner(owner);
        organization.getUsers().add(owner);
        owner.getOrganizations().add(organization);

        final Long newOrgId = organizationService.save(organization);

        organizationService.createDefaultRoles(organization);

        owner.setOrganizationRoles(organization.getOrganizationRoles());
        userService.save(owner);

        final User user = userService.getUser(owner.getUsername());
        storageService.createFolder(format("%s/%d", properties.getOrganizationsDirectory(), newOrgId));
        storageService.createFolder(format("organizations/%d/%d", newOrgId, user.getId()));
        return newOrgId;
    }

    @Override
    public boolean existsUserByUsername(final String username) {
        return userService.existsByUsername(username);
    }

    @Override
    public boolean existsUserByEmail(final String email) {
        return userService.existsByEmail(email);
    }

    @Override
    public void saveUser(final User user) {
        userService.save(user);
    }

    @Override
    public UserDashboardDto getUserDashboard(final String username) {
        return userService.getDashboard(username);
    }

    @Override
    public void updateUserDashboard(final String username, final UserDashboardDto userDashboardDto) {
        userService.updateDashboard(username, userDashboardDto);
    }

    @Override
    public List<OrganizationInfoDto> getOrganizations(final String username) {
        return userService.getOrganizations(username);
    }

    @Override
    public List<OrganizationMemberDto> getOrganizationMembers(final Long organizationId) {
        return organizationService.get(organizationId).getUsers().stream()
                .map(OrganizationMemberDto::fromUser)
                .collect(toList());
    }

    @Override
    public OrganizationMemberViewDto getOrganizationMember(final Long organizationId, final Long userId) {

        final User member = userService.find(userId);
        final OrganizationMemberViewDto organizationMemberViewDto = OrganizationMemberViewDto.fromUser(member);
        final List<OrganizationRoleInfoDto> roles = member.getOrganizationRoles().stream()
                .filter(organizationRole -> organizationRole.getOrganization().getId().equals(organizationId))
                .map(OrganizationRoleInfoDto::fromOrganizationRole)
                .collect(toList());
        organizationMemberViewDto.setRoles(roles);

        return organizationMemberViewDto;
    }

    @Override
    public OrganizationSettingsDto getOrganizationSettings(final Long organizationId) {
        return OrganizationSettingsDto.fromOrganization(organizationService.get(organizationId));
    }

    @Override
    public void updateOrganizationSettings(final OrganizationSettingsDto organizationSettingsDto) {
        final Organization organization = organizationService.get(organizationSettingsDto.getId());

        OrganizationSettingsDto.updateOrganization(organization, organizationSettingsDto);

        organizationService.save(organization);
    }

    @Override
    public void joinPrivate(final Long userId, final PrivateJoinTokenDto privateJoinTokenDto) {
        final User user = userService.find(userId);
        final String token = privateJoinTokenDto.getToken();
        final JSONObject json = new JSONObject(jwtProvider.getSubject(token));
        final Organization organization = organizationService.get(json.getLong("organizationId"));
    }

    @Override
    public void privateJoin(final PrivateJoinTokenDto privateJoinTokenDto) {
        final JSONObject json = new JSONObject(jwtProvider.getSubject(privateJoinTokenDto.getToken()));
        final User user = userService.getUser(json.getString("username"));
        final Organization organization = organizationService.get(json.getLong("organizationId"));

        user.getOrganizations().add(organization);
        userService.save(user);

        organization.getUsers().add(user);
        organizationService.save(organization);
    }

    @Override
    public void createRequest(final Long organizationId, final Long userId) {
        final User user = userService.find(userId);
        final Organization organization = organizationService.get(organizationId);
        final Offer offer = offerService.createOffer(user, organization, OfferType.TO_ORGANIZATION);

        offerService.save(offer);

        user.getOffers().add(offer);
        userService.save(user);

        organization.getOffers().add(offer);
        organizationService.save(organization);
    }

    @Override
    public List<OrganizationRoleInfoDto> getOrganizationRoles(final Long organizationId) {
        return organizationService.get(organizationId).getOrganizationRoles().stream()
                .map(OrganizationRoleInfoDto::fromOrganizationRole)
                .collect(toList());
    }

    @Override
    public void createOrganizationRole(final CreateOrganizationRoleDto createOrganizationRoleDto) {
        organizationService.createOrganizationRole(createOrganizationRoleDto);
    }

    @Override
    public void deleteOrganizationRole(final Long organizationRoleId) {
        organizationService.deleteOrganizationRole(organizationRoleId);
    }

    @Override
    public void renameOrganizationRole(final RenameOrganizationRoleDto renameOrganizationRoleDto) {
        organizationService.renameOrganizationRole(renameOrganizationRoleDto);
    }

    @Override
    public void createOrganizationRequest(final CreateOrganizationRequest createOrganizationRequest) {
        final User user = userService.getUser(createOrganizationRequest.getUsername());
        final Organization organization = organizationService.get(createOrganizationRequest.getOrganizationId());
        final Offer offer = offerService.createOffer(user, organization, OfferType.FROM_ORGANIZATION);

        offerService.save(offer);

        user.getOffers().add(offer);
        userService.save(user);

        organization.getOffers().add(offer);
        organizationService.save(organization);
    }

    @Override
    public UserRequestsViewDto getRequests(final String username) {
        var map = userService.getUser(username).getOffers().stream()
                .map(offer -> OrganizationOfferDto.fromOffer(offer, true))
                .collect(groupingBy(OrganizationOfferDto::getType, HashMap::new, toCollection(ArrayList::new)));
        return UserRequestsViewDto.createFormMap(map);
    }

    @Override
    public void checkPermissions(final Long organizationId) throws Exception {
        final UserPrinciple userPrinciple =
                (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        final User user = userService.find(userPrinciple.getId());
        final boolean admin = user.getOrganizationRoles().stream()
                .filter(role -> role.getOrganization().getId().equals(organizationId))
                .anyMatch(role -> role.getRoleName().equals("Admin"));

        if (!admin) {
            throw new Exception("NO");
        }
    }

    @Override
    public void addRole(final Long organizationId, final Long memberId, final AddRoleDto addRoleDto) {
        final User user = userService.find(memberId);
        final Organization organization = organizationService.get(organizationId);
        final OrganizationRole organizationRole = organization.getOrganizationRoles().stream()
                .filter(role -> role.getId().equals(addRoleDto.getRole()))
                .findAny().get();

        user.getOrganizationRoles().add(organizationRole);
        userService.save(user);
    }

    @Override
    public void deleteRole(final Long organizationId, final Long memberId, final Long id) {
        final User user = userService.find(memberId);

        user.getOrganizationRoles().removeIf(role -> role.getId().equals(id));

        userService.save(user);
    }

    @Override
    public OrganizationRequestsView getOffers(final Long organizationId) {
        var map = organizationService.get(organizationId).getOffers().stream()
                .map(offer -> OrganizationOfferDto.fromOffer(offer, false))
                .collect(groupingBy(OrganizationOfferDto::getType, HashMap::new, toCollection(ArrayList::new)));
        return OrganizationRequestsView.createFormMap(map);
    }

    @Override
    public void answeredOffer(final OrganizationAnswerOfferDto organizationAnswerOfferDto) {
        final Offer offer = offerService.get(organizationAnswerOfferDto.getOfferId());
        final User user = offer.getUser();
        final Organization organization = offer.getOrganization();

        if (organizationAnswerOfferDto.isAnswer()) {
            organization.getUsers().add(user);
            user.getOrganizations().add(organization);
            storageService.createFolder(format("organizations/%s/%d", organization.getId(), user.getId()));

            user.setOrganizationRoles(organizationService.getDefaultOrganizationRoles(organization));
        }

        organization.getOffers().remove(offer);
        user.getOffers().remove(offer);

        organizationService.save(organization);

        userService.save(user);

        offerService.delete(offer);
    }

    @Override
    public List<OrganizationInfoDto> getPublicOrganizations() {
        final UserPrinciple userPrinciple =
                (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final Long currentId = userPrinciple.getId();
        final User user = userService.find(currentId);

        var list = organizationService.getOrganizationsByType(PUBLIC).stream()
                .map(organization -> OrganizationInfoDto.fromOrganization(organization, user))
                .collect(toList());
        list.forEach(organizationInfoDto -> {
            if (organizationInfoDto.getSubscribe().equals(NO_REQUEST.name())) {
                organizationInfoDto.setSubscribe(
                        (offerService.existsByUserIdAndOrganizationId(currentId, organizationInfoDto.getId())
                                ? WAITING
                                : NO_REQUEST).name());
            }
        });

        return list;
    }

    @Override
    public PrivateJoinTokenDto generatePrivateJoinToken(final GenerateOrganizationJoinJwtDto jwtDto) {
        return organizationService.generatePrivateJoinToken(jwtDto);
    }
}
