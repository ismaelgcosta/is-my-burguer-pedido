package br.com.ismyburguer.pedido.gateway.out;

import br.com.ismyburguer.pedido.entity.Pedido;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ListarPedidoRepository {
    List<Pedido> listar();
}
