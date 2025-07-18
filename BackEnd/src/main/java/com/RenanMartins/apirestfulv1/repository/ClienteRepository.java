package com.RenanMartins.apirestfulv1.repository;

import com.RenanMartins.apirestfulv1.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    /**
     * A busca para login deve ser feita apenas pela 'conta'.
     * A verificação da senha é uma responsabilidade da camada de Serviço.
     * Usar Optional<T> é uma boa prática para evitar NullPointerException.
     */
    Optional<Cliente> findByConta(String conta);

    boolean existsByConta(String conta);
}
