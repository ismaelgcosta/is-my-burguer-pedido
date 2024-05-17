package br.com.ismyburguer.pedido.web.api.converter;
import br.com.ismyburguer.pedido.entity.ItemPedido;
import br.com.ismyburguer.pedido.web.api.request.CadastrarItemPedidoRequest;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CadastrarItemPedidoRequestConverterTest {

    @Test
    void convert_DeveConverterCadastrarItemPedidoRequestParaItemPedido() {
        // Arrange
        UUID produtoId = UUID.randomUUID();
        BigDecimal preco = BigDecimal.valueOf(5);
        CadastrarItemPedidoRequest request = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(CadastrarItemPedidoRequest.class);
        request.setProdutoId(produtoId.toString());
        request.setQuantidade(15);
        request.setPreco(preco);
        CadastrarItemPedidoRequestConverter converter = new CadastrarItemPedidoRequestConverter();

        // Act
        ItemPedido itemPedido = converter.convert(request);

        // Assert
        assertNotNull(itemPedido);
        assertEquals(produtoId, itemPedido.getProdutoId().getProdutoId());
        assertEquals(request.getQuantidade(), itemPedido.getQuantidade().getValor());
        assertEquals(preco, itemPedido.getPreco().getValor());
    }

    @Test
    void convert_DeveLancarConstraintViolationExceptionQuandoCadastrarItemPedidoRequestForNulo() {
        // Arrange
        CadastrarItemPedidoRequestConverter converter = new CadastrarItemPedidoRequestConverter();

        // Act & Assert
        assertThrows(ConstraintViolationException.class, () -> converter.convert(null));
    }
}
