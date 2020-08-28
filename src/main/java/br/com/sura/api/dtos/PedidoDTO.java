package br.com.sura.api.dtos;

import br.com.sura.api.model.Pedido;
import br.com.sura.api.model.PedidoItem;
import br.com.sura.api.model.Produto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PedidoDTO {

    @NotEmpty(message = "Para realizar um pedido adicione no mínimo um produto")
    private List<ItemPedidoDTO> produtosPedido = new ArrayList<>();

    public PedidoDTO(List<ItemPedidoDTO> produtosPedido) {
        this.produtosPedido = produtosPedido;
    }

    public PedidoDTO() {
    }

    public List<PedidoItem> listaNovoPedidoItens(Pedido pedido, List<Produto> listProdutoPedido) {
        List<PedidoItem> itens = new ArrayList<>();

        listProdutoPedido.forEach(x -> itens.add(new PedidoItem(pedido, x, quantidadeAdicionada(x))));

        return itens;
    }

    public void atualizaItem(Pedido pedido, List<Produto> listProdutoPedido) {
        List<PedidoItem> itens = pedido.getPedidoItems();

        listProdutoPedido.forEach(x -> {

            int quantidade = quantidadeAdicionada(x);

            if (itemJaExiste(pedido, x)) {
                itens.stream().filter(z -> z.getProduto().getId() == x.getId()).forEach(d -> {
                    d.setQuantidade(quantidade);
                    d.getProduto().setQuantidadeReserva(x.getQuantidadeReserva());
                });
            } else {
                itens.add(new PedidoItem(pedido, x, quantidade));
            }

        });
    }

    public int quantidadeAdicionada(Produto x) {
        return produtosPedido.stream().filter(y -> y.getIdProduto() == x.getId()).findFirst().get().getQuantidade();
    }

    public boolean itemJaExiste(Pedido pedido, Produto x) {
        return pedido.getPedidoItems().stream().filter(z -> z.getProduto().getId() == x.getId()).findFirst().isPresent();
    }
}
