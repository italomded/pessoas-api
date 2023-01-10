package com.example.demo.dto.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class EnderecoForm {
    @NotBlank
    public String logradouro;
    @NotBlank @Size(min = 8, max = 8)
    public String cep;
    @Min(value = 1)
    public int numero;
    @NotBlank
    public String cidade;
}
