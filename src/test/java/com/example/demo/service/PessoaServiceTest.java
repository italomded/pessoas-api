package com.example.demo.service;

import com.example.demo.domain.Endereco;
import com.example.demo.domain.Pessoa;
import com.example.demo.dto.form.EnderecoForm;
import com.example.demo.dto.form.PessoaForm;
import com.example.demo.repository.EnderecoRepository;
import com.example.demo.repository.PessoaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PessoaServiceTest {
    @Mock
    private PessoaRepository pessoaRepository;
    @InjectMocks
    private PessoaService pessoaService;
    @Captor
    private ArgumentCaptor<Pessoa> captor;

    private AutoCloseable closeable;

    @BeforeEach
    void criarMocks() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void naoDeveriaCriarDuasPessoasComMesmoNome() {
        Pessoa pessoaA = new Pessoa("Alguém", LocalDate.now());
        PessoaForm form = new PessoaForm("Alguém", LocalDate.now());

        Mockito.when(pessoaRepository.findByNome("Alguém"))
                .thenReturn(Optional.of(pessoaA));

        Assertions.assertThrows(CampoUnicoDuplicadoException.class, () -> pessoaService.criarPessoa(form));
    }

    @AfterEach
    void fecharMocks() throws Exception {
        closeable.close();
    }
}
