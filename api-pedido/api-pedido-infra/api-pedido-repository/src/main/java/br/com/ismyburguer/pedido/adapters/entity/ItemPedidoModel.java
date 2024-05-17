package br.com.ismyburguer.pedido.adapters.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "item_pedido",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_item_pedido_produto", columnNames = {"pedido_id", "produto_id"})
        }
)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {
        "itemPedidoId",
        "produtoId",
})
public class ItemPedidoModel {

    @Id
    @NonNull
    private UUID itemPedidoId = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "pedido_id", referencedColumnName = "pedido_id", foreignKey = @ForeignKey(name = "fk_item_pedido_pedido"))
    private PedidoModel pedido;

    @Column(name = "produto_id", columnDefinition = "uuid references produto(produto_id)")
    @NonNull
    private UUID produtoId;

    @NonNull
    private int quantidade;

    @NotNull
    @NonNull
    private BigDecimal preco;

    @NotNull
    @NonNull
    private BigDecimal valorTotal;

    public ItemPedidoModel(UUID itemPedidoId, UUID produtoId, int quantidade, BigDecimal preco, BigDecimal valorTotal) {
        this.itemPedidoId = itemPedidoId;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.preco = preco;
        this.valorTotal = valorTotal;
    }
}
