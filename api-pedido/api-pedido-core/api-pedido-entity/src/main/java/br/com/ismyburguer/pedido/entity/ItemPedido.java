package br.com.ismyburguer.pedido.entity;

import br.com.ismyburguer.core.validation.Validation;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class ItemPedido implements Validation {

    private ItemPedidoId itemPedidoId;

    private Pedido.PedidoId pedidoId;

    @NotNull(message = "Informe o código do Produto")
    @NonNull
    private ProdutoId produtoId;

    @NotNull(message = "Informe a quantidade do item")
    @NonNull
    private Quantidade quantidade;

    @NotNull(message = "Informe o preço do item")
    @NonNull
    private Preco preco;

    @Getter
    @AllArgsConstructor
    public static class ItemPedidoId {

        @NotNull(message = "Informe o código do Item do Pedido")
        private UUID itemPedidoId;

    }

    @Getter
    @AllArgsConstructor
    public static class Preco {

        @NotNull(message = "Informe o preço do item")
        @DecimalMin(value = "0.01", message = "O preço do item deve ser de no mínimo R$0,01")
        private BigDecimal valor;

    }

    @Getter
    @AllArgsConstructor
    public static class Quantidade {

        @NotNull(message = "Informe o preço do item")
        @Min(value = 1, message = "A quantidade do item do pedido deve ser de no mínimo 1")
        private Integer valor;

    }

    @Getter
    @AllArgsConstructor
    public static class Total {

        @NotNull(message = "Informe o preço do item")
        @DecimalMin(value = "0.01", message = "O valor total do item deve ser de no mínimo R$0,01")
        private BigDecimal valor;

    }

    @Getter
    @AllArgsConstructor
    public static class ProdutoId {

        @NotNull(message = "Informe o código do Produto")
        private UUID produtoId;

    }

    public Total getTotal() {
        validate();
        return new Total(this.preco.getValor().multiply(BigDecimal.valueOf(quantidade.getValor())));
    }

    public Optional<ItemPedidoId> getItemPedidoId() {
        return Optional.ofNullable(itemPedidoId);
    }

}
