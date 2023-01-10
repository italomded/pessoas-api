package com.example.demo.dto.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
public class PessoaForm {
    @NotBlank
    public String nome;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate dataNascimento;
}
