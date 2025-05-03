package com.example.loginauthapi.service;

import com.example.loginauthapi.domain.user.Endereco;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface EnderecoService {
    void save(Endereco endereco);
    void update(Endereco endereco);
    void delete(Long id);
    Optional<Endereco> getById(Long id);
    List<Endereco> getAll();
}
