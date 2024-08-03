package br.com.ismyburguer.pedido.entity;

import br.com.ismyburguer.core.exception.BusinessException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PedidoTest {

    @Test
    public void criarPedido_ComConstrutor_ComParametros_DeveCriarPedidoCorretamente() {
        // Arrange
        Pedido.PedidoId pedidoId = new Pedido.PedidoId(UUID.randomUUID());
        Pedido.ClienteId clienteId = new Pedido.ClienteId(UUID.randomUUID());
        Pedido.StatusPedido status = Pedido.StatusPedido.AGUARDANDO_PAGAMENTO;
        ItemPedido itemPedido = new ItemPedido(new ItemPedido.ProdutoId(UUID.randomUUID()), new ItemPedido.Quantidade(1), new ItemPedido.Preco(BigDecimal.valueOf(10.0)));

        // Act
        Pedido pedido = new Pedido(clienteId, status, List.of(itemPedido));

        // Assert
        assertEquals(clienteId, pedido.getClienteId().orElse(null));
        assertEquals(status, pedido.getStatusPedido());
        assertEquals(BigDecimal.valueOf(10.0), pedido.getTotal().getValor());
    }

    @Test
    public void alterarStatus_DeveAlterarStatusCorretamente() {
        // Arrange
        Pedido pedido = new Pedido(
                new Pedido.PedidoId(UUID.randomUUID()),
                List.of(new ItemPedido(new ItemPedido.ProdutoId(UUID.randomUUID()), new ItemPedido.Quantidade(1), new ItemPedido.Preco(BigDecimal.valueOf(10.0))))
        );

        // Act
        pedido.alterarStatus(Pedido.StatusPedido.FECHADO);

        // Assert
        assertEquals(Pedido.StatusPedido.FECHADO, pedido.getStatusPedido());
    }

    @Test
    public void alterarStatus_DeveLancarBusinessException_QuandoStatusNaoPermitido() {
        // Arrange
        Pedido pedido = new Pedido(
                new Pedido.PedidoId(UUID.randomUUID().toString()),
                List.of(new ItemPedido(new ItemPedido.ProdutoId(UUID.randomUUID()), new ItemPedido.Quantidade(1), new ItemPedido.Preco(BigDecimal.valueOf(10.0))))
        );

        // Act & Assert
        assertThrows(BusinessException.class, () -> pedido.alterarStatus(Pedido.StatusPedido.PAGO));
        assertThrows(BusinessException.class, () -> pedido.alterarStatus(Pedido.StatusPedido.PRONTO));
        assertThrows(BusinessException.class, () -> pedido.alterarStatus(Pedido.StatusPedido.EM_PREPARACAO));
        assertThrows(BusinessException.class, () -> pedido.alterarStatus(Pedido.StatusPedido.AGUARDANDO_PAGAMENTO));
        assertThrows(BusinessException.class, () -> pedido.alterarStatus(Pedido.StatusPedido.AGUARDANDO_CONFIRMACAO_PAGAMENTO));
        pedido.alterarStatus(Pedido.StatusPedido.CANCELADO);
        assertThrows(BusinessException.class, () -> pedido.alterarStatus(Pedido.StatusPedido.FINALIZADO));
        assertThrows(BusinessException.class, () -> pedido.alterarStatus(Pedido.StatusPedido.FECHADO));
    }

    @Test
    public void getTotal_DeveCalcularTotalCorretamente() {
        // Arrange
        ItemPedido item1 = new ItemPedido(new ItemPedido.ProdutoId(UUID.randomUUID()), new ItemPedido.Quantidade(1), new ItemPedido.Preco(BigDecimal.valueOf(10.0)));
        ItemPedido item2 = new ItemPedido(new ItemPedido.ProdutoId(UUID.randomUUID()), new ItemPedido.Quantidade(1), new ItemPedido.Preco(BigDecimal.valueOf(20.0)));
        Pedido pedido = new Pedido(
                new Pedido.PedidoId(UUID.randomUUID()),
                List.of(item1, item2)
        );

        // Act
        Pedido.Total total = pedido.getTotal();

        // Assert
        assertEquals(BigDecimal.valueOf(30.0), total.getValor());
    }

    @Test
    public void getClienteId_DeveRetornarClienteId_QuandoClienteIdNaoForNulo() {
        // Arrange
        Pedido.ClienteId clienteId = new Pedido.ClienteId(UUID.randomUUID());
        Pedido pedido = new Pedido(
                clienteId,
                List.of(new ItemPedido(new ItemPedido.ProdutoId(UUID.randomUUID()), new ItemPedido.Quantidade(1), new ItemPedido.Preco(BigDecimal.valueOf(10.0))))
        );

        // Act
        Optional<Pedido.ClienteId> result = pedido.getClienteId();

        // Assert
        assertTrue(result.isPresent());
        assertEquals(clienteId, result.get());
    }

    @Test
    public void getClienteId_DeveRetornarEmptyOptional_QuandoClienteIdForNulo() {
        // Arrange
        Pedido pedido = new Pedido(
                new Pedido.PedidoId(UUID.randomUUID()),
                List.of(new ItemPedido(new ItemPedido.ProdutoId(UUID.randomUUID()), new ItemPedido.Quantidade(1), new ItemPedido.Preco(BigDecimal.valueOf(10.0))))
        );

        // Act
        Optional<Pedido.ClienteId> result = pedido.getClienteId();

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    public void getPedidoId_DeveRetornarPedidoId_QuandoPedidoIdNaoForNulo() {
        // Arrange
        Pedido.PedidoId pedidoId = new Pedido.PedidoId(UUID.randomUUID());
        Pedido pedido = new Pedido(
                pedidoId,
                List.of(new ItemPedido(new ItemPedido.ProdutoId(UUID.randomUUID()), new ItemPedido.Quantidade(1), new ItemPedido.Preco(BigDecimal.valueOf(10.0))))
        );

        // Act
        Optional<Pedido.PedidoId> result = pedido.getPedidoId();

        // Assert
        assertTrue(result.isPresent());
        assertEquals(pedidoId, result.get());
    }

    @Test
    public void getPedidoId_DeveRetornarEmptyOptional_QuandoPedidoIdForNulo() {
        // Arrange
        Pedido pedido = new Pedido(
                List.of(new ItemPedido(new ItemPedido.ProdutoId(UUID.randomUUID()), new ItemPedido.Quantidade(1), new ItemPedido.Preco(BigDecimal.valueOf(10.0))))
        );

        // Act
        Optional<Pedido.PedidoId> result = pedido.getPedidoId();

        // Assert
        assertFalse(result.isPresent());
    }
}
