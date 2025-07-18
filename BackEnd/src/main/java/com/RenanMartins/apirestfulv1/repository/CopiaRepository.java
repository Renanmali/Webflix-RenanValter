package com.RenanMartins.apirestfulv1.repository;

import com.RenanMartins.apirestfulv1.model.Copia;
import com.RenanMartins.apirestfulv1.model.StatusCopia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CopiaRepository extends JpaRepository<Copia, Long> {
    // Método útil para encontrar cópias disponíveis de um filme
    List<Copia> findByFilme_IdAndStatus(Long filmeId, StatusCopia status);
}
