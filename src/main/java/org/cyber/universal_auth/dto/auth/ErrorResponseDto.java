package org.cyber.universal_auth.dto.auth;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ErrorResponseDto {
    private final Integer status;
    private final String error;
    private final String message;
}

