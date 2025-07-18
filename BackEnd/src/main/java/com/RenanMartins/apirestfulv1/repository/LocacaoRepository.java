package com.RenanMartins.apirestfulv1.repository;

import com.RenanMartins.apirestfulv1.model.Locacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocacaoRepository extends JpaRepository<Locacao, Long> {
    // Método para buscar o histórico de um cliente
    List<Locacao> findByCliente_Id(Long clienteId);

    // Método para encontrar uma locação ativa por uma cópia (para devolução)
    Optional<Locacao> findByCopia_IdAndDataDevolucaoRealIsNull(Long copiaId);
}
