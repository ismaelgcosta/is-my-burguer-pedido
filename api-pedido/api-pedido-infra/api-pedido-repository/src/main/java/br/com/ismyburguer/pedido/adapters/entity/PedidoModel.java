package br.com.ismyburguer.pedido.adapters.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "pedido")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "pedidoId")
public class PedidoModel {

    @Id
    @Column(name = "pedido_id")
    private UUID pedidoId = UUID.randomUUID();

    @Column(name = "cliente_id", columnDefinition = "uuid references cliente(cliente_id)")
    private UUID clienteId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedido", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<ItemPedidoModel> itens;

    @Enumerated(EnumType.STRING)
    private StatusPedidoEntity statusPedido = StatusPedidoEntity.ABERTO;

    private BigDecimal valorTotal;

    public PedidoModel(UUID clienteId, StatusPedidoEntity statusPedido, BigDecimal valorTotal) {
        this.clienteId = clienteId;
        this.statusPedido = statusPedido;
        this.valorTotal = valorTotal;
    }

    public Optional<UUID> getClienteId() {
        return Optional.ofNullable(clienteId);
    }

    public void limparItens() {
        itens = itens == null ? new HashSet<>() : itens;
        itens.clear();
    }

    public void addItem(ItemPedidoModel item) {
        itens = itens == null ? new HashSet<>() : itens;
        itens.add(item);
        itens.forEach(itemPedidoModel -> itemPedidoModel.setPedido(this));
    }
}
