package com.example.loginauthapi.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoDTO {

    // Não inclua o 'id' do Endereco aqui, ele será gerado pelo banco.
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private Long proprietarioId; // <-- ID do proprietário a ser enviado pelo frontend

    // Construtores, Getters e Setters
    // ... (gere-os ou use Lombok @Data)

    public EnderecoDTO() { }

    public EnderecoDTO(String cep, String logradouro, String numero, String complemento, String bairro, String cidade, String uf, Long proprietarioId) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.proprietarioId = proprietarioId;
    }
    // Getters e Setters ...
}