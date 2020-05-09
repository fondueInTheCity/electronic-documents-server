package edu.fondue.electronicdocuments.services;

import edu.fondue.electronicdocuments.configuration.UserPrinciple;
import edu.fondue.electronicdocuments.dto.ProgressDocumentAnswerDto;
import edu.fondue.electronicdocuments.dto.document.*;
import edu.fondue.electronicdocuments.dto.organization.MyOrganizationDocumentsInfoDto;
import edu.fondue.electronicdocuments.dto.organization.OrganizationDocumentsInfoDto;
import edu.fondue.electronicdocuments.dto.organization.OrganizationRoleInfoDto;
import edu.fondue.electronicdocuments.enums.DocumentAnswer;
import edu.fondue.electronicdocuments.enums.DocumentState;
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
import static java.lang.String.format;
import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class OrganizationDocumentsServiceImpl implements OrganizationDocumentsService {

    private final OrganizationService organizationService;

    private final DocumentService documentService;

    private final ProgressDocumentsResponseService progressDocumentsResponseService;

    private final UserService userService;

    private final StorageService storageService;

    private final CryptoService cryptoService;

    @Override
    @Transactional
    public void uploadOrganizationFile(final Long organizationId, final Long userId,
                                       final MultipartFile file) {
        documentService.uploadOrganizationFile(organizationId, userId, file);
    }

    @Override
    @Transactional
    public MyOrganizationDocumentsInfoDto getUserOrganizationDocuments(final Long organizationId, final Long userId) {
        final List<DocumentInfoDto> joinToMe = progressDocumentsResponseService.getAllByUserId(userId).stream()
                .filter(item -> item.getAnswer().equals(NOT_ANSWER))
                .map(ProgressDocumentsResponse::getDocumentId)
                .map(documentService::get)
                .map(DocumentInfoDto::fromDocument)
                .collect(toList());

        joinToMe.forEach(documentInfo -> documentInfo.setDocumentState(JOIN_TO_ME.name()));
        final MyOrganizationDocumentsInfoDto dto = documentService.getUserOrganizationDocuments(organizationId, userId);
        dto.setJoinToMeDocuments(joinToMe);
        return dto;
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
                .filter(item -> item.getAnswer().equals(NOT_ANSWER))
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
                .joinToMeDocuments(joinToMe).build();
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
        final boolean waiting = progressDocumentsResponseService.getAllByDocumentId(documentId).stream()
                .allMatch(item -> item.getAnswer().equals(NOT_ANSWER));
        final Document document = documentService.get(documentId);

        if (waiting && document.getState().equals(WAITING)) {
            document.setState(PENDING);
            documentService.save(document);
        }

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
        final UserPrinciple userPrinciple =
                (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final long currentId = userPrinciple.getId();
        final Organization organization = organizationService.get(heapDocumentViewDto.getOrganizationId());
        final List<Long> usersId = organization.getUsers().stream()
                .filter(user -> user.getOrganizationRoles().stream()
                        .map(OrganizationRole::getId)
                        .anyMatch(roleId -> heapDocumentViewDto.getRoles().contains(roleId)))
                .map(User::getId)
                .filter(id -> !id.equals(currentId))
                .collect(toList());

        usersId.forEach(userId -> progressDocumentsResponseService
                .createProgressDocuments(userId, heapDocumentViewDto.getId()));

        final Document document = documentService.get(heapDocumentViewDto.getId());
        document.setState(WAITING);

        final String pathTo = format("electronic-documents/organizations/%d/%d/%s",
                heapDocumentViewDto.getOrganizationId(), currentId, heapDocumentViewDto.getName());
        storageService.renameFile(format("electronic-documents%s", document.getPath()), pathTo);
        document.setPath(pathTo);
        document.setName(heapDocumentViewDto.getName());
        documentService.save(document);

        cryptoService.addSignatureToPdf(document.getPath(), document.getOwnerId(), document.getName(),
                "Send for approve");
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

        final Document document = documentService.get(documentId);
        final DocumentAnswer documentAnswer = answer.getAnswer() ? APPROVE : DENY;
        cryptoService.addSignatureToPdf(document.getPath(), userPrinciple.getId(), document.getName(),
                documentAnswer.name());

        final ProgressDocumentsResponse progressDocumentsResponse =
                progressDocumentsResponseService.get(userPrinciple.getId(), documentId);
        progressDocumentsResponse.setAnswer(documentAnswer);
        progressDocumentsResponseService.save(progressDocumentsResponse);

        final List<ProgressDocumentsResponse> list = progressDocumentsResponseService.getAllByDocumentId(documentId);
        boolean inProgress = list.stream()
                .map(ProgressDocumentsResponse::getAnswer)
                .anyMatch(answer1 -> answer1.equals(NOT_ANSWER));
        if (!inProgress) {
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

    @Override
    public PendingDocumentViewDto getPendingDocument(final Long documentId) {
        final Document document = documentService.get(documentId);
        final List<ProgressDocumentAnswerDto> list = progressDocumentsResponseService.getAllByDocumentId(documentId)
                .stream()
                .map(ProgressDocumentAnswerDto::fromProgressDocumentsResponse)
                .collect(toList());
        list.forEach(item -> item.setUsername(userService.find(item.getUserId()).getUsername()));

        final Organization organization = organizationService.get(document.getOrganizationId());
        final PendingDocumentViewDto pendingDocumentViewDto = PendingDocumentViewDto.fromDocument(document);

        pendingDocumentViewDto.setOrganizationName(organization.getName());
        pendingDocumentViewDto.setAnswers(list);
        return pendingDocumentViewDto;
    }

    @Override
    public AnsweredDocumentViewDto getAnsweredDocument(final Long documentId) {
        final Document document = documentService.get(documentId);
        final AnsweredDocumentViewDto answeredDocumentViewDto = AnsweredDocumentViewDto.fromDocument(document);
        final Organization organization = organizationService.get(document.getOrganizationId());

        answeredDocumentViewDto.setOrganizationName(organization.getName());

        answeredDocumentViewDto.setJoins(progressDocumentsResponseService.getAllByDocumentId(documentId).stream()
                .map(ProgressDocumentsResponse::getUserId)
                .map(userId -> userService.find(userId).getUsername())
                .collect(toList()));
        return answeredDocumentViewDto;
    }

    @Override
    public OrganizationDocumentsInfoDto getOrganizationDocumentsInfo(final Long organizationId) {
        final List<DocumentInfoDto> list = documentService.getOrganizationDocumentsInfo(organizationId);
        return OrganizationDocumentsInfoDto.builder()
                .documents(list).build();
    }

    @Override
    public String getDocumentState(final Long documentId) {
        final UserPrinciple userPrinciple =
                (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userPrinciple.getId();
        final DocumentState state = documentService.get(documentId).getState();
        if (state.equals(WAITING) || state.equals(PENDING) ) {
            return (progressDocumentsResponseService.get(userId, documentId) == null
                    ? state
                    : JOIN_TO_ME).name();
        }
        return documentService.get(documentId).getState().name();
    }
}
