package br.com.ismyburguer.pedido.web.api.converter;
import br.com.ismyburguer.pedido.entity.ItemPedido;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.web.api.request.CadastrarItemPedidoRequest;
import br.com.ismyburguer.pedido.web.api.request.CadastrarPedidoRequest;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CadastrarPedidoRequestConverterTest {

    private CadastrarPedidoRequestConverter converter;

    @Mock
    private CadastrarItemPedidoRequestConverter itemPedidoRequestConverter;

    @BeforeEach
    void setUp() {
        itemPedidoRequestConverter = mock(CadastrarItemPedidoRequestConverter.class);
        converter = new CadastrarPedidoRequestConverter(itemPedidoRequestConverter);
    }

    @Test
    void convert_DeveConverterCadastrarPedidoRequestParaPedido() {
        // Arrange
        UUID clienteId = UUID.randomUUID();
        CadastrarItemPedidoRequest itemPedidoRequest = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(CadastrarItemPedidoRequest.class);
        itemPedidoRequest.setProdutoId(UUID.randomUUID().toString());
        itemPedidoRequest.setQuantidade(12);
        ItemPedido itemPedido = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(ItemPedido.class);
        when(itemPedidoRequestConverter.convert(itemPedidoRequest)).thenReturn(itemPedido);
        CadastrarPedidoRequest request = new CadastrarPedidoRequest();
        request.setClienteId(clienteId.toString());
        request.setItens(List.of(itemPedidoRequest));

        // Act
        Pedido pedido = converter.convert(request);

        // Assert
        assertNotNull(pedido);
        assertEquals(clienteId, pedido.getClienteId().get().getClienteId());
        assertFalse(pedido.getItens().isEmpty());
    }

    @Test
    void convert_DeveLancarConstraintViolationExceptionQuandoRequestForNulo() {
        // Act & Assert
        assertThrows(ConstraintViolationException.class, () -> converter.convert(null));
    }

    @Test
    void convert_DeveLancarConstraintViolationExceptionQuandoClienteIdForInvalido() {
        // Arrange
        CadastrarPedidoRequest request = new CadastrarPedidoRequest();
        request.setClienteId("invalid_uuid");

        // Act & Assert
        assertThrows(ConstraintViolationException.class, () -> converter.convert(request));
    }
}
