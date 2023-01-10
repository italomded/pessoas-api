package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@EqualsAndHashCode(of = {"nome", "ID"})
@Entity
public class Pessoa {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Getter
    private long id;

    @Column(unique = true, nullable = false) @Getter @Setter
    private String nome;
    @Column(nullable = false) @Getter @Setter
    private LocalDate dataDeNascimento;
    @OneToMany(mappedBy = "pessoa") @Getter
    private List<Endereco> enderecos;

    public Pessoa(String nome, LocalDate dataDeNascimento) {
        this.nome = nome;
        this.dataDeNascimento = dataDeNascimento;
        enderecos = new ArrayList<>();
    }
}
