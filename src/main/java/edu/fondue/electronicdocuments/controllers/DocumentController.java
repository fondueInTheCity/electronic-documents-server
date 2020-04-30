package edu.fondue.electronicdocuments.controllers;

import edu.fondue.electronicdocuments.dto.document.*;
import edu.fondue.electronicdocuments.dto.organization.OrganizationDocumentsInfoDto;
import edu.fondue.electronicdocuments.services.OrganizationDocumentsService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("documents")
public class DocumentController {

    private final OrganizationDocumentsService service;

    @PostMapping("organization/{organizationId}/user/{userId}")
    public void uploadOrganizationFile(@PathVariable final Long organizationId,
                                       @PathVariable final Long userId,
                                       @RequestBody final MultipartFile file) {
        service.uploadOrganizationFile(organizationId, userId, file);
    }

    @GetMapping("organization/{organizationId}/user/{userId}")
    public OrganizationDocumentsInfoDto uploadOrganizationFile(@PathVariable final Long organizationId,
                                                               @PathVariable final Long userId) {
        return service.getUserOrganizationDocuments(organizationId, userId);
    }

    @GetMapping("{id}")
    public OrganizationDocumentViewDto getOrganizationDocumentView(@PathVariable final Long id) {
        return service.getOrganizationDocumentView(id);
    }

    @GetMapping("user/{userId}")
    public UserDocumentsInfoDto getUserDocumentsInfo(@PathVariable final Long userId) {
        return service.getUserDocumentsInfo(userId);
    }

    @PutMapping("{documentId}")
    public void changeDocumentState(@PathVariable final Long documentId,
                                    @RequestBody final ChangeDocumentStateDto dto) {
        service.changeDocumentState(documentId, dto);
    }

    @PutMapping("{documentId}/approveDeny")
    public void changeDocumentState(@PathVariable final Long documentId,
                                    @RequestBody final DocumentAnswerDto answer) {
        service.approveDenyDocument(documentId, answer);
    }

    @PostMapping("{documentId}/download")
    public byte[] renameOrganizationRole(@PathVariable Long documentId) {
        return service.download(documentId);
    }

    @GetMapping("{documentId}/heap")
    public HeapDocumentViewDto getHeapDocument(@PathVariable Long documentId) {
        return service.getHeapDocument(documentId);
    }

    @PostMapping("{documentId}/heap")
    public void approveHeapDocument(@PathVariable final Long documentId,
                                                   @RequestBody final HeapDocumentViewDto heapDocumentViewDto) {
        service.approveHeapDocument(heapDocumentViewDto);
    }

    @GetMapping("{documentId}/waiting")
    public WaitingDocumentViewDto getWaitingDocument(@PathVariable Long documentId) {
        return service.getWaitingDocument(documentId);
    }

    @GetMapping("{documentId}/join-to-me")
    public JoinToMeDocumentViewDto getJoinToMeDocument(@PathVariable Long documentId) {
        return service.getJoinToMeDocument(documentId);
    }

    @SneakyThrows
    @PostMapping("{documentId}/join-to-me/download")
    public ResponseEntity<byte[]> downloadDocumentForCheck(@PathVariable Long documentId) {

        ClassPathResource pdfFile = new ClassPathResource("Functional_Programming,_Simplified_Scala_edition_by_Alvin_Alexander.pdf");

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).contentLength(pdfFile.contentLength()).body(pdfFile.getInputStream().readAllBytes());
    }

    @PostMapping("{documentId}/join-to-me/answer")
    public void answerDocument(@PathVariable final Long documentId,
                               @RequestBody final DocumentAnswerDto answer) {
        service.answerDocument(documentId, answer);
    }
}
