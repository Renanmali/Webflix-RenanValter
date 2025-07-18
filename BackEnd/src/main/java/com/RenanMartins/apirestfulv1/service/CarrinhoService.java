package com.RenanMartins.apirestfulv1.service;

import com.RenanMartins.apirestfulv1.exception.EntidadeNaoEncontradaException;
import com.RenanMartins.apirestfulv1.exception.RegraNegocioException;
import com.RenanMartins.apirestfulv1.model.*;
import com.RenanMartins.apirestfulv1.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private CarrinhoItemRepository carrinhoItemRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CopiaRepository copiaRepository;

    @Autowired
    private LocacaoService locacaoService;

    public Carrinho obterCarrinhoAberto(Long clienteId) {
        return carrinhoRepository.findByCliente_IdAndStatus(clienteId, StatusCarrinho.ABERTO)
                .orElseGet(() -> criarCarrinho(clienteId));
    }

    private Carrinho criarCarrinho(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente n\u00e3o encontrado."));
        Carrinho carrinho = new Carrinho();
        carrinho.setCliente(cliente);
        carrinho.setStatus(StatusCarrinho.ABERTO);
        return carrinhoRepository.save(carrinho);
    }

    @Transactional
    public Carrinho adicionarItem(Long clienteId, Long copiaId) {
        Carrinho carrinho = obterCarrinhoAberto(clienteId);
        Copia copia = copiaRepository.findById(copiaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("C\u00f3pia n\u00e3o encontrada."));

        if (copia.getStatus() != StatusCopia.DISPONIVEL) {
            throw new RegraNegocioException("Esta c\u00f3pia n\u00e3o est\u00e1 dispon\u00edvel para loca\u00e7\u00e3o.");
        }

        CarrinhoItem item = new CarrinhoItem();
        item.setCarrinho(carrinho);
        item.setCopia(copia);
        carrinho.getItens().add(item);
        carrinhoItemRepository.save(item);
        return carrinho;
    }

    @Transactional
    public List<Locacao> finalizarCarrinho(Long carrinhoId) {
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Carrinho n\u00e3o encontrado."));

        if (carrinho.getStatus() != StatusCarrinho.ABERTO) {
            throw new RegraNegocioException("Carrinho j\u00e1 finalizado.");
        }

        List<Locacao> locacoes = new ArrayList<>();
        for (CarrinhoItem item : carrinho.getItens()) {
            Locacao locacao = locacaoService.realizarLocacao(carrinho.getCliente().getId(), item.getCopia().getId());
            locacoes.add(locacao);
        }

        carrinho.setStatus(StatusCarrinho.FINALIZADO);
        carrinhoRepository.save(carrinho);
        return locacoes;
    }
}
