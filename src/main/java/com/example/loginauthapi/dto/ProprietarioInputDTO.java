package com.example.loginauthapi.dto;

import com.example.loginauthapi.dto.EnderecoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProprietarioInputDTO(
        @NotBlank String nome,
        @NotBlank String email,
        @NotBlank String telefone,
        // outros campos do proprietario
        @Valid @NotNull // Valida o endereço
        List<EnderecoDTO> enderecos // Recebe uma lista de endereços do frontend
) {}