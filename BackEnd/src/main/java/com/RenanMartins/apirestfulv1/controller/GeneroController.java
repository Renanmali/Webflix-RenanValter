package com.RenanMartins.apirestfulv1.controller;

import com.RenanMartins.apirestfulv1.model.Genero;
import com.RenanMartins.apirestfulv1.model.ResultadoPaginado;
import com.RenanMartins.apirestfulv1.service.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("generos")
public class GeneroController {

    @Autowired
    private GeneroService generoService;

    @GetMapping
    public ResultadoPaginado<Genero> listar(
            @RequestParam(value = "pagina", defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", defaultValue = "5") int tamanho,
            @RequestParam(value = "nome", defaultValue = "") String nome) {
        Pageable pageable = PageRequest.of(pagina, tamanho);
        Page<Genero> page = generoService.listar(pageable, nome);
        return new ResultadoPaginado<>(
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getContent());
    }

    @PostMapping
    public Genero cadastrar(@RequestBody Genero genero) {
        return generoService.cadastrar(genero);
    }

    @PutMapping
    public Genero alterar(@RequestBody Genero genero) {
        return generoService.alterar(genero);
    }

    @DeleteMapping("{id}")
    public void remover(@PathVariable Long id) {
        generoService.remover(id);
    }

    @GetMapping("{id}")
    public Genero recuperarPorId(@PathVariable Long id) {
        return generoService.recuperarPorId(id);
    }
}
