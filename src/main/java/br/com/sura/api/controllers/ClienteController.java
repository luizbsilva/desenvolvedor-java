package br.com.sura.api.controllers;

import br.com.sura.api.dtos.ClienteDTO;
import br.com.sura.api.enums.PerfilEnum;
import br.com.sura.api.model.Cliente;
import br.com.sura.api.response.Response;
import br.com.sura.api.services.ClienteService;
import br.com.sura.api.utils.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin(origins = "*")
public class ClienteController {

    private static final Logger log = LoggerFactory.getLogger(ClienteController.class);


    @Autowired
    private ClienteService clienteService;


    /**
     * Busca todos Clientes pessoa física no sistema.
     *
     * @return ResponseEntity<Response<ClienteDTO>>
     */

    @GetMapping("/cliente")
    public ResponseEntity<List<ClienteDTO>> listarClientes(){
        return ResponseEntity.ok(ClienteDTO.converter(clienteService.getClientes()));
    }

    /**
     * Cadastra um Clientes no sistema.
     *
     * @param clienteDto
     * @param result
     * @return ResponseEntity<Response<CadastroPFDto>>
     * @throws NoSuchAlgorithmException
     */
    @PostMapping
    public ResponseEntity<Response<ClienteDTO>> cadastrar(@Valid @RequestBody ClienteDTO clienteDto,
                                                          BindingResult result) throws NoSuchAlgorithmException {
        log.info("Cadastrando Cliente: {}", clienteDto.toString());
        Response<ClienteDTO> response = new Response<>();

        validarDadosExistentes(clienteDto, result);
        Cliente cliente = this.converterDtoParaCliente(clienteDto, result);

        if (result.hasErrors()) {
            log.error("Erro validando dados de cadastro PF: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        this.clienteService.persistir(cliente);

        response.setData(this.converterCadastroPFDto(cliente));
        return ResponseEntity.ok(response);
    }

    /**
     * Busca um Cliente por ID no sistema.
     *
     * @return ResponseEntity<Response<ClienteDTO>>
     */

    @GetMapping("/cliente/{id}")
    public ResponseEntity<ClienteDTO> buscarClientePorId(@PathVariable Long id){
        return ResponseEntity.ok(converterCadastroPFDto(clienteService.buscarPorId(id).get()));
    }

    /**
     * Verifica se o Cliente não existe na base de dados.
     *
     * @param clienteDto
     * @param result
     */
    private void validarDadosExistentes(ClienteDTO clienteDto, BindingResult result) {

        this.clienteService.buscarPorCpf(clienteDto.getCpf())
                .ifPresent(func -> result.addError(new ObjectError("cliente", "CPF já existente.")));

        this.clienteService.buscarPorEmail(clienteDto.getEmail())
                .ifPresent(func -> result.addError(new ObjectError("funcionario", "Email já existente.")));
    }

    /**
     * Converte os dados do DTO para funcionário.
     *
     * @param cadastroPFDto
     * @param result
     * @return Funcionario
     * @throws NoSuchAlgorithmException
     */
    private Cliente converterDtoParaCliente(ClienteDTO cadastroPFDto, BindingResult result)
            throws NoSuchAlgorithmException {
        Cliente cliente = new Cliente();
        cliente.setNome(cadastroPFDto.getNome());
        cliente.setEmail(cadastroPFDto.getEmail());
        cliente.setCpf(cadastroPFDto.getCpf());
        cliente.setPerfil(PerfilEnum.ROLE_USUARIO);
        cliente.setSenha(PasswordUtils.gerarBCrypt(cadastroPFDto.getSenha()));
        cliente.setRua(cadastroPFDto.getRua());
        cliente.setCidade(cadastroPFDto.getCidade());
        cliente.setBairro(cadastroPFDto.getBairro());
        cliente.setCep(cadastroPFDto.getCep());
        cliente.setEstado(cadastroPFDto.getEstado());

        return cliente;
    }

    /**
     * Popula o DTO de cadastro com os dados do funcionário e empresa.
     *
     * @param cliente
     * @return CadastroPFDto
     */
    private ClienteDTO converterCadastroPFDto(Cliente cliente) {
        ClienteDTO cadastroPFDto = new ClienteDTO();
        cadastroPFDto.setId(cliente.getId());
        cadastroPFDto.setNome(cliente.getNome());
        cadastroPFDto.setEmail(cliente.getEmail());
        cadastroPFDto.setCpf(cliente.getCpf());
        cadastroPFDto.setRua(cliente.getRua());
        cadastroPFDto.setCidade(cliente.getCidade());
        cadastroPFDto.setBairro(cliente.getBairro());
        cadastroPFDto.setCep(cliente.getCep());
        cadastroPFDto.setEstado(cliente.getEstado());

        return cadastroPFDto;
    }
}
