package br.com.ismyburguer.pedido.gateway.out;

import java.util.UUID;

public interface FecharPedidoRepository {
    void fecharPedido(UUID pedidoId);
}
