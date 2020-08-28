package br.com.sura.api.services.impl;

import br.com.sura.api.exception.RegraNegocioException;
import br.com.sura.api.model.Cliente;
import br.com.sura.api.repositories.ClienteRepository;
import br.com.sura.api.services.ClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    private static final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente persistir(Cliente cliente) {
        log.info("Persistindo cliente: {}", cliente);
        if (Optional.ofNullable(this.clienteRepository.findByEmail(cliente.getEmail())).isPresent()){
            throw new RegraNegocioException("E-mail já cadastrado para outro Cliente");
        }
        return this.clienteRepository.save(cliente);
    }

    public Optional<Cliente> buscarPorCpf(String cpf) {
        log.info("Buscando cleinte pelo CPF {}", cpf);
        return Optional.ofNullable(this.clienteRepository.findByCpf(cpf));
    }

    public Optional<Cliente> buscarPorEmail(String email) {
        log.info("Buscando cliente pelo email {}", email);
        return Optional.ofNullable(this.clienteRepository.findByEmail(email));
    }

    public Optional<Cliente> buscarPorId(Long id) {
        log.info("Buscando cliente pelo IDl {}", id);
        return this.clienteRepository.findById(id);
    }

    public Boolean removerCliente(Long id) {

        Optional<Cliente> clienteResult = buscarPorId(id);
        if (clienteResult.isPresent()) {
            clienteRepository.delete(clienteResult.get());
            return Boolean.TRUE;
        }
        throw new RegraNegocioException("Erro ao tentar excluir o Cliente ");
    }

    public List<Cliente> getClientes() {
        log.info("Buscando clientes");
        return clienteRepository.findAll();
    }

}
