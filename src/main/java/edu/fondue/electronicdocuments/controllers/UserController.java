package edu.fondue.electronicdocuments.controllers;

import edu.fondue.electronicdocuments.dto.UserDashboardDto;
import edu.fondue.electronicdocuments.dto.organization.OrganizationInfoDto;
import edu.fondue.electronicdocuments.services.UserOrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserOrganizationService service;

    @GetMapping("{username}/dashboard")
    public UserDashboardDto get(@PathVariable final String username) {
        return service.getUserDashboard(username);
    }

    @PutMapping("{username}/dashboard")
    public void put(@PathVariable final String username, @RequestBody final UserDashboardDto userDashboardDto) {
        service.updateUserDashboard(username, userDashboardDto);
    }

    @GetMapping("{username}/organizations")
    public List<OrganizationInfoDto> getList(@PathVariable final String username) {
        return service.getOrganizations(username);
    }
}
