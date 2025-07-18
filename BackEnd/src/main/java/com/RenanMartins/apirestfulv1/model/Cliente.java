package com.RenanMartins.apirestfulv1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Cliente { // Renomeado de Usuario

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(unique = true)
    private String conta;

    @NotEmpty
    private String senha;

    // Novo: Um cliente pode ter várias locações
    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Locacao> locacoes;

    public Cliente(String conta, String senha) {
        this.conta = conta;
        this.senha = senha;
    }
}

