package br.com.ismyburguer.pedido.gateway.out;

import br.com.ismyburguer.pedido.entity.Pedido;

public interface AlterarStatusPedidoRepository {
    void alterar(Pedido.PedidoId pedidoId, Pedido.StatusPedido statusPedido);
}
