package com.RenanMartins.apirestfulv1.service;

import com.RenanMartins.apirestfulv1.model.Cliente;
import com.RenanMartins.apirestfulv1.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticacaoService {

    @Autowired
    private ClienteRepository clienteRepository; // Renomeado para clareza

    public Cliente login(Cliente clienteParaLogar) {
        // 1. Busca o cliente pela conta (que deve ser única)
        Optional<Cliente> optCliente = clienteRepository.findByConta(clienteParaLogar.getConta());

        // 2. Verifica se o cliente existe e se a senha bate
        if (optCliente.isPresent()) {
            Cliente clienteDoBanco = optCliente.get();
            // A comparação de senhas é feita aqui!
            // (Em um projeto real, as senhas seriam criptografadas - aqui usamos texto plano)
            if (clienteDoBanco.getSenha().equals(clienteParaLogar.getSenha())) {
                return clienteDoBanco; // Login bem-sucedido
            }
        }

        return null; // Retorna nulo se o cliente não existe ou a senha está incorreta
    }
}