package com.RenanMartins.apirestfulv1.controller;

import com.RenanMartins.apirestfulv1.dto.ClienteCadastroDTO;
import com.RenanMartins.apirestfulv1.model.Cliente;
import com.RenanMartins.apirestfulv1.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody @Valid ClienteCadastroDTO dto) {
        if (!dto.senha().equals(dto.confirmacaoSenha())) {
            return ResponseEntity.badRequest().body("Senha e confirmação de senha não conferem.");
        }
        if (clienteService.contaJaExiste(dto.conta())) {
            return ResponseEntity.badRequest().body("Já existe um usuário com essa conta.");
        }
        Cliente cliente = clienteService.registrarCliente(dto);
        return ResponseEntity.ok(cliente);
    }
}