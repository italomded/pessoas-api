package com.example.demo.service;

import com.example.demo.domain.Endereco;
import com.example.demo.domain.Pessoa;
import com.example.demo.repository.EnderecoRepository;
import com.example.demo.repository.PessoaRepository;
import com.example.demo.dto.form.EnderecoForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnderecoService {
    private EnderecoRepository enderecoRepository;
    private PessoaRepository pessoaRepository;

    @Autowired
    public EnderecoService(EnderecoRepository enderecoRepository, PessoaRepository pessoaRepository) {
        this.enderecoRepository = enderecoRepository;
        this.pessoaRepository = pessoaRepository;
    }

    public Page<Endereco> getEnderecosDaPessoa(long pessoaId, Pageable pageable) {
        return enderecoRepository.findByPessoa_Id(pessoaId, pageable);
    }

    public Long definirEnderecoPrincipal(long enderecoId) {
        Optional<Endereco> enderecoOpcional = enderecoRepository.findById(enderecoId);
        if (enderecoOpcional.isEmpty()) return null;

        Endereco endereco = enderecoOpcional.get();
        long pessoaId = endereco.getPessoa().getId();

        Optional<Endereco> enderecoPrincipalOpcional = enderecoRepository.findByPessoa_IdAndPrincipal(pessoaId, true);
        if (enderecoOpcional.isPresent()) {
            enderecoPrincipalOpcional.get().setPrincipal(false);
        }

        endereco.setPrincipal(true);
        enderecoRepository.save(endereco);
        return endereco.getId();
    }

    public Long criarEndereco(long pessoaId, EnderecoForm form) {
        Optional<Pessoa> pessoaOpcional = pessoaRepository.findById(pessoaId);
        if (pessoaOpcional.isEmpty()) return null;

        Pessoa pessoa = pessoaOpcional.get();
        Endereco novoEndereco = new Endereco(form.logradouro, form.cep, form.numero, form.cidade, pessoa);

        if (pessoa.getEnderecos().isEmpty()) {
            novoEndereco.setPrincipal(true);
        }

        novoEndereco = enderecoRepository.save(novoEndereco);
        return novoEndereco.getId();
    }

}
