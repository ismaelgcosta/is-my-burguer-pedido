package br.com.ismyburguer.pedido.gateway.out;

import br.com.ismyburguer.pedido.entity.Pedido;
import jakarta.validation.Valid;

public interface AlterarPedidoRepository {
    void alterarPedido(String pedidoId, @Valid Pedido pedido);
}
