package edu.fondue.electronicdocuments.services;

import edu.fondue.electronicdocuments.dto.document.ChangeDocumentStateDto;
import edu.fondue.electronicdocuments.dto.document.DocumentAnswerDto;
import edu.fondue.electronicdocuments.dto.document.HeapDocumentViewDto;
import edu.fondue.electronicdocuments.dto.organization.OrganizationDocumentsInfoDto;
import edu.fondue.electronicdocuments.models.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {
    void uploadOrganizationFile(String organizationName, Long organizationId, Long userId, MultipartFile file);

    OrganizationDocumentsInfoDto getUserOrganizationDocuments(Long organizationId, Long userId);

    Document get(Long documentId);

    List<Document> getDocumentsByUserId(Long userId);

    void changeDocumentState(Long documentId, ChangeDocumentStateDto dto);

    void approveDenyDocument(Long documentId, DocumentAnswerDto answer);

    byte[] download(Long documentId);

    HeapDocumentViewDto getHeapDocument(Long documentId);

    void save(Document document);
}
