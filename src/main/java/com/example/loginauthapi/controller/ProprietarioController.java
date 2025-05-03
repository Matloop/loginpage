package com.example.loginauthapi.controller;

import com.example.loginauthapi.domain.user.Endereco;
import com.example.loginauthapi.domain.user.Proprietario;
import com.example.loginauthapi.repositories.EnderecoRepository;
import com.example.loginauthapi.repositories.ProprietarioRepository;
import com.example.loginauthapi.service.EnderecoService;
import com.example.loginauthapi.service.ProprietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/owner")
public class ProprietarioController {
    @Autowired
    private ProprietarioService proprietarioService;

    @GetMapping
    public ResponseEntity<List<Proprietario>> findAll() {
        List<Proprietario> proprietarios = proprietarioService.findAll();
        return new ResponseEntity<>(proprietarios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proprietario> findById(@PathVariable Long id) {
        Optional<Proprietario> proprietario = proprietarioService.findById(id);
        if (proprietario.isPresent()) {
            return new ResponseEntity<>(proprietario.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Proprietario> create(@RequestBody Proprietario proprietario) {
        Proprietario newProprietario = new Proprietario(proprietario.getId(),proprietario.getNome(),
                proprietario.getEmail(),proprietario.getTelefone(),
                proprietario.getEnderecos());
        proprietarioService.save(newProprietario);
        return new ResponseEntity<>(newProprietario, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Proprietario> update(@RequestBody Proprietario proprietario) {
        proprietarioService.update(proprietario);
        if (proprietarioService.findById(proprietario.getId()).isPresent()) {
            return new ResponseEntity<>(proprietario, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping
    public ResponseEntity<Proprietario> delete(@RequestBody Proprietario proprietario) {
        proprietarioService.delete(proprietario);
        return new ResponseEntity<>(proprietario, HttpStatus.OK);
    }

}
