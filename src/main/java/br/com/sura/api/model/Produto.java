package br.com.sura.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "produto")
public class Produto extends AbstractEntity {


    private static final long serialVersionUID = 1L;

    @ManyToOne
    private Categoria categoria;

    @Column(name="produto")
    private String nomeProduto;

    @Column
    private BigDecimal preco;

    @Column
    private Integer quantidade;

    @Column(columnDefinition = "INTEGER default 0")
    private Integer quantidadeReserva = 0;

    @Column
    private String descricao;

    @Lob
    private byte[] foto;

    public Produto(Categoria categoria, String nomeProduto, BigDecimal preco, Integer quantidade, String descricao,
                   byte[] foto) {
        this.categoria = categoria;
        this.nomeProduto = nomeProduto;
        this.preco = preco;
        this.quantidade = quantidade;
        this.quantidadeReserva = 0;
        this.descricao = descricao;
        this.foto = foto;
    }

    public Produto() {
    }

    @OneToMany(mappedBy = "produto")
    @JsonIgnore
    private List<PedidoItem> pedidoItens = new ArrayList<>();


    public boolean temEstoque(int qtd) {
        return getQuantidade() >= qtd;
    }


}
