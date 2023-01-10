package com.example.demo.service;

import com.example.demo.domain.Pessoa;
import com.example.demo.dto.ValidationErrorDTO;
import com.example.demo.dto.form.PessoaForm;
import com.example.demo.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Optional;

@Service
public class PessoaService {
    private PessoaRepository pessoaRepository;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public Optional<Pessoa> getPessoa(long ID) {
        return pessoaRepository.findById(ID);
    }

    public Page<Pessoa> getTodaAsPessoas(Pageable pageable) {
        return pessoaRepository.findAll(pageable);
    }

    public Long editarPessoa(Long pessoaId, PessoaForm form) {
        Optional<Pessoa> pessoaOpcional = pessoaRepository.findById(pessoaId);
        if (pessoaOpcional.isEmpty()) return null;

        Pessoa pessoa = pessoaOpcional.get();
        pessoa.setNome(form.nome);
        pessoa.setDataDeNascimento(form.dataNascimento);
        pessoaRepository.save(pessoa);

        return pessoa.getId();
    }

    public Long criarPessoa(PessoaForm form) {
        Optional<Pessoa> pessoaOpcional = pessoaRepository.findByNome(form.nome);
        if (pessoaOpcional.isPresent()) {
            ValidationErrorDTO erroDeValidacao = new ValidationErrorDTO("nome", "este nome já está me uso");
            throw new CampoUnicoDuplicadoException(erroDeValidacao);
        }

        Pessoa novaPessoa = new Pessoa(form.nome, form.dataNascimento);
        novaPessoa = pessoaRepository.save(novaPessoa);
        return novaPessoa.getId();
    }
}
