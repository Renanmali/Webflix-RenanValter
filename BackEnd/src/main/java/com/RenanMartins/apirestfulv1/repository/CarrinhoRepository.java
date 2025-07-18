package com.RenanMartins.apirestfulv1.repository;

import com.RenanMartins.apirestfulv1.model.Carrinho;
import com.RenanMartins.apirestfulv1.model.StatusCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    Optional<Carrinho> findByCliente_IdAndStatus(Long clienteId, StatusCarrinho status);
}
