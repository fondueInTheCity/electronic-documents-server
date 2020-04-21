package edu.fondue.electronicdocuments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class JwtResponseDto {

    private static final String type = "Bearer";

    private String token;

    private String username;

    private Long id;
}
