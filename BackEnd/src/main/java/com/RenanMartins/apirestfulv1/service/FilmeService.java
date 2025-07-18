package com.RenanMartins.apirestfulv1.service;

import com.RenanMartins.apirestfulv1.exception.EntidadeNaoEncontradaException;
import com.RenanMartins.apirestfulv1.model.Filme;
import com.RenanMartins.apirestfulv1.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FilmeService { // Classe Renomeada

    @Autowired
    private FilmeRepository filmeRepository; // Repositório injetado correto

    public List<Filme> recuperarFilmes() {
        // Usando o método customizado para garantir que o gênero venha junto
        return filmeRepository.findAllComGenero();
    }

    public Filme cadastrarFilme(Filme filme) {
        return filmeRepository.save(filme);
    }

    @Transactional
    public Filme alterarFilme(Filme filme) {
        // A lógica de buscar primeiro e depois salvar está correta
        filmeRepository.findById(filme.getId())
            .orElseThrow(() -> new EntidadeNaoEncontradaException(
                    "Filme número " + filme.getId() + " não encontrado."));
        return filmeRepository.save(filme);
    }

    @Transactional
    public void removerFilme(long id) {
        // Verificando se o filme existe antes de deletar
        if (!filmeRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Filme número " + id + " não encontrado para remoção.");
        }
        filmeRepository.deleteById(id);
    }

    public Filme recuperarFilmePorId(long id) {
        return filmeRepository.findById(id)
            .orElseThrow(() -> new EntidadeNaoEncontradaException(
                "Filme número " + id + " não encontrado."));
    }

    public Page<Filme> recuperarFilmesComPaginacao(Pageable pageable, String titulo) {
        // Usando o novo método derivado, muito mais simples
        return filmeRepository.findByTituloContainingIgnoreCase(titulo, pageable);
    }

    public List<Filme> recuperarFilmesPorSlugGenero(String slugGenero) {
        // Usando o novo método derivado
        return filmeRepository.findByGenero_Slug(slugGenero);
    }

    public Page<Filme> recuperarFilmesPaginadosPorSlugDoGenero(String slugGenero, Pageable pageable) {
        if (slugGenero != null && !slugGenero.isEmpty()) {
            // Usando o novo método derivado
            return filmeRepository.findByGenero_Slug(slugGenero, pageable);
        } else {
            return filmeRepository.findAll(pageable);
        }
    }
}