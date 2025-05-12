package com.example.loginauthapi.dto;

import com.example.loginauthapi.domain.user.Proprietario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public record ProprietarioDTO(
        @NotBlank String nome,
        @NotBlank String email,
        @NotBlank String telefone,
        // outros campos do proprietario
        @Valid @NotNull // Valida o endereço
        List<EnderecoDTO> enderecos // Recebe uma lista de endereços do frontend
) {

        public static ProprietarioDTO fromProprietario(Proprietario proprietario) {
                if (proprietario == null) {}
                List<EnderecoDTO> enderecoDTOs = null;
                if (proprietario.getEnderecos() != null) {
                        enderecoDTOs = proprietario.getEnderecos().stream()
                                .map(enderecoEntity -> new EnderecoDTO( // Assumindo que EnderecoDTO é um record
                                        enderecoEntity.getLogradouro(),
                                        enderecoEntity.getNumero(),
                                        // ... outros campos do EnderecoDTO
                                        enderecoEntity.getCep(),
                                        enderecoEntity.getBairro(),
                                        enderecoEntity.getCidade(),
                                        enderecoEntity.getUf(),
                                        enderecoEntity.getCep(),
                                        enderecoEntity.getProprietario().getId()
                                ))
                                // OU se você tiver um método estático EnderecoDTO.fromEntity(endereco):
                                // .map(EnderecoDTO::fromEntity)
                                .collect(Collectors.toList());
                } else {
                        // Se a lista de endereços da entidade for nula, o DTO terá uma lista vazia
                        enderecoDTOs = Collections.emptyList();
                }
                return new ProprietarioDTO(
                        proprietario.getNome(),
                        proprietario.getEmail(),
                        proprietario.getTelefone(),
                        enderecoDTOs

                );
        }
}