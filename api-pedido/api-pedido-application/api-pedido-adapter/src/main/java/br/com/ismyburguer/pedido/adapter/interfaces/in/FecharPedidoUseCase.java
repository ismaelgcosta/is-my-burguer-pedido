package br.com.ismyburguer.pedido.adapter.interfaces.in;

import br.com.ismyburguer.pedido.entity.Pedido;

public interface FecharPedidoUseCase {
    void fechar(Pedido.PedidoId pedidoId);
}
