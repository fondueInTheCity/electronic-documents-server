package edu.fondue.electronicdocuments.services;

import edu.fondue.electronicdocuments.models.ProgressDocumentsResponse;
import edu.fondue.electronicdocuments.repositories.ProgressDocumentsResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static edu.fondue.electronicdocuments.enums.DocumentAnswer.NOT_ANSWER;

@Service
@RequiredArgsConstructor
public class ProgressDocumentsResponseServiceImpl implements ProgressDocumentsResponseService {

    private final ProgressDocumentsResponseRepository repository;

    @Override
    public void createProgressDocuments(final Long userId, final Long documentId) {
        final ProgressDocumentsResponse progressDocumentsResponse = ProgressDocumentsResponse.builder()
                .documentId(documentId)
                .userId(userId)
                .answer(NOT_ANSWER).build();

        repository.save(progressDocumentsResponse);
    }

    @Override
    public Long getCount(final Long documentId) {
        return repository.countByDocumentId(documentId);
    }

    @Override
    public List<ProgressDocumentsResponse> getAllByUserId(final Long userId) {
        return repository.getAllByUserId(userId);
    }

    @Override
    public ProgressDocumentsResponse get(final Long userId, final Long documentId) {
        return repository.getByUserIdAndDocumentId(userId, documentId);
    }

    @Override
    public List<ProgressDocumentsResponse> getAllByDocumentId(final Long documentId) {
        return repository.getAllByDocumentId(documentId);
    }

    @Override
    public void save(final ProgressDocumentsResponse progressDocumentsResponse) {
        repository.save(progressDocumentsResponse);
    }
}
