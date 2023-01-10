package com.example.demo.controller;

import com.example.demo.domain.Pessoa;
import com.example.demo.dto.PessoaDTO;
import com.example.demo.dto.ResourceChangeDTO;
import com.example.demo.dto.ValidationErrorDTO;
import com.example.demo.dto.form.PessoaForm;
import com.example.demo.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/pessoa")
public class PessoaController {
    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public ResponseEntity<Page<PessoaDTO>> listar(Pageable pageable) {
        Page<Pessoa> todaAsPessoas = pessoaService.getTodaAsPessoas(pageable);
        Page<PessoaDTO> todasAsPessoasDTO = todaAsPessoas.map(p -> new PessoaDTO(p.getId(), p.getNome(), p.getDataDeNascimento()));
        return ResponseEntity.ok(todasAsPessoasDTO);
    }

    @GetMapping("{id}")
    public ResponseEntity<PessoaDTO> recuperar(@PathVariable String id) {
        try {
            Optional<Pessoa> pessoaOpcional = pessoaService.getPessoa(Long.parseLong(id));
            if (pessoaOpcional.isEmpty()) return ResponseEntity.notFound().build();

            Pessoa pessoa = pessoaOpcional.get();
            PessoaDTO pessoaDTO = new PessoaDTO(pessoa.getId(), pessoa.getNome(), pessoa.getDataDeNascimento());
            return ResponseEntity.ok(pessoaDTO);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<ResourceChangeDTO> editar(@PathVariable String id, @RequestBody @Valid PessoaForm form) {
        try {
            Long result = pessoaService.editarPessoa(Long.parseLong(id), form);
            if (result == null) return ResponseEntity.badRequest().build();
            return ResponseEntity.ok(new ResourceChangeDTO(result));
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody @Valid PessoaForm form) {
        Long result = pessoaService.criarPessoa(form);
        //if (result == null) return ResponseEntity.badRequest().body(new ValidationErrorDTO("nome", "este nome já está me uso"));
        return ResponseEntity.ok(new ResourceChangeDTO(result));
    }
}
