package edu.fondue.electronicdocuments.services;

import edu.fondue.electronicdocuments.configuration.UserPrinciple;
import edu.fondue.electronicdocuments.configuration.security.JwtProvider;
import edu.fondue.electronicdocuments.dto.GenerateOrganizationJoinJwtDto;
import edu.fondue.electronicdocuments.dto.PrivateJoinTokenDto;
import edu.fondue.electronicdocuments.dto.UserDashboardDto;
import edu.fondue.electronicdocuments.dto.organization.*;
import edu.fondue.electronicdocuments.enums.OfferType;
import edu.fondue.electronicdocuments.models.Offer;
import edu.fondue.electronicdocuments.models.Organization;
import edu.fondue.electronicdocuments.models.User;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static edu.fondue.electronicdocuments.enums.OrganizationType.PUBLIC;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserOrganizationServiceImpl implements UserOrganizationService {

    private final UserService userService;

    private final OrganizationService organizationService;

    private final StorageService storageService;

    private final OfferService offerService;

    private final JwtProvider jwtProvider;


    @Override
    public OrganizationViewDto getOrganizationView(final Long id) {
        return organizationService.getView(id);
    }

    @Override
    @Transactional
    public void createOrganization(final OrganizationCreateDto organizationCreateDto) {
        final Organization organization = OrganizationCreateDto.toOrganization(organizationCreateDto);
        final User owner = userService.getUser(organizationCreateDto.getOwnerUsername());

        storageService.createFolder(organization.getName());

        organization.setOwner(owner);
        organization.getUsers().add(owner);

        owner.getOrganizations().add(organization);

        organizationService.save(organization);
        userService.save(owner);
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
        return OrganizationMemberViewDto.fromUser(organizationService.get(organizationId).getUsers()
                .get(userId.intValue()));
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
    public List<OrganizationOfferDto> getOffers(final Long organizationId) {
        return organizationService.get(organizationId).getOffers().stream()
                .map(OrganizationOfferDto::fromOffer)
                .collect(toList());
    }

    @Override
    public void answeredOffer(final OrganizationAnswerOfferDto organizationAnswerOfferDto) {
        final Offer offer = offerService.get(organizationAnswerOfferDto.getOfferId());
        final User user = offer.getUser();
        final Organization organization = offer.getOrganization();

        if (organizationAnswerOfferDto.isAnswer()) {
            organization.getUsers().add(user);
            user.getOrganizations().add(organization);
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
                (UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final Long currentId = userPrinciple.getId();

        return organizationService.getOrganizationsByType(PUBLIC).stream()
                .filter(organization -> organization.getUsers().stream()
                        .map(User::getId)
                        .noneMatch(currentId::equals))
                .filter(organization -> organization.getOffers().stream()
                        .map(Offer::getUser)
                        .map(User::getId)
                        .noneMatch(currentId::equals))
                .map(OrganizationInfoDto::fromOrganization)
                .collect(toList());
    }

    @Override
    public PrivateJoinTokenDto generatePrivateJoinToken(final GenerateOrganizationJoinJwtDto jwtDto) {
        return organizationService.generatePrivateJoinToken(jwtDto);
    }
}
