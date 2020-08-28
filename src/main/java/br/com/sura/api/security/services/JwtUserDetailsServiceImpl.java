package br.com.sura.api.security.services;

import br.com.sura.api.model.Cliente;
import br.com.sura.api.security.JwtUserFactory;
import br.com.sura.api.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ClienteService clienteService;

    @Override
    public UserDetails loadUserByUsername(String username) throws RuntimeException {
        Optional<Cliente> cliente = clienteService.buscarPorEmail(username);

        if (cliente.isPresent()) {
            return JwtUserFactory.create(cliente.get());
        }

        throw new UsernameNotFoundException("Email não encontrado.");
    }

}
