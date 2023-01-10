package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@EqualsAndHashCode
public class Endereco {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Getter
    private long id;

    @Column(nullable = false) @Getter @Setter
    private String logradouro;
    @Column(nullable = false, length = 8) @Getter @Setter
    private String cep;
    @Column(nullable = false) @Getter @Setter
    private int numero;
    @Column(nullable = false) @Getter @Setter
    private String cidade;
    @Column(nullable = false) @Getter @Setter
    private boolean principal;

    @ManyToOne(optional = false) @Getter
    private Pessoa pessoa;

    public Endereco(String logradouro, String cep, int numero, String cidade, Pessoa pessoa) {
        this.logradouro = logradouro;
        this.cep = cep;
        this.numero = numero;
        this.cidade = cidade;
        this.pessoa = pessoa;
        principal = false;
    }
}
