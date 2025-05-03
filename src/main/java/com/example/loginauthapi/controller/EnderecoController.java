package com.example.loginauthapi.controller;

import com.example.loginauthapi.domain.user.Endereco;
import com.example.loginauthapi.dto.EnderecoDTO;
import com.example.loginauthapi.repositories.EnderecoRepository;
import com.example.loginauthapi.service.EnderecoService;
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
    private ResponseEntity<Endereco> addEndereco(@RequestBody Endereco endereco, EnderecoDTO enderecoDTO) {
        Endereco endereco1 = new Endereco(endereco.getId(),
                endereco.getCep(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getUf(),
                endereco.getProprietario());
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
