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
        enderecoService.save(enderecoDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<EnderecoDTO> updateEndereco(@RequestBody EnderecoDTO enderecoDTO) {

        if (enderecoService.getById(enderecoDTO.id()).isPresent()) {
            enderecoService.update(enderecoDTO);
            return ResponseEntity.ok(enderecoDTO);
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
