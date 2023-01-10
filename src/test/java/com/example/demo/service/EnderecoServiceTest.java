package com.example.demo.service;

import com.example.demo.domain.Endereco;
import com.example.demo.domain.Pessoa;
import com.example.demo.dto.form.EnderecoForm;
import com.example.demo.repository.EnderecoRepository;
import com.example.demo.repository.PessoaRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Optional;

public class EnderecoServiceTest {
    @Mock
    private EnderecoRepository enderecoRepository;
    @Mock
    private PessoaRepository pessoaRepository;
    @InjectMocks
    private EnderecoService enderecoService;
    @Captor
    private ArgumentCaptor<Endereco> captor;

    private AutoCloseable closeable;

    @BeforeEach
    void criarMocks() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveriaAlterarEnderecoPrincipalDaPessoa() {
        Pessoa pessoa = new Pessoa("Alguém", LocalDate.now());

        Endereco enderecoA = new Endereco("Rua X", "12345678", 10, "Cidade", pessoa);
        Endereco enderecoB = new Endereco("Rua Z", "87654321", 12, "Outra cidade", pessoa);
        enderecoB.setPrincipal(true);

        Mockito.when(enderecoRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(enderecoA));
        Mockito.when(enderecoRepository.findByPessoa_IdAndPrincipal(Mockito.anyLong(), Mockito.anyBoolean()))
                .thenReturn(Optional.of(enderecoB));

        enderecoService.definirEnderecoPrincipal(0);
        Assertions.assertEquals(true, enderecoA.isPrincipal());
        Assertions.assertEquals(false, enderecoB.isPrincipal());
    }

    @Test
    void unicoEnderecoDeveriaSerOPrincipal() throws NoSuchFieldException, IllegalAccessException {
        Pessoa pessoa = new Pessoa("Alguém", LocalDate.now());
        EnderecoForm form = new EnderecoForm("Rua X","87654321", 12, "Outra cidade");

        Endereco enderecoComId = new Endereco();
        Field idField = Endereco.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(enderecoComId, 1l);

        Mockito.when(pessoaRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(pessoa));
        Mockito.when(enderecoRepository.save(Mockito.any()))
                .thenReturn(enderecoComId);

        enderecoService.criarEndereco(Mockito.anyLong(), form);

        Mockito.verify(enderecoRepository).save(captor.capture());
        Endereco enderecoCriado = captor.getValue();
        Assertions.assertEquals(pessoa, enderecoCriado.getPessoa());
        Assertions.assertEquals(true, enderecoCriado.isPrincipal());
    }

    @AfterEach
    void fecharMocks() throws Exception {
        closeable.close();
    }
}
