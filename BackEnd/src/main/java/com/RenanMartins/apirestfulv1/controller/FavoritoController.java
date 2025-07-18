package com.RenanMartins.apirestfulv1.controller;

import com.RenanMartins.apirestfulv1.model.Favorito;
import com.RenanMartins.apirestfulv1.model.ResultadoPaginado;
import com.RenanMartins.apirestfulv1.service.FavoritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("favoritos")
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;

    @GetMapping("/cliente/{clienteId}")
    public List<Favorito> listarFavoritos(@PathVariable Long clienteId) {
        return favoritoService.listarFavoritos(clienteId);
    }

    @GetMapping("/cliente/{clienteId}/paginado")
    public ResultadoPaginado<Favorito> listarFavoritosPaginado(@PathVariable Long clienteId,
                                                               @RequestParam(value = "pagina", defaultValue = "0") int pagina,
                                                               @RequestParam(value = "tamanho", defaultValue = "5") int tamanho) {
        Pageable pageable = PageRequest.of(pagina, tamanho);
        Page<Favorito> page = favoritoService.listarFavoritosPaginado(clienteId, pageable);
        return new ResultadoPaginado<>(
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getContent()
        );
    }

    @PostMapping("/cliente/{clienteId}/filme/{filmeId}")
    public Favorito adicionarFavorito(@PathVariable Long clienteId, @PathVariable Long filmeId) {
        return favoritoService.adicionarFavorito(clienteId, filmeId);
    }

    @DeleteMapping("/cliente/{clienteId}/filme/{filmeId}")
    public void removerFavorito(@PathVariable Long clienteId, @PathVariable Long filmeId) {
        favoritoService.removerFavorito(clienteId, filmeId);
    }
}
