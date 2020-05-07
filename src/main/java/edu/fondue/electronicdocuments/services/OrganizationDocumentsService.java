package edu.fondue.electronicdocuments.services;

import edu.fondue.electronicdocuments.dto.document.*;
import edu.fondue.electronicdocuments.dto.organization.MyOrganizationDocumentsInfoDto;
import edu.fondue.electronicdocuments.dto.organization.OrganizationDocumentsInfoDto;
import org.springframework.web.multipart.MultipartFile;

public interface OrganizationDocumentsService {

    void uploadOrganizationFile(Long organizationId, Long userId, MultipartFile file);

    MyOrganizationDocumentsInfoDto getUserOrganizationDocuments(Long organizationId, Long userId);

    OrganizationDocumentViewDto getOrganizationDocumentView(Long documentId);

    UserDocumentsInfoDto getUserDocumentsInfo(Long userId);

    void changeDocumentState(Long documentId, ChangeDocumentStateDto dto);

    void approveDenyDocument(Long documentId, DocumentAnswerDto answer);

    byte[] download(Long documentId);

    HeapDocumentViewDto getHeapDocument(Long documentId);

    void approveHeapDocument(HeapDocumentViewDto heapDocumentViewDto);

    WaitingDocumentViewDto getWaitingDocument(Long documentId);

    JoinToMeDocumentViewDto getJoinToMeDocument(Long documentId);

    void downloadDocumentForCheck(Long documentId);

    void answerDocument(Long documentId, DocumentAnswerDto answer);

    String getMediaType(Long documentId);

    PendingDocumentViewDto getPendingDocument(Long documentId);

    AnsweredDocumentViewDto getAnsweredDocument(Long documentId);

    OrganizationDocumentsInfoDto getOrganizationDocumentsInfo(Long organizationId);

    String getDocumentState(Long documentId);
}
