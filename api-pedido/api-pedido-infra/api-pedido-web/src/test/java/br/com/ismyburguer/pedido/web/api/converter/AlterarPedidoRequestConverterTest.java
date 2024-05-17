package br.com.ismyburguer.pedido.web.api.converter;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.web.api.request.AlterarItemPedidoRequest;
import br.com.ismyburguer.pedido.web.api.request.AlterarPedidoRequest;
import br.com.ismyburguer.core.adapter.Converter;
import br.com.ismyburguer.pedido.web.api.request.CadastrarItemPedidoRequest;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AlterarPedidoRequestConverterTest {

    @Test
    void convert_DeveConverterAlterarPedidoRequestParaPedido() {
        // Arrange
        UUID clienteId = UUID.randomUUID();
        String produtoId = UUID.randomUUID().toString();
        int quantidade = 2;
        BigDecimal preco = BigDecimal.valueOf(10);
        AlterarItemPedidoRequest pedidoRequest = new AlterarItemPedidoRequest();
        pedidoRequest.setPreco(preco);
        pedidoRequest.setQuantidade(quantidade);
        pedidoRequest.setProdutoId(produtoId);

        List<AlterarItemPedidoRequest> itens = Collections.singletonList(pedidoRequest);
        AlterarPedidoRequest request = new AlterarPedidoRequest();
        request.setClienteId(clienteId.toString());
        request.setItens(itens);
        AlterarItemPedidoRequestConverter itemPedidoRequestConverter = new AlterarItemPedidoRequestConverter();
        AlterarPedidoRequestConverter converter = new AlterarPedidoRequestConverter(itemPedidoRequestConverter);

        // Act
        Pedido pedido = converter.convert(request);

        // Assert
        assertNotNull(pedido);
        assertEquals(clienteId, pedido.getClienteId().get().getClienteId());
        assertFalse(pedido.getPedidoId().isPresent());
        assertEquals(1, pedido.getItens().size());
    }

    @Test
    void convert_DeveLancarConstraintViolationExceptionQuandoAlterarPedidoRequestForNulo() {
        // Arrange
        AlterarItemPedidoRequestConverter itemPedidoRequestConverter = new AlterarItemPedidoRequestConverter();
        AlterarPedidoRequestConverter converter = new AlterarPedidoRequestConverter(itemPedidoRequestConverter);

        // Act & Assert
        assertThrows(ConstraintViolationException.class, () -> converter.convert(null));
    }
}
