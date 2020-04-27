package edu.fondue.electronicdocuments.services;

import edu.fondue.electronicdocuments.dto.document.*;
import edu.fondue.electronicdocuments.dto.organization.OrganizationDocumentsInfoDto;
import edu.fondue.electronicdocuments.models.Document;
import edu.fondue.electronicdocuments.models.Organization;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class OrganizationDocumentsServiceImpl implements OrganizationDocumentsService {

    private final OrganizationService organizationService;

    private final DocumentService documentService;

    @Override
    @Transactional
    public void uploadOrganizationFile(final Long organizationId, final Long userId,
                                       final MultipartFile file) {
        final Organization organization = organizationService.get(organizationId);

        documentService.uploadOrganizationFile(organization.getName(), organizationId, userId,
                file);
    }

    @Override
    @Transactional
    public OrganizationDocumentsInfoDto getUserOrganizationDocuments(final Long organizationId, final Long userId) {
        return documentService.getUserOrganizationDocuments(organizationId, userId);
    }

    @Override
    public OrganizationDocumentViewDto getOrganizationDocumentView(final Long documentId) {
        return OrganizationDocumentViewDto.fromDocument(documentService.get(documentId));
    }

    @Override
    public UserDocumentsInfoDto getUserDocumentsInfo(final Long userId) {
        final List<Document> userDocuments = documentService.getDocumentsByUserId(userId);
        return UserDocumentsInfoDto.builder().
                documentsInfo(userDocuments.stream()
                        .map(DocumentInfoDto::fromDocument)
                        .collect(toList())).build();
    }

    @Override
    public void changeDocumentState(final Long documentId, final ChangeDocumentStateDto dto) {
        documentService.changeDocumentState(documentId, dto);
    }

    @Override
    public void approveDenyDocument(final Long documentId, final DocumentAnswerDto answer) {
        documentService.approveDenyDocument(documentId, answer);
    }

    @Override
    public byte[] download(final Long documentId) {
        return documentService.download(documentId);
    }
}
