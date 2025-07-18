package com.RenanMartins.apirestfulv1.service;

import com.RenanMartins.apirestfulv1.exception.EntidadeNaoEncontradaException;
import com.RenanMartins.apirestfulv1.exception.RegraNegocioException;
import com.RenanMartins.apirestfulv1.model.Cliente;
import com.RenanMartins.apirestfulv1.model.Favorito;
import com.RenanMartins.apirestfulv1.model.Filme;
import com.RenanMartins.apirestfulv1.repository.ClienteRepository;
import com.RenanMartins.apirestfulv1.repository.FavoritoRepository;
import com.RenanMartins.apirestfulv1.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FavoritoService {

    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FilmeRepository filmeRepository;

    public List<Favorito> listarFavoritos(Long clienteId) {
        return favoritoRepository.findByCliente_Id(clienteId);
    }

    public Page<Favorito> listarFavoritosPaginado(Long clienteId, Pageable pageable) {
        return favoritoRepository.findByCliente_Id(clienteId, pageable);
    }

    @Transactional
    public Favorito adicionarFavorito(Long clienteId, Long filmeId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente não encontrado."));
        Filme filme = filmeRepository.findById(filmeId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Filme não encontrado."));

        favoritoRepository.findByCliente_IdAndFilme_Id(clienteId, filmeId)
                .ifPresent(f -> { throw new RegraNegocioException("Filme já está nos favoritos."); });

        Favorito favorito = new Favorito();
        favorito.setCliente(cliente);
        favorito.setFilme(filme);
        return favoritoRepository.save(favorito);
    }

    @Transactional
    public void removerFavorito(Long clienteId, Long filmeId) {
        Favorito favorito = favoritoRepository.findByCliente_IdAndFilme_Id(clienteId, filmeId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Favorito não encontrado."));
        favoritoRepository.delete(favorito);
    }
}
