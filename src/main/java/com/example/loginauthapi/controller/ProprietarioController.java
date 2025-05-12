package com.example.loginauthapi.controller;

import com.example.loginauthapi.domain.user.Endereco;
import com.example.loginauthapi.domain.user.Proprietario;
import com.example.loginauthapi.dto.ProprietarioDTO;
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
    public ResponseEntity<List<ProprietarioDTO>> findAll() {
        List<ProprietarioDTO> proprietarios = proprietarioService.findAll();
        return new ResponseEntity<>(proprietarios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProprietarioDTO> findById(@PathVariable Long id) {
        Optional<ProprietarioDTO> proprietario = proprietarioService.findById(id);
        if (proprietario.isPresent()) {
            return new ResponseEntity<>(proprietario.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<ProprietarioDTO> create(@RequestBody ProprietarioDTO proprietario) {
        proprietarioService.save(proprietario);
        return new ResponseEntity<>(proprietario, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProprietarioDTO> update(@PathVariable Long id ,@RequestBody ProprietarioDTO proprietario) {
        proprietarioService.update(id,proprietario);

        return new ResponseEntity<>(proprietario, HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Proprietario> delete(@PathVariable Long id) {
        proprietarioService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
