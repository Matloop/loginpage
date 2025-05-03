package com.example.loginauthapi.controller;

import com.example.loginauthapi.domain.user.Endereco;
import com.example.loginauthapi.domain.user.Proprietario;
import com.example.loginauthapi.dto.EnderecoDTO;
import com.example.loginauthapi.repositories.EnderecoRepository;
import com.example.loginauthapi.repositories.ProprietarioRepository;
import com.example.loginauthapi.service.EnderecoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("address")
public class EnderecoController {
    @Autowired
    private EnderecoService enderecoService;
    @Autowired
    private ProprietarioRepository proprietarioRepository;

    @GetMapping()
    private ResponseEntity<List<Endereco>> enderecos() {
        List<Endereco> enderecos = enderecoService.getAll();
        return ResponseEntity.ok(enderecos);
    }

    @GetMapping("{id}")
    private ResponseEntity<Endereco> endereco(@PathVariable Long id) {
        Optional<Endereco> endereco = enderecoService.getById(id);

        if (endereco.isPresent()) {
            return ResponseEntity.ok(endereco.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    private ResponseEntity<Endereco> addEndereco(@RequestBody EnderecoDTO enderecoDTO) throws Exception {
        if (enderecoDTO.getProprietarioId() == null) {
            // Ou outra validação necessária
            return ResponseEntity.badRequest().build(); // Retorna 400 Bad Request
        }
        //Se não tiver o id do proprietario na db
        Proprietario proprietario = proprietarioRepository.findById(enderecoDTO.getProprietarioId())
                .orElseThrow(() -> new EntityNotFoundException("Proprietário não encontrado com ID: " + enderecoDTO.getProprietarioId()));

        Endereco endereco1 = new Endereco();
        endereco1.setProprietario(proprietario);
        endereco1.setCidade(enderecoDTO.getCidade());
        endereco1.setBairro(enderecoDTO.getBairro());
        endereco1.setUf(enderecoDTO.getUf());
        endereco1.setCep(enderecoDTO.getCep());
        endereco1.setLogradouro(enderecoDTO.getLogradouro());
        endereco1.setNumero(enderecoDTO.getNumero());
        endereco1.setComplemento(enderecoDTO.getComplemento());

        enderecoService.save(endereco1);
        return new ResponseEntity<>(endereco1,HttpStatus.CREATED);
    }

    @PutMapping
    private ResponseEntity<Endereco> updateEndereco(@RequestBody Endereco endereco) {

        if (enderecoService.getById(endereco.getId()).isPresent()) {
            enderecoService.update(endereco);
            return ResponseEntity.ok(endereco);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    private ResponseEntity<Endereco> deleteEndereco(@RequestBody Endereco endereco) {
        if (enderecoService.getById(endereco.getId()).isPresent()) {
            enderecoService.delete(endereco.getId());
            return ResponseEntity.ok(endereco);
        }
        return ResponseEntity.ok(endereco);
    }
}
