package edu.fondue.electronicdocuments.dto.organization;

import edu.fondue.electronicdocuments.models.Offer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class OrganizationOfferDto {

    private Long id;

    private String ownerUsername;

    static public OrganizationOfferDto fromOffer(final Offer offer) {
        return OrganizationOfferDto.builder()
                .id(offer.getId())
                .ownerUsername(offer.getUser().getUsername()).build();
    }
}
