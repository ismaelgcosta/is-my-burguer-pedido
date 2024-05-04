package br.com.ismyburguer.controlepedido.adapters.response;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "controle_pedido")
@AllArgsConstructor
@NoArgsConstructor
public class ControlePedidoResponse {

    @Id
    private UUID controlePedidoId = UUID.randomUUID();

    @Column(name = "pedido_id", columnDefinition = "uuid references pedido(pedido_id)")
    private UUID pedidoId;

    @Enumerated(EnumType.STRING)
    private StatusControlePedido statusControlePedido;

    private LocalDateTime recebidoEm = LocalDateTime.now();

    @Setter
    private LocalDateTime inicioDaPreparacao;

    @Setter
    private LocalDateTime fimDaPreparacao;

    public ControlePedidoResponse(UUID pedidoId, StatusControlePedido statusControlePedido, LocalDateTime recebidoEm) {
        this.pedidoId = pedidoId;
        this.statusControlePedido = statusControlePedido;
        this.recebidoEm = recebidoEm;
    }

    public ControlePedidoResponse(UUID controlePedidoId, UUID pedidoId, StatusControlePedido statusControlePedido, LocalDateTime recebidoEm) {
        this.controlePedidoId = controlePedidoId;
        this.pedidoId = pedidoId;
        this.statusControlePedido = statusControlePedido;
        this.recebidoEm = recebidoEm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ControlePedidoResponse that)) return false;

        return new EqualsBuilder().append(getControlePedidoId(), that.getControlePedidoId()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getControlePedidoId()).toHashCode();
    }
}
