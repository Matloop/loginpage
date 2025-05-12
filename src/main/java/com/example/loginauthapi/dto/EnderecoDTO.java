package com.example.loginauthapi.dto;

import lombok.Getter;
import lombok.Setter;


public record EnderecoDTO(
        Long id,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String uf,
        String cep,
        Long proprietarioId
) {


}

