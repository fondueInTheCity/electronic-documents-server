package edu.fondue.electronicdocuments.services;

import edu.fondue.electronicdocuments.models.ProgressDocumentsResponse;

import java.util.List;

public interface ProgressDocumentsResponseService {

    void createProgressDocuments(Long userId, Long documentId);

    Long getCount(Long documentId);

    List<ProgressDocumentsResponse> getAllByUserId(Long userId);

    ProgressDocumentsResponse get(Long id, Long documentId);

    List<ProgressDocumentsResponse> getAllByDocumentId(Long documentId);

    void save(ProgressDocumentsResponse progressDocumentsResponse);
}
