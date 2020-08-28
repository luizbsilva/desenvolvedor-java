package br.com.sura.api.services.impl;

import br.com.sura.api.dtos.ItemPedidoDTO;
import br.com.sura.api.dtos.PedidoDTO;
import br.com.sura.api.dtos.ProdutoDTO;
import br.com.sura.api.exception.RegraNegocioException;
import br.com.sura.api.model.Categoria;
import br.com.sura.api.model.Produto;
import br.com.sura.api.repositories.ProdutoRepository;
import br.com.sura.api.services.CategoriaService;
import br.com.sura.api.services.ProdutoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    private static final Logger log = LoggerFactory.getLogger(ProdutoServiceImpl.class);

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaService categoriaService;

    @Override
    public Iterable<Produto> getProdutos() {
        log.info("Buscando todos Produtos");
        return produtoRepository.findAll();
    }

    @Override
    public Produto getByIdProdutos(Long id) {
        log.info("Buscando Produto  pelo ID {}", id);
        return produtoRepository.findById(id).orElseThrow(() -> new RegraNegocioException("O Produto não existe!"));
    }

    @Override
    public Produto persistir(Produto produto) {
        log.info("Persistindo Produto: {}", produto);
        return produtoRepository.save(produto);
    }

    @Override
    public void deletar(Long id) {
        log.info("Removendo o produto pelo ID {}", id);
        produtoRepository.delete(getByIdProdutos(id));

    }

    @Override
    public Produto alterar(Produto produto) {
        log.info("Alterando  Produto: {}", produto);
        return produtoRepository.save(produto);
    }

    @Override
    public Produto validarAtualizacao(Long id, ProdutoDTO produtoDTO) {
        log.info("Buscando cliente pelo IDl {}", id);

        Produto produto = getByIdProdutos(id);
        Categoria categoria = categoriaService.buscarPorId(produtoDTO.getIdCategoria()).get();
        Produto produtoUpdate = produtoDTO.novoProduto(categoria);
        produtoUpdate.setId(produto.getId());
        return produtoUpdate;
    }

    @Override
    public List<Produto> getListPedido(PedidoDTO produtoDTO) {
        List<ItemPedidoDTO> itemPedidoDTOS = produtoDTO.getProdutosPedido();
        List<Produto> list = new ArrayList<>();

        itemPedidoDTOS.forEach(produto -> list.add(getByIdProdutos(produto.getIdProduto())));

        return list;
    }

    @Override
    public void getAtualizaQtdReserva(List<Produto> produtoList, List<ItemPedidoDTO> itenPedidoDTOS) {
        produtoList.forEach(produto -> produto.setQuantidadeReserva(itenPedidoDTOS.stream()
                .filter(itemPedidoDTO -> itemPedidoDTO.getIdProduto() == produto.getId()).findFirst().get().getQuantidade()));

        produtoList.forEach(this::alterar);

    }

    @Override
    public Produto salvaProduto(ProdutoDTO produtoDTO) {
        Categoria categoria = categoriaService.buscarPorId(produtoDTO.getIdCategoria()).get();
        Produto produto = produtoDTO.novoProduto(categoria);

        persistir(produto);
        return produto;
    }
}
