package com.example.loginauthapi.domain.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "enderecos")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "CEP é obrigatório")
    @Size(min = 8, max = 9, message = "CEP deve ter 8 ou 9 caracteres (com ou sem hífen)")
    @Column(nullable = false, length = 9)
    private String cep;

    @NotBlank(message = "Logradouro é obrigatório")
    @Column(nullable = false)
    private String logradouro;

    @NotBlank(message = "Número é obrigatório")
    @Column(nullable = false, length = 20)
    private String numero;

    @Column(length = 100) // Complemento é opcional
    private String complemento;

    @NotBlank(message = "Bairro é obrigatório")
    @Column(nullable = false, length = 100)
    private String bairro;

    @NotBlank(message = "Cidade é obrigatória")
    @Column(nullable = false, length = 100)
    private String cidade;

    @NotBlank(message = "UF é obrigatória")
    @Size(min = 2, max = 2, message = "UF deve ter 2 caracteres")
    @Column(nullable = false, length = 2)
    private String uf;

    // --- Relacionamento com Proprietario ---
    @ManyToOne(fetch = FetchType.LAZY) // Carrega o proprietário apenas quando necessário
    @JoinColumn(name = "proprietario_id", nullable = false) // Nome da FK na tabela 'enderecos'
    @JsonBackReference
    private Proprietario proprietario;
}
