package br.com.sura.api.model;

import br.com.sura.api.enums.SessaoPedidoEnum;
import br.com.sura.api.enums.StatusPedidoEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "pedido")
public class Pedido extends AbstractEntity {


    private static final long serialVersionUID = 1L;

    @Column
    private LocalDate data = LocalDate.now();

    @ManyToOne
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    @Column
    private StatusPedidoEnum status = StatusPedidoEnum.ATIVO;

    @Enumerated(EnumType.STRING)
    private SessaoPedidoEnum sessao;

    @JsonManagedReference
    @OneToMany(mappedBy = "pedido", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<PedidoItem> pedidoItems = new ArrayList<>();

    public Pedido(Cliente cliente, SessaoPedidoEnum sessao) {
        this.cliente = cliente;
        this.sessao = sessao;
    }

    public Pedido() {
    }

    public void setListaItens(List<PedidoItem> pedidoItems) {
        this.pedidoItems.clear();
        this.pedidoItems.addAll(pedidoItems);
    }


    public void removerItemProduto(PedidoItem item) {
        this.pedidoItems.remove(item);
    }
}
