package com.example.demo.repository;

import com.example.demo.domain.Endereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    Page<Endereco> findByPessoa_Id(long id, Pageable pageable);
    Optional<Endereco> findByPessoa_IdAndPrincipal(long id, boolean ehPrincipal);
}
