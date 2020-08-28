package br.com.sura.api.security;

import br.com.sura.api.enums.PerfilEnum;
import br.com.sura.api.model.Cliente;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class JwtUserFactory {

    private JwtUserFactory() {
    }

    /**
     * Converte e gera um JwtUser com base nos dados de um funcionário.
     *
     * @param cliente
     * @return JwtUser
     */
    public static JwtUser create(Cliente cliente) {
        return new JwtUser(cliente.getId(), cliente.getEmail(), cliente.getSenha(),
                mapToGrantedAuthorities(cliente.getPerfil()));
    }

    /**
     * Converte o perfil do usuário para o formato utilizado pelo Spring Security.
     *
     * @param perfilEnum
     * @return List<GrantedAuthority>
     */
    private static List<GrantedAuthority> mapToGrantedAuthorities(PerfilEnum perfilEnum) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(perfilEnum.toString()));
        return authorities;
    }

}
