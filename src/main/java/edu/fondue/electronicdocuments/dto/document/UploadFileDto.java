package edu.fondue.electronicdocuments.dto.document;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileDto {

    private MultipartFile file;
}
