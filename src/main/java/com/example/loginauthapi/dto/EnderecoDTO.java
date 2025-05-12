package com.example.loginauthapi.dto;

import com.example.loginauthapi.domain.user.Endereco;
import lombok.Getter;
import lombok.Setter;


public record EnderecoDTO(
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String uf,
        String cep,
        Long proprietarioId
) {
    public static EnderecoDTO fromEndereco(Endereco endereco) {
        if(endereco == null) return null;

        return new EnderecoDTO(
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getUf(),
                endereco.getCep(),
                endereco.getProprietario().getId()
        );
    }

}

