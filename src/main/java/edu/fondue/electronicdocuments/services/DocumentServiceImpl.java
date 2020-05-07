package edu.fondue.electronicdocuments.services;

import edu.fondue.electronicdocuments.dto.document.ChangeDocumentStateDto;
import edu.fondue.electronicdocuments.dto.document.DocumentAnswerDto;
import edu.fondue.electronicdocuments.dto.document.DocumentInfoDto;
import edu.fondue.electronicdocuments.dto.document.HeapDocumentViewDto;
import edu.fondue.electronicdocuments.dto.organization.MyOrganizationDocumentsInfoDto;
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
import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final StorageService storageService;

    private final DocumentRepository repository;

    private final OrganizationRoleService organizationRoleService;

    private final Properties properties;

    @Override
    public void uploadOrganizationFile(final Long organizationId, final Long userId, final MultipartFile file) {
        final String uploadPath = format("%s/%d/%d", properties.getOrganizationsDirectory(), organizationId, userId);

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
    public MyOrganizationDocumentsInfoDto getUserOrganizationDocuments(final Long organizationId, final Long userId) {
        final List<Document> documents = repository.findAllByOrganizationIdAndOwnerId(organizationId, userId);
        final Map<String, List<DocumentInfoDto>> map = documents.stream()
                .map(DocumentInfoDto::fromDocument)
                .collect(groupingBy(DocumentInfoDto::getDocumentState, HashMap::new, toCollection(ArrayList::new)));

        return MyOrganizationDocumentsInfoDto.builder()
                .heapDocuments(map.get(HEAP.name()))
                .waitingDocuments(map.get(WAITING.name()))
                .progressDocuments(map.get(PENDING.name()))
                .joinToMeDocuments(map.get(JOIN_TO_ME.name()))
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
        return storageService.download(document.getPath());
    }

    @Override
    public HeapDocumentViewDto getHeapDocument(final Long documentId) {
        return HeapDocumentViewDto.fromDocument(repository.getOne(documentId));
    }

    @Override
    public void save(final Document document) {
        repository.save(document);
    }

    @Override
    public List<DocumentInfoDto> getOrganizationDocumentsInfo(final Long organizationId) {
        return repository.findAllByOrganizationIdAndAnswer(organizationId, true).stream()
                .map(DocumentInfoDto::fromDocument)
                .collect(toList());
    }
}
