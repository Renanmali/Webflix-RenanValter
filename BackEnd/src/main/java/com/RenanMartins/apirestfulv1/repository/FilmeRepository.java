package com.RenanMartins.apirestfulv1.repository;

import com.RenanMartins.apirestfulv1.model.Filme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {

    // Métodos derivados (o Spring cria a query para nós)
    List<Filme> findByGenero_Slug(String slugGenero);

    Page<Filme> findByGenero_Slug(String slugGenero, Pageable pageable);

    Page<Filme> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);

    /**
     * Esta é a query customizada que o seu service está tentando chamar.
     * Ela garante que as informações do Gênero sejam carregadas junto com o Filme
     * em uma única consulta ao banco, evitando problemas de performance.
     */
    @Query("SELECT f FROM Filme f JOIN FETCH f.genero ORDER BY f.id")
    List<Filme> findAllComGenero();
}
