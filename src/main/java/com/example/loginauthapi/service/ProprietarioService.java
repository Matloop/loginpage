package com.example.loginauthapi.service;

import com.example.loginauthapi.domain.user.Proprietario;

import java.util.List;
import java.util.Optional;

public interface ProprietarioService {
    void save(Proprietario proprietario);
    void update(Proprietario proprietario);
    void delete(Proprietario proprietario);
    Optional<Proprietario> findById(Long id);
    List<Proprietario> findAll();
}
