package com.example.demo.service;

import com.example.demo.dto.ValidationErrorDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CampoUnicoDuplicadoException extends RuntimeException {
    private ValidationErrorDTO erroDeValidacao;
}
