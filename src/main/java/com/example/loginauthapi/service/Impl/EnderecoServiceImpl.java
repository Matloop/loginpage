package com.example.loginauthapi.service.Impl;

import com.example.loginauthapi.domain.user.Endereco;
import com.example.loginauthapi.repositories.EnderecoRepository;
import com.example.loginauthapi.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoServiceImpl implements EnderecoService {
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Override
    public void save(Endereco endereco) {
        this.enderecoRepository.save(endereco);
    }

    @Override
    public void update(Endereco endereco) {
        this.enderecoRepository.save(endereco);
    }

    @Override
    public void delete(Long id) {
        this.enderecoRepository.deleteById(id);
    }

    @Override
    public Optional<Endereco> getById(Long id) {
        return this.enderecoRepository.findById(id);
    }


    @Override
    public List<Endereco> getAll() {
        return this.enderecoRepository.findAll();
    }
}
