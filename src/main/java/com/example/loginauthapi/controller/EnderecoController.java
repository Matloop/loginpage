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

    @GetMapping()
    private ResponseEntity<List<EnderecoDTO>> enderecos() {
        List<EnderecoDTO> enderecos = enderecoService.getAll();
        return ResponseEntity.ok(enderecos);
    }

    @GetMapping("{id}")
    private ResponseEntity<EnderecoDTO> endereco(@PathVariable Long id) {
        Optional<EnderecoDTO> endereco = enderecoService.getById(id);

        if (endereco.isPresent()) {
            return ResponseEntity.ok(endereco.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    private ResponseEntity<EnderecoDTO> addEndereco(@RequestBody EnderecoDTO enderecoDTO) {
        enderecoService.save(enderecoDTO);
        return new ResponseEntity<>(enderecoDTO, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<EnderecoDTO> updateEndereco(@RequestBody EnderecoDTO enderecoDTO, @PathVariable Long id) {
        enderecoService.update(id, enderecoDTO);

        return ResponseEntity.ok(enderecoDTO);

    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteEndereco(@PathVariable Long id) {
        enderecoService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
