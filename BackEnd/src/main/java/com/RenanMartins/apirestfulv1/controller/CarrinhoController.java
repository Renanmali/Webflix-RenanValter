package com.RenanMartins.apirestfulv1.controller;

import com.RenanMartins.apirestfulv1.model.Carrinho;
import com.RenanMartins.apirestfulv1.model.Locacao;
import com.RenanMartins.apirestfulv1.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("carrinhos")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @GetMapping("/cliente/{clienteId}")
    public Carrinho visualizarCarrinho(@PathVariable Long clienteId) {
        return carrinhoService.obterCarrinhoAberto(clienteId);
    }

    @PostMapping("/cliente/{clienteId}/adicionar/{copiaId}")
    public Carrinho adicionarItem(@PathVariable Long clienteId, @PathVariable Long copiaId) {
        return carrinhoService.adicionarItem(clienteId, copiaId);
    }

    @PostMapping("/{carrinhoId}/finalizar")
    public List<Locacao> finalizarCarrinho(@PathVariable Long carrinhoId) {
        return carrinhoService.finalizarCarrinho(carrinhoId);
    }
}
