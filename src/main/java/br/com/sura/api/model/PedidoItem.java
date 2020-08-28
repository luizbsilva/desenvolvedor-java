package br.com.sura.api.model;

import br.com.sura.api.exception.RegraNegocioException;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "pedido_item")
public class PedidoItem extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @JsonBackReference
    @ManyToOne
    private Pedido pedido;

    @ManyToOne
    private Produto produto;

    @Column
    private int quantidade;

    @Column
    private BigDecimal valor;

    @Column
    private BigDecimal subTotal;

    public PedidoItem(Pedido pedido, Produto produto, int quantidade) {
        verificaStoque(produto, quantidade);

        this.pedido = pedido;
        this.produto = produto;
        this.quantidade = quantidade;
        this.valor = produto.getPreco();
        this.subTotal = new BigDecimal(quantidade).multiply(valor);
    }

    private void verificaStoque(Produto produto, int quantidade) {
        if (!produto.temEstoque(quantidade))
            throw new RegraNegocioException(
                    "Quantidade do estoque não é o suficiente, Produto: " + produto.getNomeProduto() + ", Estoque atual = " + produto.getQuantidade());
        if (quantidade <= 0)
            throw new RegraNegocioException("A quandidade mínima para realizar um pedido deve ser = 1");
    }

    public PedidoItem() {
    }

    public BigDecimal getSubTotal() {
        return new BigDecimal(quantidade).multiply(new BigDecimal(produto.getPreco().toString()));
    }

}
