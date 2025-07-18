package com.RenanMartins.apirestfulv1.exception;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 🔄 REFINAMENTO SUGERIDO
 * - 'localDateTime' para 'timestamp' (mais comum em APIs)
 * - 'metodo' para 'httpMethod' (consistência com o inglês)
 * - 'map' para 'validationErrors' (mais descritivo)
 */
public record ErrorResponse(
    LocalDateTime timestamp,
    int status,
    String error,
    String httpMethod,
    String path,
    Map<String, String> validationErrors,
    String message
) {
}
