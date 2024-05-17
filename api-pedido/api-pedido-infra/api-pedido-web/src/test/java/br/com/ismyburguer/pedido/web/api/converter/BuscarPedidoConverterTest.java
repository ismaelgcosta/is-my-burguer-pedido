package br.com.ismyburguer.pedido.web.api.converter;

import br.com.ismyburguer.pedido.entity.ItemPedido;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.web.api.response.BuscarPedidoResponse;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BuscarPedidoConverterTest {

    @Test
    void convert_DeveConverterPedidoParaBuscarPedidoResponse() {
        // Arrange
        UUID pedidoId = UUID.randomUUID();
        UUID clienteId = UUID.randomUUID();
        BigDecimal total = BigDecimal.valueOf(50);
        List<ItemPedido> itens = new ArrayList<>();
        Pedido pedido = new Pedido(new Pedido.PedidoId(pedidoId), new Pedido.ClienteId(clienteId), Pedido.StatusPedido.ABERTO, itens);
        pedido.getItens().add(new ItemPedido(new ItemPedido.ItemPedidoId(UUID.randomUUID()),
                new Pedido.PedidoId(pedidoId),
                new ItemPedido.ProdutoId(UUID.randomUUID()),
                new ItemPedido.Quantidade(1),
                new ItemPedido.Preco(total)));
        BuscarItemPedidoConverter buscarItemPedidoConverter = new BuscarItemPedidoConverter();
        BuscarPedidoConverter converter = new BuscarPedidoConverter(buscarItemPedidoConverter);

        // Act
        BuscarPedidoResponse response = converter.convert(pedido);

        // Assert
        assertNotNull(response);
        assertEquals(pedidoId.toString(), response.getPedidoId());
        assertEquals(clienteId.toString(), response.getClienteId());
        assertFalse(response.getItens().isEmpty());
        assertEquals("ABERTO", response.getStatus());
        assertEquals(total, response.getValorTotal());
    }

    @Test
    void convert_DeveLancarConstraintViolationExceptionQuandoPedidoForNulo() {
        // Arrange
        BuscarItemPedidoConverter buscarItemPedidoConverter = new BuscarItemPedidoConverter();
        BuscarPedidoConverter converter = new BuscarPedidoConverter(buscarItemPedidoConverter);

        // Act & Assert
        assertThrows(ConstraintViolationException.class, () -> converter.convert(null));
    }
}
