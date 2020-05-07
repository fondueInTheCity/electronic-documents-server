package edu.fondue.electronicdocuments.dto;

import edu.fondue.electronicdocuments.models.ProgressDocumentsResponse;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgressDocumentAnswerDto {

    private Long id;

    private String username;

    private Long userId;

    private String answer;

    public static ProgressDocumentAnswerDto fromProgressDocumentsResponse(
            final ProgressDocumentsResponse progressDocumentsResponse) {
        return ProgressDocumentAnswerDto.builder()
                .id(progressDocumentsResponse.getId())
                .answer(progressDocumentsResponse.getAnswer().name())
                .userId(progressDocumentsResponse.getUserId()).build();
    }
}
