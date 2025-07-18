package com.RenanMartins.apirestfulv1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Genero { // Renomeado de Categoria

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome; // Ex: Ação, Comédia
    private String slug; // Ex: acao, comedia

    @JsonIgnore
    @OneToMany(mappedBy = "genero") // Mapeamento ajustado para o campo 'genero' em Filme
    private List<Filme> filmes; // Relação ajustada para Filme

    public Genero(String nome, String slug) {
        this.nome = nome;
        this.slug = slug;
    }
}
