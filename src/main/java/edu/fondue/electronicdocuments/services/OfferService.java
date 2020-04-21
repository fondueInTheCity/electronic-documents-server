package edu.fondue.electronicdocuments.services;

import edu.fondue.electronicdocuments.enums.OfferType;
import edu.fondue.electronicdocuments.models.Offer;
import edu.fondue.electronicdocuments.models.Organization;
import edu.fondue.electronicdocuments.models.User;

public interface OfferService {

    Offer createOffer(User user, Organization organization, OfferType type);

    void save(Offer offer);

    Offer get(Long id);

    void delete(Offer offer);
}
