package br.com.ismyburguer.pedido.web.api.converter;
import br.com.ismyburguer.pedido.entity.ItemPedido;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.web.api.response.BuscarItemPedidoResponse;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BuscarItemPedidoConverterTest {

    @Test
    void convert_DeveConverterItemPedidoParaBuscarItemPedidoResponse() {
        // Arrange
        UUID itemPedidoId = UUID.randomUUID();
        UUID produtoId = UUID.randomUUID();
        UUID pedidoId = UUID.randomUUID();
        BigDecimal quantidade = BigDecimal.valueOf(2);
        BigDecimal preco = BigDecimal.valueOf(5);
        BigDecimal total = BigDecimal.valueOf(10);
        ItemPedido itemPedido = new ItemPedido(new ItemPedido.ItemPedidoId(itemPedidoId),
                new Pedido.PedidoId(pedidoId),
                new ItemPedido.ProdutoId(produtoId),
                new ItemPedido.Quantidade(quantidade.intValue()),
                new ItemPedido.Preco(preco));

        BuscarItemPedidoConverter converter = new BuscarItemPedidoConverter();

        // Act
        BuscarItemPedidoResponse response = converter.convert(itemPedido);

        // Assert
        assertNotNull(response);
        assertEquals(itemPedidoId.toString(), response.getItemPedidoId());
        assertEquals(produtoId.toString(), response.getProdutoId());
        assertEquals(quantidade.intValue(), response.getQuantidade());
        assertEquals(preco, response.getPreco());
        assertEquals(total, response.getValorTotal());
    }

    @Test
    void convert_DeveLancarConstraintViolationExceptionQuandoItemPedidoForNulo() {
        // Arrange
        BuscarItemPedidoConverter converter = new BuscarItemPedidoConverter();

        // Act & Assert
        assertThrows(ConstraintViolationException.class, () -> converter.convert(null));
    }
}
