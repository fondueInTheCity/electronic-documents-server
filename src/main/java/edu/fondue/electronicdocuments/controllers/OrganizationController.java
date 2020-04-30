package edu.fondue.electronicdocuments.controllers;

import edu.fondue.electronicdocuments.dto.AddRoleDto;
import edu.fondue.electronicdocuments.dto.GenerateOrganizationJoinJwtDto;
import edu.fondue.electronicdocuments.dto.PrivateJoinTokenDto;
import edu.fondue.electronicdocuments.dto.organization.*;
import edu.fondue.electronicdocuments.services.UserOrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final UserOrganizationService service;

    @GetMapping("{organizationId}")
    public OrganizationViewDto get(@PathVariable final Long organizationId) {
        return service.getOrganizationView(organizationId);
    }

    @PostMapping
    public Long create(@RequestBody OrganizationCreateDto organizationCreateDto) {
        return service.createOrganization(organizationCreateDto);
    }

    @GetMapping("{organizationId}/members")
    public List<OrganizationMemberDto> getOrganizationMembers(@PathVariable final Long organizationId) {
        return service.getOrganizationMembers(organizationId);
    }

    @GetMapping("{organizationId}/members/{userId}")
    public OrganizationMemberViewDto getOrganizationMember(@PathVariable final Long organizationId,
                                                            @PathVariable final Long userId) {
        return service.getOrganizationMember(organizationId, userId);
    }

    @GetMapping("{organizationId}/settings")
    public OrganizationSettingsDto getOrganizationSettings(@PathVariable final Long organizationId) {
        return service.getOrganizationSettings(organizationId);
    }

    @PutMapping("{organizationId}/settings")
    public void updateOrganizationSettings(
            @RequestBody final OrganizationSettingsDto organizationSettingsDto) {
        service.updateOrganizationSettings(organizationSettingsDto);
    }

    @GetMapping("{organizationId}/offers")
    public OrganizationRequestsView getOffers(@PathVariable final Long organizationId) {
        return service.getOffers(organizationId);
    }

    @PutMapping("offers")
    public void answeredOffer(@RequestBody final OrganizationAnswerOfferDto organizationAnswerOfferDto) {
        service.answeredOffer(organizationAnswerOfferDto);
    }

    @GetMapping("public")
    public List<OrganizationInfoDto> getPublicOrganizations() {
        return service.getPublicOrganizations();
    }

    @PostMapping("generate/private-token")
    public PrivateJoinTokenDto generatePrivateJoinToken(@RequestBody final GenerateOrganizationJoinJwtDto jwtDto) {
        return service.generatePrivateJoinToken(jwtDto);
    }

    @PostMapping("join/private")
    public void privateJoin(@RequestBody final PrivateJoinTokenDto privateJoinTokenDto) {
        service.privateJoin(privateJoinTokenDto);
    }

    @PostMapping("{organizationId}/user/{userId}/request")
    public void createRequest(@PathVariable final Long organizationId, @PathVariable final Long userId) {
        service.createRequest(organizationId, userId);
    }

    @GetMapping("{organizationId}/roles")
    public List<OrganizationRoleInfoDto> getOrganizationRoles(@PathVariable final Long organizationId) {
        return service.getOrganizationRoles(organizationId);
    }

    @PostMapping("roles/create")
    public void creteOrganizationRole(@RequestBody final CreateOrganizationRoleDto createOrganizationRoleDto) {
        service.createOrganizationRole(createOrganizationRoleDto);
    }

    @DeleteMapping("roles/{organizationRoleId}")
    public void deleteOrganizationRole(@PathVariable final Long organizationRoleId) {
        service.deleteOrganizationRole(organizationRoleId);
    }

    @PutMapping("roles/rename")
    public void renameOrganizationRole(@RequestBody final RenameOrganizationRoleDto renameOrganizationRoleDto) {
        service.renameOrganizationRole(renameOrganizationRoleDto);
    }

    @PostMapping("organization-request/create")
    public void createOrganizationRequest(@RequestBody CreateOrganizationRequest createOrganizationRequest) {
        service.createOrganizationRequest(createOrganizationRequest);
    }

    @PostMapping("{organizationId}/members/{memberId}/roles")
    public void addRole(@PathVariable final Long organizationId,
                        @PathVariable final Long memberId,
                        @RequestBody final AddRoleDto addRoleDto) {
        service.addRole(organizationId, memberId, addRoleDto);
    }

    @DeleteMapping("{organizationId}/members/{memberId}/roles/{id}")
    public void addRole(@PathVariable final Long organizationId,
                        @PathVariable final Long memberId,
                        @PathVariable final Long id) {
        service.deleteRole(organizationId, memberId, id);
    }
}
