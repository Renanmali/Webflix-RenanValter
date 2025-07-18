package com.RenanMartins.apirestfulv1.repository;

import com.RenanMartins.apirestfulv1.model.Genero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Adicionar a anotação @Repository é uma boa prática
public interface GeneroRepository extends JpaRepository<Genero, Long> {
    // Busca por slug para ser usada no service
    Optional<Genero> findBySlug(String slug);

    Page<Genero> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
