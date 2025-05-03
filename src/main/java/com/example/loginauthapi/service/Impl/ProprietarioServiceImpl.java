package com.example.loginauthapi.service.Impl;

import com.example.loginauthapi.domain.user.Proprietario;
import com.example.loginauthapi.repositories.ProprietarioRepository;
import com.example.loginauthapi.service.ProprietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProprietarioServiceImpl implements ProprietarioService {
    @Autowired
    private ProprietarioRepository proprietarioRepository;
    @Override
    public void save(Proprietario proprietario) {
        this.proprietarioRepository.save(proprietario);
    }

    @Override
    public void update(Proprietario proprietario) {
        this.proprietarioRepository.save(proprietario);
    }

    @Override
    public void delete(Proprietario proprietario) {
        this.proprietarioRepository.delete(proprietario);
    }

    @Override
    public Optional<Proprietario> findById(Long id) {
        return this.proprietarioRepository.findById(id);
    }

    @Override
    public List<Proprietario> findAll() {
        return this.proprietarioRepository.findAll();
    }
}
