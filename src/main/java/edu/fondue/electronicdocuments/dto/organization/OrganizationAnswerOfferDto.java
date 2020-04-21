package edu.fondue.electronicdocuments.dto.organization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class OrganizationAnswerOfferDto {

    private Long offerId;

    private boolean answer;
}
