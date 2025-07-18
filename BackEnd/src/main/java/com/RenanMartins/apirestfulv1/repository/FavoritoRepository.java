package com.RenanMartins.apirestfulv1.repository;

import com.RenanMartins.apirestfulv1.model.Favorito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
    List<Favorito> findByCliente_Id(Long clienteId);

    Page<Favorito> findByCliente_Id(Long clienteId, Pageable pageable);

    Optional<Favorito> findByCliente_IdAndFilme_Id(Long clienteId, Long filmeId);

    void deleteByCliente_IdAndFilme_Id(Long clienteId, Long filmeId);
}
