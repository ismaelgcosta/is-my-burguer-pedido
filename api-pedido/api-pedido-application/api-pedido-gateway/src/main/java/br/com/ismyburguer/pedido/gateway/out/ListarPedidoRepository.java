package br.com.ismyburguer.pedido.gateway.out;

import br.com.ismyburguer.pedido.entity.Pedido;

import java.util.List;

public interface ListarPedidoRepository {
    List<Pedido> listar();
}
