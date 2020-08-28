package br.com.sura.api.services.impl;

import br.com.sura.api.model.PedidoItem;
import br.com.sura.api.model.Produto;
import br.com.sura.api.repositories.PedidoItemRepository;
import br.com.sura.api.repositories.ProdutoRepository;
import br.com.sura.api.services.PedidoItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoItemServiceImpl implements PedidoItemService {

    private static final Logger log = LoggerFactory.getLogger(PedidoItemServiceImpl.class);

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoItemRepository pedidoItemRepository;

    @Override
    public Iterable<PedidoItem> getItemPedido() {
        return pedidoItemRepository.findAll();
    }

    @Override
    public Optional<PedidoItem> buscaPorIdItemPedido(Long id) {
        return pedidoItemRepository.findById(id);
    }

    @Override
    public PedidoItem persistir(PedidoItem pedidoItem) {
        return pedidoItemRepository.save(pedidoItem);
    }

    @Override
    public boolean deletar(Long id) {
        Optional<PedidoItem> pedidoItens = buscaPorIdItemPedido(id);

        if (!pedidoItens.isPresent())
            return false;

        int quantidadeDevolvida = pedidoItens.get().getQuantidade();
        Long idProduto = pedidoItens.get().getProduto().getId();

        if (!atualizaValorResevaProduto(idProduto, quantidadeDevolvida))
            return false;

        pedidoItemRepository.deleteById(id);
        return true;
    }

    @Override
    public PedidoItem alterar(PedidoItem pedidoItem) {
        return pedidoItemRepository.save(pedidoItem);
    }

    private boolean atualizaValorResevaProduto(Long idProduto, int quantidade) {
        Optional<Produto> produto = produtoRepository.findById(idProduto);
        if (!produto.isPresent())
            return false;

        produto.get().setQuantidadeReserva(quantidade);
        produtoRepository.save(produto.get());
        return true;
    }
}
