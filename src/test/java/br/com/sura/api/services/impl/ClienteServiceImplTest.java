package br.com.sura.api.services.impl;

import br.com.sura.api.model.Cliente;
import br.com.sura.api.repositories.ClienteRepository;
import br.com.sura.api.services.ClienteService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest
@ActiveProfiles("test")
public class ClienteServiceImplTest {


    @MockBean
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    @Before
    public void setUp() throws Exception {
        BDDMockito.given(this.clienteRepository.save(Mockito.any(Cliente.class))).willReturn(new Cliente());
        BDDMockito.given(this.clienteRepository.findById(Mockito.anyLong())).willReturn(Optional.of(new Cliente()));
        BDDMockito.given(this.clienteRepository.findByEmail(Mockito.anyString())).willReturn(new Cliente());
        BDDMockito.given(this.clienteRepository.findByCpf(Mockito.anyString())).willReturn(new Cliente());
    }

    @Test
    public void testPersistirCliente() {
        Cliente cliente = this.clienteService.persistir(new Cliente());
        assertNotNull(cliente);
    }

    @Test
    public void testBuscarClientePorCpf() {
        Optional<Cliente> Cliente = this.clienteService.buscarPorCpf("24291173474");
        assertTrue(Cliente.isPresent());
    }

    @Test
    public void testBuscarClientePorEmail() {
        Optional<Cliente> Cliente = this.clienteService.buscarPorEmail("email@email.com");
        assertTrue(Cliente.isPresent());
    }

    @Test
    public void testBuscarClientePorId() {
        Optional<Cliente> Cliente = this.clienteService.buscarPorId(1L);
        assertTrue(Cliente.isPresent());
    }

}