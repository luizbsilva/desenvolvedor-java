package br.com.sura.api.services.impl;

import br.com.sura.api.dtos.PedidoDTO;
import br.com.sura.api.enums.SessaoPedidoEnum;
import br.com.sura.api.enums.StatusPedidoEnum;
import br.com.sura.api.model.Cliente;
import br.com.sura.api.model.Pedido;
import br.com.sura.api.model.PedidoItem;
import br.com.sura.api.model.Produto;
import br.com.sura.api.repositories.PedidoItemRepository;
import br.com.sura.api.repositories.PedidoRepository;
import br.com.sura.api.repositories.ProdutoRepository;
import br.com.sura.api.services.ClienteService;
import br.com.sura.api.services.PedidoItemService;
import br.com.sura.api.services.PedidoService;
import br.com.sura.api.services.ProdutoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PedidoServiceImpl implements PedidoService {

    private static final Logger log = LoggerFactory.getLogger(PedidoServiceImpl.class);

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ClienteService clienteService;

    @Override
    public Optional<List<Pedido>> getPedidos(String email) {
        return pedidoRepository.findByClienteEmail(email);
    }

    @Override
    public Optional<Pedido> getByIdPedido(Long id) {
        return pedidoRepository.findById(id);
    }

    @Override
    public Optional<Pedido> getPedidoIdAndEmail(Long id, String email) {
        return pedidoRepository.findByPedidoAndCliente(id, email);
    }

    @Override
    public Pedido savePedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Override
    public boolean deletePedido(Long id) {
        Optional<Pedido> pedido = getByIdPedido(id);

        if (!pedido.isPresent())
            return false;

        Map<String, Integer> listaProdutos = mapProdutosPosPedido(pedido.get());

        if (!atualizaQuantidadeProduto(listaProdutos))
            return false;

        pedidoRepository.deleteById(id);
        return true;
    }

    private Map<String, Integer> mapProdutosPosPedido(Pedido pedido) {
        Map<String, Integer> listaProdutos = new HashMap<>();
        pedido.getPedidoItems()
                .forEach(x -> listaProdutos.put(String.valueOf(x.getProduto().getId()), x.getQuantidade()));
        return listaProdutos;
    }

    private boolean atualizaQuantidadeProduto(Map<String, Integer> listaProdutos) {

        listaProdutos.keySet().forEach(x -> {
            Produto produto = produtoService.getByIdProdutos(Long.valueOf(x));
            produto.setQuantidadeReserva(listaProdutos.get(x));
            produto.setQuantidade(listaProdutos.get(x));
            produtoService.persistir(produto);
        });

        return true;
    }

    @Override
    public Pedido updatePedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Override
    public boolean finalizarPedido(Pedido pedido) {
        pedido.setStatus(StatusPedidoEnum.FINALIZADO);
        Map<String, Integer> listaProdutos = mapProdutosPosPedido(pedido);

        if (!atualizaQuantidadeProduto(listaProdutos))
            return false;

        updatePedido(pedido);
        return true;
    }

    @Override
    public Pedido realizarPedido(PedidoDTO pedidoDTO, String email) {

        Optional<Cliente> cliente = clienteService.buscarPorEmail(email);

        Pedido pedido = new Pedido(cliente.get(), SessaoPedidoEnum.VENDA);
        List<Produto> listProdutoPedido = produtoService.getListPedido(pedidoDTO);

        List<PedidoItem> listaItens = pedidoDTO.listaNovoPedidoItens(pedido, listProdutoPedido);
        pedido.setListaItens(listaItens);

        savePedido(pedido);
        produtoService.getAtualizaQtdReserva(listProdutoPedido, pedidoDTO.getProdutosPedido());
        return pedido;
    }

    @Override
    public void adicionaOuRemoveProdutoNoPedidoEp(PedidoDTO pedidoDTO, Optional<Pedido> pedido) {
        List<Produto> listProdutoPedido = produtoService.getListPedido(pedidoDTO);

        pedidoDTO.atualizaItem(pedido.get(), listProdutoPedido);

        savePedido(pedido.get());
        produtoService.getAtualizaQtdReserva(listProdutoPedido, pedidoDTO.getProdutosPedido());

    }
}
