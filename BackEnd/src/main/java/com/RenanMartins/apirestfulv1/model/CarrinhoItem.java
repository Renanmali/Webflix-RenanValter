package com.RenanMartins.apirestfulv1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class CarrinhoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrinho_id")
    private Carrinho carrinho;

    @ManyToOne
    @JoinColumn(name = "copia_id", nullable = false)
    private Copia copia;
}
