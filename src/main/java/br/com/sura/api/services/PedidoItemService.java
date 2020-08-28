package br.com.sura.api.services;

import br.com.sura.api.model.PedidoItem;

import java.util.Optional;

public interface PedidoItemService {

    Iterable<PedidoItem> getItemPedido();

    Optional<PedidoItem> buscaPorIdItemPedido(Long id);

    PedidoItem persistir(PedidoItem PedidoItem);

    boolean deletar(Long id);

    PedidoItem alterar(PedidoItem PedidoItem);

}
