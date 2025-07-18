package com.RenanMartins.apirestfulv1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.RenanMartins.apirestfulv1.model.Cliente;
import com.RenanMartins.apirestfulv1.service.AutenticacaoService;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("autenticacao")   // http://localhost:8080/autenticacao
public class AutenticacaoController {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @PostMapping("login")  // http://localhost:8080/autenticacao/login
    public Cliente login(@RequestBody Cliente usuario) {
        System.out.println(usuario.getConta() + " " + usuario.getSenha());
        Cliente usuarioLogado = autenticacaoService.login(usuario);
        if (usuarioLogado != null) {
            return usuarioLogado;
        }
        return new Cliente();
    }
}
