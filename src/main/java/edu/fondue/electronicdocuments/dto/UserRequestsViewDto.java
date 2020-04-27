package edu.fondue.electronicdocuments.dto;

import edu.fondue.electronicdocuments.dto.organization.OrganizationOfferDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static edu.fondue.electronicdocuments.enums.OfferType.FROM_ORGANIZATION;
import static edu.fondue.electronicdocuments.enums.OfferType.TO_ORGANIZATION;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestsViewDto {

    private List<OrganizationOfferDto> userRequests;

    private List<OrganizationOfferDto> organizationRequests;

    public static UserRequestsViewDto createFormMap(final Map<String, ArrayList<OrganizationOfferDto>> map) {
        return UserRequestsViewDto.builder()
                .userRequests(map.get(TO_ORGANIZATION.name()))
                .organizationRequests(map.get(FROM_ORGANIZATION.name())).build();
    }
}
