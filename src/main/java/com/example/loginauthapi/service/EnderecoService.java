package com.example.loginauthapi.service;

import com.example.loginauthapi.domain.user.Endereco;
import com.example.loginauthapi.dto.EnderecoDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface EnderecoService {
    void save(EnderecoDTO endereco);
    EnderecoDTO update(Long id,EnderecoDTO endereco);
    void delete(Long id);
    Optional<EnderecoDTO> getById(Long id);
    List<EnderecoDTO> getAll();
}
