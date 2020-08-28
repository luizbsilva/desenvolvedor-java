package br.com.sura.api.dtos;

import br.com.sura.api.model.Categoria;
import br.com.sura.api.model.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProdutoDTO {

    @NotNull(message = "O id da categoria não pode ser vazio")
    private Long idCategoria;

    @NotEmpty(message = "O produto não pode ser vazio")
    private String produto;

    @NotNull(message = "O preço não pode ser vazio")
    private BigDecimal preco;

    @NotNull(message = "A quantidade não pode ser vazio")
    private Integer quantidade;

    @NotEmpty(message = "A descrição detalhada não pode ser vazio")
    private String descricao;
    @Lob
    private byte[] foto;


    @JsonIgnore
    public List<Produto> converterProduto(Iterable<Produto> produtos) {
        List<Produto> list = new ArrayList<>();
        produtos.forEach(list::add);
        return list;
    }

    public Produto novoProduto(Categoria categoria) {
        return new Produto(categoria,produto,preco,quantidade,descricao,foto);
    }

}
