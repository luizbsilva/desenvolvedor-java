package br.com.sura.api.dtos;

import br.com.sura.api.enums.SessaoPedidoEnum;
import br.com.sura.api.enums.StatusPedidoEnum;
import br.com.sura.api.model.Pedido;
import br.com.sura.api.model.PedidoItem;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class PedidoConsultaDTO {

    private Long idPedido;

    private StatusPedidoEnum status;

    private SessaoPedidoEnum sessao;

    private List<ProdutoConsultaDTO> produtosPedido = new ArrayList<>();

    public PedidoConsultaDTO(Pedido pedido) {

        this.idPedido = pedido.getId();
        this.status = pedido.getStatus();
        this.sessao = pedido.getSessao();
        this.produtosPedido = converterPedidoItens(pedido.getPedidoItems());
    }

    public PedidoConsultaDTO() {
    }

    public BigDecimal getSubtotalPedido() {
        Optional<BigDecimal> subtotal = produtosPedido.stream()
                .map(produtoConsultaDTO -> produtoConsultaDTO.getPreco().multiply(new BigDecimal(produtoConsultaDTO.getQuantidade()))).reduce(BigDecimal::add);
        if (subtotal.isPresent())
            return subtotal.get();

        return BigDecimal.ZERO;
    }

    private List<ProdutoConsultaDTO> converterPedidoItens(List<PedidoItem> pedidoItemList) {
        List<ProdutoConsultaDTO> produtoConsultaDTOS = new ArrayList<>();

        pedidoItemList.forEach(pedidoItem -> produtoConsultaDTOS.add(new ProdutoConsultaDTO(pedidoItem.getId(), pedidoItem.getProduto().getId(),
                pedidoItem.getProduto().getCategoria().getNomeCategoria(), pedidoItem.getProduto().getNomeProduto(), pedidoItem.getProduto().getPreco(),
                pedidoItem.getQuantidade(), pedidoItem.getProduto().getDescricao(), pedidoItem.getProduto().getFoto())));
        
        return produtoConsultaDTOS;
    }

    public List<PedidoConsultaDTO> converter(List<Pedido> pedidoList) {
        List<PedidoConsultaDTO> pedidoConsultaDTOList = new ArrayList<>();
        pedidoList.forEach(pedido -> pedidoConsultaDTOList.add(new PedidoConsultaDTO(pedido)));

        return pedidoConsultaDTOList;
    }

}
