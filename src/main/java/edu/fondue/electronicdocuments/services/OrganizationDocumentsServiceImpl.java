package edu.fondue.electronicdocuments.services;

import edu.fondue.electronicdocuments.configuration.UserPrinciple;
import edu.fondue.electronicdocuments.dto.document.*;
import edu.fondue.electronicdocuments.dto.organization.OrganizationDocumentsInfoDto;
import edu.fondue.electronicdocuments.dto.organization.OrganizationRoleInfoDto;
import edu.fondue.electronicdocuments.models.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static edu.fondue.electronicdocuments.enums.DocumentAnswer.*;
import static edu.fondue.electronicdocuments.enums.DocumentState.*;
import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class OrganizationDocumentsServiceImpl implements OrganizationDocumentsService {

    private final OrganizationService organizationService;

    private final DocumentService documentService;

    private final ProgressDocumentsResponseService progressDocumentsResponseService;

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

        final Map<String, List<DocumentInfoDto>> map = userDocuments.stream()
                .map(DocumentInfoDto::fromDocument)
                .collect(groupingBy(DocumentInfoDto::getDocumentState, HashMap::new, toCollection(ArrayList::new)));

        final List<DocumentInfoDto> joinToMe = progressDocumentsResponseService.getAllByUserId(userId).stream()
                .map(ProgressDocumentsResponse::getDocumentId)
                .map(documentService::get)
                .map(DocumentInfoDto::fromDocument)
                .collect(toList());

        joinToMe.forEach(documentInfo -> documentInfo.setDocumentState(JOIN_TO_ME.name()));

        return UserDocumentsInfoDto.builder()
                .heapDocuments(map.get(HEAP.name()))
                .waitingDocuments(map.get(WAITING.name()))
                .progressDocuments(map.get(PENDING.name()))
                .answeredDocuments(map.get(ANSWERED.name()))
                .joinToMe(joinToMe).build();
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

    @Override
    public HeapDocumentViewDto getHeapDocument(final Long documentId) {
        final HeapDocumentViewDto heapDocumentViewDto = documentService.getHeapDocument(documentId);
        final Organization organization = organizationService.get(heapDocumentViewDto.getOrganizationId());

        heapDocumentViewDto.setAllRoles(organization.getOrganizationRoles().stream()
                .map(OrganizationRoleInfoDto::fromOrganizationRole)
                .collect(toList()));

        return heapDocumentViewDto;
    }

    @Override
    public void approveHeapDocument(final HeapDocumentViewDto heapDocumentViewDto) {
        final Organization organization = organizationService.get(heapDocumentViewDto.getOrganizationId());
        final List<Long> usersId = organization.getUsers().stream()
                .filter(user -> user.getOrganizationRoles().stream()
                        .map(OrganizationRole::getId)
                        .anyMatch(roleId -> heapDocumentViewDto.getRoles().contains(roleId)))
                .map(User::getId)
                .collect(toList());

        usersId.forEach(userId -> progressDocumentsResponseService
                .createProgressDocuments(userId, heapDocumentViewDto.getId()));

        final Document document = documentService.get(heapDocumentViewDto.getId());
        document.setState(WAITING);
        documentService.save(document);
    }

    @Override
    public WaitingDocumentViewDto getWaitingDocument(final Long documentId) {
        final Document document = documentService.get(documentId);
        final WaitingDocumentViewDto waitingDocumentViewDto = WaitingDocumentViewDto.fromDocument(document);
        final Organization organization = organizationService.get(document.getOrganizationId());

        waitingDocumentViewDto.setOrganizationName(organization.getName());
        waitingDocumentViewDto.setCount(progressDocumentsResponseService.getCount(documentId));

        return waitingDocumentViewDto;
    }

    @Override
    public JoinToMeDocumentViewDto getJoinToMeDocument(final Long documentId) {
        final Document document = documentService.get(documentId);
        final Organization organization = organizationService.get(document.getOrganizationId());
        final JoinToMeDocumentViewDto joinToMeDocumentViewDto = JoinToMeDocumentViewDto.fromDocument(document);
        joinToMeDocumentViewDto.setOrganizationName(organization.getName());

        return joinToMeDocumentViewDto;
    }

    @Override
    public void downloadDocumentForCheck(final Long documentId) {
        final Document document = documentService.get(documentId);
        if (document.getState() == WAITING) {
            document.setState(PENDING);
            documentService.save(document);
        }
    }

    @Override
    public void answerDocument(final Long documentId, final DocumentAnswerDto answer) {
        final UserPrinciple userPrinciple =
                (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        final ProgressDocumentsResponse progressDocumentsResponse =
                progressDocumentsResponseService.get(userPrinciple.getId(), documentId);
        progressDocumentsResponse.setAnswer(answer.getAnswer()
                ? APPROVE
                : DENY);
        progressDocumentsResponseService.save(progressDocumentsResponse);
        final List<ProgressDocumentsResponse> list = progressDocumentsResponseService.getAllByDocumentId(documentId);
        boolean inProgress = list.stream()
                .map(ProgressDocumentsResponse::getAnswer)
                .anyMatch(answer1 -> answer1.equals(NOT_ANSWER));
        if (!inProgress) {
            final Document document = documentService.get(documentId);
            document.setState(ANSWERED);
            document.setAnswer(true);
            this.documentService.save(document);
        }
    }

    @Override
    @SneakyThrows
    public String getMediaType(final Long documentId) {
        final Document document = documentService.get(documentId);
        return Files.probeContentType(new File(document.getName()).toPath());
    }
}
