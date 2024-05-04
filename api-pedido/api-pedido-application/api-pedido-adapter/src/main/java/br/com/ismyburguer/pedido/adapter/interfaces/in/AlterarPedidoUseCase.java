package br.com.ismyburguer.pedido.adapter.interfaces.in;

import br.com.ismyburguer.pedido.entity.Pedido;

public interface AlterarPedidoUseCase {
    void alterar(String pedidoId, Pedido pedido);
}
