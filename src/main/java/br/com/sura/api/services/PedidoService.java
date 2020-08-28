package br.com.sura.api.services;

import br.com.sura.api.dtos.PedidoDTO;
import br.com.sura.api.model.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoService {

    Optional<List<Pedido>> getPedidos(String email);

    Optional<Pedido> getByIdPedido(Long id);

    Optional<Pedido> getPedidoIdAndEmail(Long id, String email);

    Pedido savePedido(Pedido pedido);

    boolean deletePedido(Long id);

    Pedido updatePedido(Pedido pedido);

    boolean finalizarPedido(Pedido pedido);

    Pedido realizarPedido(PedidoDTO pedidoDTO, String email);

    void adicionaOuRemoveProdutoNoPedidoEp(PedidoDTO pedidoDTO, Optional<Pedido> pedido);

}
