package com.example.loginauthapi.service.Impl;

import com.example.loginauthapi.domain.user.Endereco;
import com.example.loginauthapi.domain.user.Proprietario;
import com.example.loginauthapi.dto.EnderecoDTO;
import com.example.loginauthapi.repositories.EnderecoRepository;
import com.example.loginauthapi.repositories.ProprietarioRepository;
import com.example.loginauthapi.service.EnderecoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnderecoServiceImpl implements EnderecoService {
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ProprietarioRepository proprietarioRepository;

    @Transactional
    @Override
    public void save(EnderecoDTO enderecoDTO) {

        if (enderecoDTO.proprietarioId() == null || enderecoDTO.cep() == null) {
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
    @Transactional
    @Override
    public EnderecoDTO update(Long id,EnderecoDTO endereco) {
        Endereco existingEndereco = enderecoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found with ID: " + id));

        existingEndereco.setProprietario(proprietarioRepository.findById(id).get());
        existingEndereco.setCidade(endereco.cidade());
        existingEndereco.setBairro(endereco.bairro());
        existingEndereco.setUf(endereco.uf());
        existingEndereco.setCep(endereco.cep());
        existingEndereco.setLogradouro(endereco.logradouro());
        existingEndereco.setNumero(endereco.numero());
        existingEndereco.setComplemento(endereco.complemento());
        Endereco updatedEntity = this.enderecoRepository.save(existingEndereco);

        return EnderecoDTO.fromEndereco(updatedEntity);

    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (!enderecoRepository.existsById(id)) { // Ou busca e usa orElseThrow como antes
            throw new EntityNotFoundException("Endereço não encontrado com id: " + id);
        }

        enderecoRepository.deleteById(id);
    }
    @Transactional
    @Override
    public Optional<EnderecoDTO> getById(Long id) {
        Optional<Endereco> endereco = this.enderecoRepository.findById(id);

        return endereco.map(EnderecoDTO::fromEndereco);
    }

    @Transactional
    @Override
    public List<EnderecoDTO> getAll() {
        List<Endereco> enderecos = enderecoRepository.findAll();
        return enderecos.stream().map(EnderecoDTO::fromEndereco).toList();

    }
}
