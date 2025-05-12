package com.example.loginauthapi.service.Impl;

import com.example.loginauthapi.domain.user.Proprietario;
import com.example.loginauthapi.dto.ProprietarioDTO;
import com.example.loginauthapi.repositories.ProprietarioRepository;
import com.example.loginauthapi.service.ProprietarioService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProprietarioServiceImpl implements ProprietarioService {
    @Autowired
    private ProprietarioRepository proprietarioRepository;
    @Override
    public void save(ProprietarioDTO proprietario) {
        Proprietario proprietarioBuild = new Proprietario();
        proprietarioBuild.setNome(proprietario.nome());
        proprietarioBuild.setEmail(proprietario.email());
        proprietarioBuild.setTelefone(proprietario.telefone());
        proprietarioRepository.save(proprietarioBuild);

    }

    @Override
    public ProprietarioDTO update(Long id, ProprietarioDTO proprietario) {
        Proprietario existingProprietario = this.proprietarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found with id" + id));
        existingProprietario.setNome(proprietario.nome());
        existingProprietario.setEmail(proprietario.email());
        existingProprietario.setTelefone(proprietario.telefone());
        Proprietario updatedProprietario = this.proprietarioRepository.save(existingProprietario);
        return ProprietarioDTO.fromProprietario(updatedProprietario);

    }

    @Override
    public void delete(Long id) {
        if(!this.proprietarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Not found with id" + id );
        }
        this.proprietarioRepository.deleteById(id);
    }
    @Transactional
    @Override
    public Optional<ProprietarioDTO> findById(Long id) {
        Optional<Proprietario> proprietario = this.proprietarioRepository.findById(id);
        return proprietario.map(ProprietarioDTO::fromProprietario);

    }

    @Override
    public List<ProprietarioDTO> findAll() {
        List<Proprietario> proprietarios = this.proprietarioRepository.findAll();
        return proprietarios.stream().map(ProprietarioDTO::fromProprietario).collect(Collectors.toList());
    }
}
