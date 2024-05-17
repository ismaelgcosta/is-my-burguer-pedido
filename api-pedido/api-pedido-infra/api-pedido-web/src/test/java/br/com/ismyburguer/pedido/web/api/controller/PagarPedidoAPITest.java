package br.com.ismyburguer.pedido.web.api.controller;

import br.com.ismyburguer.pedido.adapter.interfaces.in.PagarPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PagarPedidoAPITest {

    @Mock
    PagarPedidoUseCase useCase;

    @InjectMocks
    PagarPedidoAPI pagarPedidoAPI;

    @Test
    void pagarPedido_DeveRetornarMensagemQuandoPedidoExistir() {
        // Arrange
        String pedidoId = UUID.randomUUID().toString();
        String mensagemEsperada = "Pedido pago com sucesso";

        // Act
        when(useCase.pagar(any(Pedido.PedidoId.class))).thenReturn(mensagemEsperada);
        String mensagemAtual = pagarPedidoAPI.pagarPedido(pedidoId);

        // Assert
        assertEquals(mensagemEsperada, mensagemAtual);
    }

    @Test
    void pagarPedido_DeveLancarExcecaoQuandoPedidoInexistente() {
        // Arrange
        String pedidoId = UUID.randomUUID().toString();
        doThrow(ResponseStatusException.class).when(useCase).pagar(any(Pedido.PedidoId.class));

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> pagarPedidoAPI.pagarPedido(pedidoId));
    }

}
