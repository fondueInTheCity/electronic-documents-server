package edu.fondue.electronicdocuments.controllers;

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
    public void create(@RequestBody OrganizationCreateDto organizationCreateDto) {
        service.createOrganization(organizationCreateDto);
    }

    @GetMapping("{organizationId}/members")
    public List<OrganizationMemberDto> getOrganizationMembers(@PathVariable final Long organizationId) {
        return service.getOrganizationMembers(organizationId);
    }

    @GetMapping("{organizationId}/members/{userId}")
    public OrganizationMemberViewDto getOrganizationMembers(@PathVariable final Long organizationId,
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
    public List<OrganizationOfferDto> getOffers(@PathVariable final Long organizationId) {
        return service.getOffers(organizationId);
    }

    @PutMapping("{organizationId}/offers")
    public void answeredOffer(@PathVariable final Long organizationId,
                              @RequestBody final OrganizationAnswerOfferDto organizationAnswerOfferDto) {
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
}
