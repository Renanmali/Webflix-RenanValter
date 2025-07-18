package com.RenanMartins.apirestfulv1.service;

import com.RenanMartins.apirestfulv1.exception.EntidadeNaoEncontradaException;
import com.RenanMartins.apirestfulv1.exception.RegraNegocioException;
import com.RenanMartins.apirestfulv1.model.*;
import com.RenanMartins.apirestfulv1.repository.ClienteRepository;
import com.RenanMartins.apirestfulv1.repository.CopiaRepository;
import com.RenanMartins.apirestfulv1.repository.LocacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LocacaoService {

    @Autowired
    private LocacaoRepository locacaoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CopiaRepository copiaRepository;

    @Transactional
    public Locacao realizarLocacao(Long clienteId, Long copiaId) {
        // 1. Validar se o cliente e a cópia existem
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente não encontrado."));

        Copia copia = copiaRepository.findById(copiaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cópia do filme não encontrada."));

        // 2. Aplicar a Regra de Negócio: a cópia deve estar disponível
        if (copia.getStatus() != StatusCopia.DISPONIVEL) {
            throw new RegraNegocioException("Esta cópia não está disponível para locação.");
        }

        // 3. Mudar o status da cópia para ALUGADO
        copia.setStatus(StatusCopia.ALUGADO);
        copiaRepository.save(copia);

        // 4. Criar o registro da locação
        Locacao novaLocacao = new Locacao();
        novaLocacao.setCliente(cliente);
        novaLocacao.setCopia(copia);
        novaLocacao.setDataLocacao(LocalDateTime.now());
        novaLocacao.setDataDevolucaoPrevista(LocalDate.now().plusDays(3)); // Prazo de 3 dias

        return locacaoRepository.save(novaLocacao);
    }

    @Transactional
    public Locacao registrarDevolucao(Long locacaoId) {
        // 1. Validar se a locação existe
        Locacao locacao = locacaoRepository.findById(locacaoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Registro de locação não encontrado."));

        // 2. Regra de Negócio: não se pode devolver algo já devolvido
        if (locacao.getDataDevolucaoReal() != null) {
            throw new RegraNegocioException("Esta locação já foi finalizada.");
        }

        // 3. Mudar o status da cópia de volta para DISPONIVEL
        Copia copiaDevolvida = locacao.getCopia();
        copiaDevolvida.setStatus(StatusCopia.DISPONIVEL);
        copiaRepository.save(copiaDevolvida);

        // 4. Registrar a data de devolução e calcular multa (lógica de multa a ser implementada)
        locacao.setDataDevolucaoReal(LocalDateTime.now());
        // if (locacao.getDataDevolucaoReal().toLocalDate().isAfter(locacao.getDataDevolucaoPrevista())) {
        //     // Lógica para calcular e salvar a multa
        // }

        return locacaoRepository.save(locacao);
    }

    public List<Locacao> listarLocacoesPorCliente(Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            throw new EntidadeNaoEncontradaException("Cliente não encontrado.");
        }
        return locacaoRepository.findByCliente_Id(clienteId);
    }
}
