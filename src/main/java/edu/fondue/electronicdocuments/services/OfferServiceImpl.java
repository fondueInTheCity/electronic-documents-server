package edu.fondue.electronicdocuments.services;

import edu.fondue.electronicdocuments.enums.OfferType;
import edu.fondue.electronicdocuments.models.Offer;
import edu.fondue.electronicdocuments.models.Organization;
import edu.fondue.electronicdocuments.models.User;
import edu.fondue.electronicdocuments.repositories.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final OfferRepository repository;

    @Override
    public Offer createOffer(final User user, final Organization organization, final OfferType type) {
        return Offer.builder()
                .organization(organization)
                .type(type)
                .user(user).build();
    }

    @Override
    public void save(final Offer offer) {
        repository.save(offer);
    }

    @Override
    public Offer get(final Long id) {
        return repository.getOne(id);
    }

    @Override
    public void delete(final Offer offer) {
        repository.delete(offer);
    }

    @Override
    public boolean existsByUserIdAndOrganizationId(final Long currentId, final Long id) {
        return repository.existsByUserIdAndOrganizationId(currentId, id);
    }
}
