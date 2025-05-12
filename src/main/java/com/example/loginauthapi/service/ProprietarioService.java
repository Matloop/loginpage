package com.example.loginauthapi.service;

import com.example.loginauthapi.domain.user.Proprietario;
import com.example.loginauthapi.dto.ProprietarioDTO;

import java.util.List;
import java.util.Optional;

public interface ProprietarioService {
    void save(ProprietarioDTO proprietario);
    ProprietarioDTO update(Long id, ProprietarioDTO proprietario);
    void delete(Long id);
    Optional<ProprietarioDTO> findById(Long id);
    List<ProprietarioDTO> findAll();
}
