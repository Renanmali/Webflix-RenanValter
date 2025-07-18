package com.RenanMartins.apirestfulv1.service;

import com.RenanMartins.apirestfulv1.exception.RegraNegocioException;
import com.RenanMartins.apirestfulv1.model.Cliente;
import com.RenanMartins.apirestfulv1.dto.ClienteCadastroDTO;
import com.RenanMartins.apirestfulv1.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public Cliente registrarCliente(ClienteCadastroDTO dto) {
        if (!dto.senha().equals(dto.confirmacaoSenha())) {
            throw new RegraNegocioException("Senhas não conferem.");
        }
        if (clienteRepository.existsByConta(dto.conta())) {
            throw new RegraNegocioException("Conta já cadastrada.");
        }
        Cliente cliente = new Cliente(dto.conta(), dto.senha());
        return clienteRepository.save(cliente);
    }

    public boolean contaJaExiste(String conta) {
        return clienteRepository.existsByConta(conta);
    }
}
