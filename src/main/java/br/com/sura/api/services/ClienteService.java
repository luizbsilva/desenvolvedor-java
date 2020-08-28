package br.com.sura.api.services;

import br.com.sura.api.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    /**
     * Persiste um cliente na base de dados.
     *
     * @param cliente
     * @return Cliente
     */
    Cliente persistir(Cliente cliente);

    /**
     * Busca e retorna um cliente dado um CPF.
     *
     * @param cpf
     * @return Optional<Cliente>
     */
    Optional<Cliente> buscarPorCpf(String cpf);

    /**
     * Busca e retorna um cliente dado um email.
     *
     * @param email
     * @return Optional<Cliente>
     */
    Optional<Cliente> buscarPorEmail(String email);

    /**
     * Busca e retorna um cliente por ID.
     *
     * @param id
     * @return Optional<Cliente>
     */
    Optional<Cliente> buscarPorId(Long id);

    /**
     * Remove um cliente por ID.
     *
     * @param id
     * @return Boolean
     */
    Boolean removerCliente(Long id);

    /**
     * Busca e retorna todos clientes.
     *
     * @return Iterable<Cliente>
     */
    List<Cliente> getClientes();
}
