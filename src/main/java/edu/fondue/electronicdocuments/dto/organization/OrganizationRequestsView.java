package edu.fondue.electronicdocuments.dto.organization;

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
public class OrganizationRequestsView {

    private List<OrganizationOfferDto> userRequests;

    private List<OrganizationOfferDto> organizationRequests;

    public static OrganizationRequestsView createFormMap(final Map<String, ArrayList<OrganizationOfferDto>> map)  {
        return OrganizationRequestsView.builder()
                .userRequests(map.get(TO_ORGANIZATION.name()))
                .organizationRequests(map.get(FROM_ORGANIZATION.name())).build();
    }
}
