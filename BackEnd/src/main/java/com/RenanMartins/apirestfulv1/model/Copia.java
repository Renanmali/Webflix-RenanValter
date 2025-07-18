package com.RenanMartins.apirestfulv1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Copia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filme_id", nullable = false)
    private Filme filme;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCopia status;
}
