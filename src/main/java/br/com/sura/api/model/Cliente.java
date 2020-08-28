package br.com.sura.api.model;

import br.com.sura.api.enums.PerfilEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

    private static final long serialVersionUID = -5754246207015712518L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "O nome não pode ser vazio")
    private String nome;

    @Email(message = "e-mail invalido")
    @NotEmpty(message = "O e-mail não pode ser vazio")
    @Column(unique = true)
    private String email;

    @CPF(message = "CPF invalido")
    @NotEmpty(message = "O CPF não pode ser vazio")
    @Column(unique = true)
    private String cpf;

    @Column
    @NotEmpty(message = "A Senha não pode ser vazio")
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private PerfilEnum perfil;

    @NotEmpty(message = "Rua não pode ser vazio")
    private String rua;

    @NotEmpty(message = "Cidade não pode ser vazio")
    private String cidade;

    @NotEmpty(message = "Bairro não pode ser vazio")
    private String bairro;

    @NotEmpty(message = "CEP não pode ser vazio")
    private String cep;

    @NotEmpty(message = "Estado não pode ser vazio")
    private String estado;

    @Column(name = "data_criacao", nullable = false)
    private LocalDate dataCriacao;

    @Column(name = "data_atualizacao", nullable = false)
    private LocalDate dataAtualizacao;

    @Column
    private boolean admin;

    @PreUpdate
    public void preUpdate() {
        dataAtualizacao = LocalDate.now();
    }

    @PrePersist
    public void prePersist() {
        final LocalDate atual = LocalDate.now();
        dataCriacao = atual;
        dataAtualizacao = atual;
    }
}
