package com.RenanMartins.apirestfulv1.service;

import com.RenanMartins.apirestfulv1.exception.EntidadeNaoEncontradaException;
import com.RenanMartins.apirestfulv1.model.Genero;
import com.RenanMartins.apirestfulv1.repository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GeneroService {

    @Autowired
    private GeneroRepository generoRepository;

    public Page<Genero> listar(Pageable pageable, String nome) {
        if (nome != null && !nome.isEmpty()) {
            return generoRepository.findByNomeContainingIgnoreCase(nome, pageable);
        }
        return generoRepository.findAll(pageable);
    }

    public Genero recuperarPorId(Long id) {
        return generoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("G\u00eanero n\u00e3o encontrado."));
    }

    @Transactional
    public Genero cadastrar(Genero genero) {
        return generoRepository.save(genero);
    }

    @Transactional
    public Genero alterar(Genero genero) {
        generoRepository.findById(genero.getId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("G\u00eanero n\u00e3o encontrado."));
        return generoRepository.save(genero);
    }

    @Transactional
    public void remover(Long id) {
        if (!generoRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("G\u00eanero n\u00e3o encontrado.");
        }
        generoRepository.deleteById(id);
    }
}
