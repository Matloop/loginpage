package com.example.loginauthapi.service.Impl;

import com.example.loginauthapi.domain.user.Endereco;
import com.example.loginauthapi.domain.user.Proprietario;
import com.example.loginauthapi.dto.EnderecoDTO;
import com.example.loginauthapi.repositories.EnderecoRepository;
import com.example.loginauthapi.repositories.ProprietarioRepository;
import com.example.loginauthapi.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoServiceImpl implements EnderecoService {
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ProprietarioRepository proprietarioRepository;
    @Override
    public void save(EnderecoDTO enderecoDTO) {

        if (enderecoDTO.proprietarioId() == null) {
            throw new IllegalArgumentException("ID do Proprietário não pode ser nulo para criar um endereço.");
        }

        Proprietario proprietario = proprietarioRepository.findById(enderecoDTO.proprietarioId())
                .orElseThrow(() -> new ResourceAccessException("Proprietário não encontrado com ID: " + enderecoDTO.proprietarioId()));

        Endereco novoEndereco = new Endereco();
        novoEndereco.setProprietario(proprietario); // Define a entidade encontrada
        novoEndereco.setCidade(enderecoDTO.cidade());
        novoEndereco.setBairro(enderecoDTO.bairro());
        // Assumindo que sua entidade tem 'estado' e o DTO também (ajuste se for 'uf')
        novoEndereco.setUf(enderecoDTO.uf());
        novoEndereco.setCep(enderecoDTO.cep());
        novoEndereco.setLogradouro(enderecoDTO.logradouro());
        novoEndereco.setNumero(enderecoDTO.numero());
        novoEndereco.setComplemento(enderecoDTO.complemento());
        this.enderecoRepository.save(novoEndereco);
    }

    @Override
    public void update(EnderecoDTO endereco) {

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
