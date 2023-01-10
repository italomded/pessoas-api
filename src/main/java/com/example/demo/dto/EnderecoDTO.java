package com.example.demo.dto;

public record EnderecoDTO(long id, String logradouro, String cep, int numero, String cidade, boolean principal) {
}