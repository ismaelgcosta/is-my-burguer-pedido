package br.com.ismyburguer.pedido.web.api.controller;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatusPedidoAPITest {

    @Mock
    AlterarStatusPedidoUseCase useCase;

    @InjectMocks
    StatusPedidoAPI statusPedidoAPI;

    @Test
    void alterarStatusPedido_DeveAlterarStatusQuandoPedidoExistirEStatusValido() {
        // Arrange
        String pedidoId = UUID.randomUUID().toString();
        String status = "ABERTO";

        // Act
        assertDoesNotThrow(() -> statusPedidoAPI.alterarStatusPedido(pedidoId, status));

        // Assert
        verify(useCase, times(1)).alterar(any(Pedido.PedidoId.class), any(Pedido.StatusPedido.class));
    }

    @Test
    void alterarStatusPedido_DeveLancarExcecaoQuandoPedidoInexistente() {
        // Arrange
        String pedidoId = UUID.randomUUID().toString();
        String status = "ABERTO";
        doThrow(ResponseStatusException.class).when(useCase).alterar(any(Pedido.PedidoId.class), any(Pedido.StatusPedido.class));

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> statusPedidoAPI.alterarStatusPedido(pedidoId, status));
    }

}
