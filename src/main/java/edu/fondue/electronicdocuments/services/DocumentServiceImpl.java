package edu.fondue.electronicdocuments.services;

import edu.fondue.electronicdocuments.dto.document.ChangeDocumentStateDto;
import edu.fondue.electronicdocuments.dto.document.DocumentAnswerDto;
import edu.fondue.electronicdocuments.dto.document.DocumentInfoDto;
import edu.fondue.electronicdocuments.dto.organization.OrganizationDocumentsInfoDto;
import edu.fondue.electronicdocuments.models.Document;
import edu.fondue.electronicdocuments.repositories.DocumentRepository;
import edu.fondue.electronicdocuments.utils.Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static edu.fondue.electronicdocuments.enums.DocumentState.*;
import static java.lang.String.format;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toCollection;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final StorageService storageService;

    private final DocumentRepository repository;

    private final Properties properties;

    @Override
    public void uploadOrganizationFile(final String organizationName, final Long organizationId, final Long userId,
                                       final MultipartFile file) {
        final String uploadPath = format("%s/%s/%d", properties.getOrganizationsDirectory(), organizationName, userId);

        storageService.upload(uploadPath, file);

        final Document document = Document.builder()
                .organizationId(organizationId)
                .ownerId(userId)
                .path(format("%s/%s", uploadPath, file.getOriginalFilename()))
                .name(file.getOriginalFilename())
                .state(HEAP).build();

        repository.save(document);
    }

    @Override
    public OrganizationDocumentsInfoDto getUserOrganizationDocuments(final Long organizationId, final Long userId) {
        final List<Document> documents = repository.findAllByOrganizationIdAndOwnerId(organizationId, userId);
        final Map<String, List<DocumentInfoDto>> map = documents.stream()
                .map(DocumentInfoDto::fromDocument)
                .collect(groupingBy(DocumentInfoDto::getDocumentState, HashMap::new, toCollection(ArrayList::new)));

        return OrganizationDocumentsInfoDto.builder()
                .heapDocuments(map.get(HEAP.name()))
                .waitingDocuments(map.get(WAITING.name()))
                .progressDocuments(map.get(PENDING.name()))
                .answeredDocuments(map.get(ANSWERED.name())).build();
    }

    @Override
    public Document get(final Long documentId) {
        return repository.getOne(documentId);
    }

    @Override
    public List<Document> getDocumentsByUserId(final Long userId) {
        return repository.findAllByOwnerId(userId);
    }

    @Override
    public void changeDocumentState(final Long documentId, final ChangeDocumentStateDto dto) {
        final Document document = repository.getOne(documentId);

        document.setState(dto.getTo());

        repository.save(document);
    }

    @Override
    public void approveDenyDocument(final Long documentId, final DocumentAnswerDto answer) {
        final Document document = repository.getOne(documentId);

        document.setState(ANSWERED);
        document.setAnswer(answer.getAnswer());

        repository.save(document);
    }

    @Override
    public byte[] download(final Long documentId) {
        final Document document = repository.getOne(documentId);
        final String path = format("%s/%s", document.getPath(), document.getName());
        return storageService.download(path);
    }
}
