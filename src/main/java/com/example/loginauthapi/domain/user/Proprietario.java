package com.example.loginauthapi.domain.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "proprietarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Proprietario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotBlank
    @Email
    @Column(nullable = false)
    private String email;

    @NotBlank
    @Size(min = 10,max = 11)
    @Column(nullable = false)
    private String telefone;

    // Adicione outros campos do proprietário (CPF/CNPJ, telefone, email, etc.)
    // @Column(nullable = false, unique = true)
    // private String cpfOuCnpj;

    // --- Relacionamento com Endereco ---
    @OneToMany(
            mappedBy = "proprietario", // Indica o campo na classe Endereco que gerencia a FK
            cascade = CascadeType.ALL, // Salva/Atualiza/Remove Enderecos junto com Proprietario (CUIDADO com REMOVE)
            orphanRemoval = true,     // Remove endereços do banco se forem removidos da lista
            fetch = FetchType.LAZY      // Carrega a lista de endereços apenas quando necessário
    )
    private List<Endereco> enderecos = new ArrayList<>(); // Inicialize a lista
}
