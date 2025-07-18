package com.RenanMartins.apirestfulv1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Filme { // Renomeado de Produto

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "O 'Título' deve ser informado.")
    private String titulo; // 'nome' virou 'titulo'

    private String slug;

    @NotEmpty(message = "A 'Imagem do pôster' deve ser informada.")
    private String imagem; // Mantido

    @NotEmpty(message = "A 'Sinopse' deve ser informada.")
    @Column(length = 1000) // Aumentando o tamanho da coluna para sinopse
    private String sinopse; // 'descricao' virou 'sinopse'

    private String diretor; // Novo campo

    private int anoLancamento; // Novo campo

    @NotNull(message = "O 'Gênero' deve ser informado.")
    @ManyToOne
    private Genero genero; // 'categoria' virou 'genero'

    // Relação com as cópias físicas do filme
    @JsonIgnore
    @OneToMany(mappedBy = "filme", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Copia> copias;

    // Campos como 'disponivel', 'qtdEstoque' e 'preco' foram removidos,
    // pois essa lógica agora pertence às entidades 'Copia' e 'Locacao'.
}
