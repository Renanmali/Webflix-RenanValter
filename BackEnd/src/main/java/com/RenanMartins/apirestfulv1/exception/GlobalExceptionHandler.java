package com.RenanMartins.apirestfulv1.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // ✅ Mantido, apenas ajustado para o novo ErrorResponse
    @ExceptionHandler({EntidadeNaoEncontradaException.class, EntityNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleEntidadeNaoEncontrada(
            Exception e, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(), // Usando getReasonPhrase() para "Not Found"
                request.getMethod(),
                request.getRequestURI(),
                null,
                e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // ✨ NOVO HANDLER para nossas regras de negócio!
    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErrorResponse> handleRegraNegocioException(
            RegraNegocioException e, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(), // 400 Bad Request é ideal para isso
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                request.getMethod(),
                request.getRequestURI(),
                null,
                e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    // ✅ Mantido, apenas ajustado para o novo ErrorResponse
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleSQLIntegrityConstraintViolation(
            SQLIntegrityConstraintViolationException e, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(), // 409 Conflict é mais semântico para violação de chave
                HttpStatus.CONFLICT.getReasonPhrase(),
                request.getMethod(),
                request.getRequestURI(),
                null,
                "Violação de integridade de dados. O recurso pode já existir ou uma referência não foi encontrada.");
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    // ✅ Mantido, apenas ajustado para o novo ErrorResponse
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            ConstraintViolationException e, HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        for (ConstraintViolation<?> cv : e.getConstraintViolations()) {
            map.put(cv.getPropertyPath().toString(), cv.getMessage());
        }

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(),
                request.getMethod(),
                request.getRequestURI(),
                map, // O mapa de erros de validação vai aqui
                "Erro de validação nos campos da requisição.");
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
