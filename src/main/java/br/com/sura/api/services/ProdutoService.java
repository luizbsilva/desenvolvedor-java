package br.com.sura.api.services;

import br.com.sura.api.dtos.ItemPedidoDTO;
import br.com.sura.api.dtos.PedidoDTO;
import br.com.sura.api.dtos.ProdutoDTO;
import br.com.sura.api.model.Produto;

import java.util.List;

public interface ProdutoService {

    Iterable<Produto> getProdutos();

    Produto getByIdProdutos(Long id);

    Produto persistir(Produto produto);

    void deletar(Long id);

    Produto alterar(Produto produto);

    Produto validarAtualizacao(Long id, ProdutoDTO produtoDTO);

    List<Produto> getListPedido(PedidoDTO produtoDTO);

    void getAtualizaQtdReserva(List<Produto> produtoList, List<ItemPedidoDTO> itenPedidoDTOS);

    Produto salvaProduto(ProdutoDTO produtoDTO);


}
