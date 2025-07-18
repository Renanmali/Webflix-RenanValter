package com.RenanMartins.apirestfulv1.controller;

import com.RenanMartins.apirestfulv1.model.Filme; // IMPORT MUDOU
import com.RenanMartins.apirestfulv1.model.ResultadoPaginado;
import com.RenanMartins.apirestfulv1.service.FilmeService; // SERVIÇO MUDOU
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Este controller agora gerencia o catálogo de Filmes.
 * As referências a 'Produto' foram trocadas por 'Filme'.
 * O endpoint principal foi alterado de '/produtos' para '/filmes'.
 */
@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("filmes")  // ENDPOINT MUDOU
public class CatalogoController {

    @Autowired
    private FilmeService filmeService; // SERVIÇO MUDOU

    @GetMapping
    public List<Filme> recuperarFilmes() {
        return filmeService.recuperarFilmes();
    }

    @GetMapping("{idFilme}")
    public Filme recuperarFilmePorId(@PathVariable("idFilme") long id) {
        return filmeService.recuperarFilmePorId(id);
    }

    // A categoria agora pode ser pensada como 'Gênero'
    @GetMapping("genero/{slugGenero}")
    public List<Filme> recuperarFilmesPorSlugGenero(@PathVariable("slugGenero") String slugGenero) {
        return filmeService.recuperarFilmesPorSlugGenero(slugGenero);
    }

    @PostMapping
    public Filme cadastrarFilme(@RequestBody Filme filme) {
        return filmeService.cadastrarFilme(filme);
    }

    @PutMapping
    public Filme alterarFilme(@RequestBody Filme filme) {
        return filmeService.alterarFilme(filme);
    }

    @DeleteMapping("{idFilme}")
    public void removerFilme(@PathVariable("idFilme") long id) {
        filmeService.removerFilme(id);
    }

    @GetMapping("paginacao")
    public ResultadoPaginado<Filme> recuperarFilmesComPaginacao(
            @RequestParam(value = "pagina", defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", defaultValue = "5") int tamanho,
            @RequestParam(value = "titulo", defaultValue = "") String titulo) { // parâmetro de busca mudou para 'titulo'
        Pageable pageable = PageRequest.of(pagina, tamanho);
        Page<Filme> page = filmeService.recuperarFilmesComPaginacao(pageable, titulo);
        return new ResultadoPaginado<>(
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getContent());
    }

    @GetMapping("genero/paginacao")
    public ResultadoPaginado<Filme> recuperarFilmesPaginadosPorSlugDoGenero(
            @RequestParam(value = "pagina", defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", defaultValue = "3") int tamanho,
            @RequestParam(value = "slugGenero", defaultValue = "") String slugGenero) {
        Pageable pageable = PageRequest.of(pagina, tamanho);
        Page<Filme> page = filmeService.recuperarFilmesPaginadosPorSlugDoGenero(slugGenero, pageable);
        return new ResultadoPaginado<>(
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getContent());
    }
}
