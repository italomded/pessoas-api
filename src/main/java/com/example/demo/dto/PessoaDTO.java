package com.example.demo.dto;

import java.time.LocalDate;

public record PessoaDTO(long id, String nome, LocalDate dataDeNascimento) {
}
