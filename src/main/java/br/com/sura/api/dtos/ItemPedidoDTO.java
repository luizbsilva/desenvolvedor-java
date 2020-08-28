package br.com.sura.api.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ItemPedidoDTO {

    @NotNull(message = "O Id do Produto  não pode ser vazio")
    private Long idProduto;

    @Min(value = 1L, message = "A quandidade mínima para realizar um pedido deve ser = 1")
    private int quantidade;

    public ItemPedidoDTO(Long idProduto, int quantidade) {
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }

    public ItemPedidoDTO() {
    }

}
