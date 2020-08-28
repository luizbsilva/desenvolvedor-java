package br.com.sura.api.security.dto;

import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Setter
public class JwtAuthenticationDto {

    private String email;
    private String senha;

    @NotEmpty(message = "Email não pode ser vazio.")
    @Email(message = "Email inválido.")
    public String getEmail() {
        return email;
    }

    @NotEmpty(message = "Senha não pode ser vazia.")
    public String getSenha() {
        return senha;
    }

    @Override
    public String toString() {
        return "JwtAuthenticationRequestDto [email=" + email + ", senha=" + senha + "]";
    }

}
