package br.com.sura.api.dtos;

import br.com.sura.api.model.Cliente;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ClienteDTO {

    private Long id;

    @NotEmpty(message = "Nome não pode ser vazio.")
    @Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres.")
    private String nome;

    @NotEmpty(message = "Email não pode ser vazio.")
    @Length(min = 5, max = 200, message = "Email deve conter entre 5 e 200 caracteres.")
    @Email(message="Email inválido.")
    private String email;

    @NotEmpty(message = "CPF não pode ser vazio.")
    @CPF(message="CPF inválido")
    private String cpf;

    @NotEmpty(message = "A Senha não pode ser vazio")
    private String senha;

    @NotEmpty(message = "Rua não pode ser vazio")
    private String rua;

    @NotEmpty(message = "Cidade não pode ser vazio")
    private String cidade;

    @NotEmpty(message = "Bairro não pode ser vazio")
    private String bairro;

    @NotEmpty(message = "CEP não pode ser vazio")
    @Size(min = 8, max = 9, message = "O CEP é inválido.")
    private String cep;

    @NotEmpty(message = "Estado não pode ser vazio")
    private String estado;

    public ClienteDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.email = cliente.getEmail();
        this.rua = cliente.getRua();
        this.cidade = cliente.getCidade();
        this.bairro = cliente.getBairro();
        this.cep = cliente.getCep();
        this.estado = cliente.getEstado();
    }

    public ClienteDTO() {
    }

    public static List<ClienteDTO> converter(Iterable<Cliente> listaCliente) {
        List<Cliente> listaPopulada = new ArrayList<>();
        listaCliente.forEach(listaPopulada::add);

        return listaPopulada.stream().map(ClienteDTO::new).collect(Collectors.toList());
    }
}
