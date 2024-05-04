package br.com.ismyburguer.pedido.gateway.out;

import br.com.ismyburguer.pedido.entity.Pedido;
import jakarta.validation.Valid;

import java.util.UUID;

public interface CadastrarPedidoRepository {
    UUID salvarPedido(@Valid Pedido pedido);
}
