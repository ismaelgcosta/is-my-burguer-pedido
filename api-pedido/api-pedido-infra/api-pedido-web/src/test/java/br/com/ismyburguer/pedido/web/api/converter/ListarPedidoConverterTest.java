package br.com.ismyburguer.pedido.web.api.converter;

import br.com.ismyburguer.pedido.entity.ItemPedido;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.web.api.response.ListarPedidoResponse;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ListarPedidoConverterTest {

    private ListarPedidoConverter converter;

    @BeforeEach
    void setUp() {
        converter = new ListarPedidoConverter();
    }

    @Test
    void convert_DeveConverterPedidoParaListarPedidoResponse() {
        // Arrange
        Pedido pedido = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Pedido.class);
        pedido.getItens().add(new ItemPedido(new ItemPedido.ProdutoId(UUID.randomUUID()), new ItemPedido.Quantidade(1), new ItemPedido.Preco(BigDecimal.TEN)));

        // Act
        ListarPedidoResponse response = converter.convert(pedido);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getPedidoId());
        assertNotNull(response.getClienteId());
        assertNotNull(response.getStatus());
        assertNotNull(response.getValorTotal());
        assertEquals(pedido.getPedidoId().get().getPedidoId().toString(), response.getPedidoId());
        assertEquals(pedido.getClienteId().get().getClienteId().toString(), response.getClienteId());
        assertEquals(pedido.getStatusPedido().name(), response.getStatus());
        assertEquals(pedido.getTotal().getValor(), response.getValorTotal());
    }

    @Test
    void convert_DeveLancarConstraintViolationExceptionQuandoPedidoForNulo() {
        // Act & Assert
        assertThrows(ConstraintViolationException.class, () -> converter.convert(null));
    }
}
