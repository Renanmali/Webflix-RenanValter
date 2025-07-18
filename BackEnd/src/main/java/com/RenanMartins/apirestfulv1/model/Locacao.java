package com.RenanMartins.apirestfulv1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Locacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "copia_id", nullable = false, unique = true) // Uma cópia só pode estar em uma locação ativa
    private Copia copia;

    @Column(nullable = false)
    private LocalDateTime dataLocacao;

    @Column(nullable = false)
    private LocalDate dataDevolucaoPrevista;

    private LocalDateTime dataDevolucaoReal; // Preenchido apenas na devolução

    private BigDecimal valorMulta; // Preenchido se houver atraso
}
