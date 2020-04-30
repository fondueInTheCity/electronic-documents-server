package edu.fondue.electronicdocuments.repositories;

import edu.fondue.electronicdocuments.models.ProgressDocumentsResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgressDocumentsResponseRepository extends JpaRepository<ProgressDocumentsResponse, Long> {

    Long countByDocumentId(Long documentId);

    List<ProgressDocumentsResponse> getAllByUserId(Long userId);

    ProgressDocumentsResponse getByUserIdAndDocumentId(Long user, Long documentId);

    List<ProgressDocumentsResponse> getAllByDocumentId(Long documentId);
}
