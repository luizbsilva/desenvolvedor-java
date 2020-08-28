package br.com.sura.api.controllers;

import br.com.sura.api.dtos.PedidoConsultaDTO;
import br.com.sura.api.dtos.PedidoDTO;
import br.com.sura.api.enums.StatusPedidoEnum;
import br.com.sura.api.model.Pedido;
import br.com.sura.api.services.PedidoItemService;
import br.com.sura.api.services.PedidoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedido")
@CrossOrigin(origins = "*")
public class PedidoController {

    private static final Logger log = LoggerFactory.getLogger(PedidoController.class);


    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoItemService pedidoItemService;


    @PostMapping("/{email}")
    @Transactional
    public ResponseEntity<PedidoConsultaDTO> realizarPedido(@PathVariable String email, @RequestBody @Valid PedidoDTO pedidoDto) {

        Pedido pedido = pedidoService.realizarPedido(pedidoDto, email);

        return ResponseEntity.ok(new PedidoConsultaDTO(pedido));

    }


    @GetMapping("/pedido/{email}")
    public ResponseEntity<List<PedidoConsultaDTO>> listaMeusPedidos(@PathVariable String email) {

        Optional<List<Pedido>> pedidos = pedidoService.getPedidos(email);

        if (pedidos.isPresent())
            return ResponseEntity.ok(new PedidoConsultaDTO().converter(pedidos.get()));

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/pedido/{id}/{email}")
    public ResponseEntity<PedidoConsultaDTO> buscarPedidosPorEmailEId(@PathVariable Long id, @PathVariable String email) {

        Optional<Pedido> pedido = pedidoService.getPedidoIdAndEmail(id, email);

        if (pedido.isPresent()) {
            return ResponseEntity.ok(new PedidoConsultaDTO(pedido.get()));
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/pedido/{id}/{email}")
    @Transactional
    public ResponseEntity<PedidoConsultaDTO> adicionarOuAlterarItem(@PathVariable Long id, @PathVariable String email,
                                                                    @RequestBody @Valid PedidoDTO pedidoDto) {

        Optional<Pedido> pedido = pedidoService.getPedidoIdAndEmail(id, email);

        if (pedido.isPresent()) {

            if (pedido.get().getStatus() == StatusPedidoEnum.FINALIZADO)
                return ResponseEntity.status(HttpStatus.FORBIDDEN).eTag("O Pedido já se encontra-se finalizado")
                        .build();

            pedidoService.adicionaOuRemoveProdutoNoPedidoEp(pedidoDto, pedido);

            return ResponseEntity.ok(new PedidoConsultaDTO(pedido.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/pedido/finalizar/{idPedido}/{email}")
    @Transactional
    public ResponseEntity<?> finalizarPedido(@PathVariable Long idPedido, @PathVariable String email) {

        Optional<Pedido> pedido = pedidoService.getPedidoIdAndEmail(idPedido, email);
        if (pedido.isPresent()) {

            if (pedido.get().getStatus() == StatusPedidoEnum.FINALIZADO.FINALIZADO)
                return ResponseEntity.status(HttpStatus.FORBIDDEN).eTag("O Pedido já se encontra-se finalizado")
                        .build();

            pedidoService.finalizarPedido(pedido.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/pedido/cancelarPedidoItem/{idPedido}/{idPedidoItem}/{email}")
    @Transactional
    public ResponseEntity<?> cancelarPedidoItem(@PathVariable Long idPedido, @PathVariable Long idPedidoItem, @PathVariable String email) {

        Optional<Pedido> pedido = pedidoService.getPedidoIdAndEmail(idPedido, email);
        if (pedido.isPresent()) {

            if (pedido.get().getStatus() == StatusPedidoEnum.FINALIZADO.FINALIZADO)
                return ResponseEntity.status(HttpStatus.FORBIDDEN).eTag("O Pedido já se encontra-se finalizado")
                        .build();

            if (pedidoItemService.deletar(idPedidoItem)) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/pedido/{idPedido}/{email}")
    @Transactional
    public ResponseEntity<?> cancelarPedido(@PathVariable Long idPedido,
                                            @PathVariable String email) {

        Optional<Pedido> pedido = pedidoService.getPedidoIdAndEmail(idPedido, email);

        if (pedido.isPresent()) {
            pedidoService.deletePedido(pedido.get().getId());
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

}
