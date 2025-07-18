package com.RenanMartins.apirestfulv1.controller;

import com.RenanMartins.apirestfulv1.dto.LocacaoRequestDTO; // Você precisará criar este DTO
import com.RenanMartins.apirestfulv1.model.Locacao;
import com.RenanMartins.apirestfulv1.service.LocacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Novo controller para gerenciar as operações de Locação.
 * - POST /locacoes: Para um cliente alugar um ou mais filmes.
 * - GET /locacoes/cliente/{id}: Para ver o histórico de um cliente.
 * - POST /locacoes/{id}/devolucao: Para registrar a devolução de um filme.
 */
@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("locacoes")
public class LocacaoController {

    @Autowired
    private LocacaoService locacaoService;

    /**
     * Endpoint para realizar uma nova locação.
     * Espera um corpo de requisição (DTO) com o ID do cliente e o ID da cópia
     * que ele deseja alugar.
     */
    @PostMapping
    public ResponseEntity<Locacao> realizarLocacao(@RequestBody LocacaoRequestDTO locacaoRequest) {
        Locacao novaLocacao = locacaoService.realizarLocacao(locacaoRequest.clienteId(), locacaoRequest.copiaId());
        return ResponseEntity.ok(novaLocacao);
    }

    /**
     * Endpoint para registrar a devolução de uma locação.
     */
    @PostMapping("/{idLocacao}/devolucao")
    public ResponseEntity<Locacao> registrarDevolucao(@PathVariable Long idLocacao) {
        Locacao locacaoAtualizada = locacaoService.registrarDevolucao(idLocacao);
        return ResponseEntity.ok(locacaoAtualizada);
    }

    /**
     * Endpoint para listar todas as locações de um cliente específico.
     */
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Locacao>> listarLocacoesPorCliente(@PathVariable Long clienteId) {
        List<Locacao> locacoes = locacaoService.listarLocacoesPorCliente(clienteId);
        return ResponseEntity.ok(locacoes);
    }
}
