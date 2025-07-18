package com.RenanMartins.apirestfulv1.exception;

// Esta exceção será usada para todas as regras de negócio da locadora
// que impedem uma operação de ser realizada.
public class RegraNegocioException extends RuntimeException {
    public RegraNegocioException(String message) {
        super(message);
    }
}