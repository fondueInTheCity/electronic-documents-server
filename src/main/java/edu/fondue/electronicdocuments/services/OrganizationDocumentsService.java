package edu.fondue.electronicdocuments.services;

import edu.fondue.electronicdocuments.dto.document.ChangeDocumentStateDto;
import edu.fondue.electronicdocuments.dto.document.DocumentAnswerDto;
import edu.fondue.electronicdocuments.dto.document.OrganizationDocumentViewDto;
import edu.fondue.electronicdocuments.dto.document.UserDocumentsInfoDto;
import edu.fondue.electronicdocuments.dto.organization.OrganizationDocumentsInfoDto;
import org.springframework.web.multipart.MultipartFile;

public interface OrganizationDocumentsService {

    void uploadOrganizationFile(Long organizationId, Long userId, MultipartFile file);

    OrganizationDocumentsInfoDto getUserOrganizationDocuments(Long organizationId, Long userId);

    OrganizationDocumentViewDto getOrganizationDocumentView(Long documentId);

    UserDocumentsInfoDto getUserDocumentsInfo(Long userId);

    void changeDocumentState(Long documentId, ChangeDocumentStateDto dto);

    void approveDenyDocument(Long documentId, DocumentAnswerDto answer);
}
