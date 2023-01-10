package com.example.demo.controller.handler;

import com.example.demo.dto.ValidationErrorDTO;
import com.example.demo.service.CampoUnicoDuplicadoException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ValidacaoHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationErrorDTO>> errosDeFormulario(MethodArgumentNotValidException exception) {
        List<ValidationErrorDTO> listaDeErros = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(erro -> {
                    String campo = erro.getField();
                    String mensagem = erro.getDefaultMessage();
                    listaDeErros.add(new ValidationErrorDTO(campo, mensagem));
                }
        );
        return ResponseEntity.badRequest().body(listaDeErros);
    }

    @ExceptionHandler(value = CampoUnicoDuplicadoException.class)
    public ResponseEntity<ValidationErrorDTO> errosDeCampoUnico(CampoUnicoDuplicadoException exception) {
        return ResponseEntity.badRequest().body(exception.getErroDeValidacao());
    }
}
