package br.com.sura.api.repositories;


import br.com.sura.api.enums.PerfilEnum;
import br.com.sura.api.model.Cliente;
import br.com.sura.api.utils.PasswordUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class ClienteRepositoryTest {


    @Autowired
    private ClienteRepository clienteRepository;

    private static final String EMAIL = "email@email.com";
    private static final String CPF = "24291173474";

    @Before
    public void setUp() throws Exception {
        this.clienteRepository.save(obterDadosFuncionario());
    }

    @Test
    public void testBuscarFuncionarioPorEmail() {
        Cliente cliente = this.clienteRepository.findByEmail(EMAIL);
        assertEquals(EMAIL, cliente.getEmail());
    }

    @Test
    public void testBuscarFuncionarioPorCpf() {
        Cliente cliente = this.clienteRepository.findByCpf(CPF);

        assertEquals(CPF, cliente.getCpf());
    }

    @Test
    public void testBuscarFuncionarioPorEmailECpf() {
        Cliente cliente = this.clienteRepository.findByCpfOrEmail(CPF, EMAIL);
        assertNotNull(cliente);
    }

    @Test
    public void testBuscarFuncionarioPorEmailOuCpfParaEmailInvalido() {
        Cliente cliente  = this.clienteRepository.findByCpfOrEmail(CPF, "email@invalido.com");
        assertNotNull(cliente);
    }

    @Test
    public void testBuscarFuncionarioPorEmailECpfParaCpfInvalido() {
        Cliente cliente = this.clienteRepository.findByCpfOrEmail("12345678901", EMAIL);
        assertNotNull(cliente);
    }

    private Cliente obterDadosFuncionario() throws NoSuchAlgorithmException {
        Cliente Cliente = new Cliente();
        Cliente.setNome("Fulano de Tal");
        Cliente.setPerfil(PerfilEnum.ROLE_USUARIO);
        Cliente.setSenha(PasswordUtils.gerarBCrypt("123456"));
        Cliente.setCpf(CPF);
        Cliente.setEmail(EMAIL);
        return Cliente;
    }
}
