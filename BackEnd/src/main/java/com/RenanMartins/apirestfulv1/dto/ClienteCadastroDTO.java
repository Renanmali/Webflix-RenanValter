package com.RenanMartins.apirestfulv1.dto;

import jakarta.validation.constraints.NotEmpty;

/**
 * DTO para registro de clientes com confirmação de senha.
 */
public record ClienteCadastroDTO(
        @NotEmpty String conta,
        @NotEmpty String senha,
        @NotEmpty String confirmacaoSenha
) {
        
}