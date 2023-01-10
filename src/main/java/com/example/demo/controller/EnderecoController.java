package com.example.demo.controller;

import com.example.demo.domain.Endereco;
import com.example.demo.dto.EnderecoDTO;
import com.example.demo.dto.ResourceChangeDTO;
import com.example.demo.dto.form.EnderecoForm;
import com.example.demo.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/endereco")
public class EnderecoController {
    @Autowired
    private EnderecoService enderecoService;

    @GetMapping("{id}")
    public ResponseEntity<Page<EnderecoDTO>> recuperar(@PathVariable String id, Pageable pageable) {
        try {
            Page<Endereco> enderecosDaPessoa = enderecoService.getEnderecosDaPessoa(Long.parseLong(id), pageable);
            Page<EnderecoDTO> enderecosDaPessoaDTO = enderecosDaPessoa.map(ep -> new EnderecoDTO(
                    ep.getId(), ep.getLogradouro(), ep.getCep(), ep.getNumero(), ep.getCidade(), ep.isPrincipal()
            ));
            return ResponseEntity.ok(enderecosDaPessoaDTO);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<ResourceChangeDTO> editar(@PathVariable String id) {
        try {
            Long result = enderecoService.definirEnderecoPrincipal(Long.parseLong(id));
            if (result == null) return ResponseEntity.badRequest().build();
            return ResponseEntity.ok(new ResourceChangeDTO(result));
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @PostMapping("{id}")
    public ResponseEntity<ResourceChangeDTO> criar(@PathVariable String id, @RequestBody @Valid EnderecoForm form) {
        try {
            Long result = enderecoService.criarEndereco(Long.parseLong(id), form);
            if (result == null) return ResponseEntity.badRequest().build();
            return ResponseEntity.ok(new ResourceChangeDTO(result));
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }
}
