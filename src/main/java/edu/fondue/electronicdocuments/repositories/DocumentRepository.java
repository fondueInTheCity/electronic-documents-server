package edu.fondue.electronicdocuments.repositories;

import edu.fondue.electronicdocuments.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findAllByOrganizationIdAndOwnerId(Long organizationId, Long ownerId);

    List<Document> findAllByOwnerId(Long ownerId);

    List<Document> findAllByOrganizationIdAndAnswer(Long organizationId, Boolean answer);
}
