package br.com.ismyburguer.pedido.adapter.interfaces.in;

import br.com.ismyburguer.pedido.entity.Pedido;

import java.util.UUID;

public interface CadastrarPedidoUseCase {
    UUID cadastrar(Pedido pedido);
}
