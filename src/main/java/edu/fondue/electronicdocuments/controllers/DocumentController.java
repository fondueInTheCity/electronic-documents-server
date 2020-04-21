package edu.fondue.electronicdocuments.controllers;

import edu.fondue.electronicdocuments.dto.document.ChangeDocumentStateDto;
import edu.fondue.electronicdocuments.dto.document.DocumentAnswerDto;
import edu.fondue.electronicdocuments.dto.document.OrganizationDocumentViewDto;
import edu.fondue.electronicdocuments.dto.document.UserDocumentsInfoDto;
import edu.fondue.electronicdocuments.dto.organization.OrganizationDocumentsInfoDto;
import edu.fondue.electronicdocuments.services.OrganizationDocumentsService;
import lombok.RequiredArgsConstructor;
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
}
