package br.com.sura.api.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Lob;
import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoConsultaDTO {

    private Long idProdutoItem;

    private Long idProduto;

    private String produto;

    private String categoria;

    private BigDecimal preco;

    private Integer quantidade;

    private String descricao;

    @Lob
    private byte[] foto;

    public ProdutoConsultaDTO(Long idProdutoItem, Long idProduto, String categoria, String produto, BigDecimal preco,
                              Integer quantidade, String descricao, byte[] foto) {
        this.idProdutoItem = idProdutoItem;
        this.idProduto = idProduto;
        this.categoria = categoria;
        this.produto = produto;
        this.preco = preco;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.foto = foto;
    }

    public ProdutoConsultaDTO() {
    }
}
