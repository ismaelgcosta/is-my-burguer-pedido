package br.com.ismyburguer.pedido.web.api.controller;

import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.web.api.request.PedidoRequest;
import br.com.ismyburguer.pedido.web.api.request.StatusPedidoRequest;
import br.com.ismyburguer.pedido.web.sqs.listener.StatusPedidoAPI;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatusPedidoAPITest {

    @Mock
    AlterarStatusPedidoUseCase useCase;

    StatusPedidoAPI statusPedidoAPI;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        statusPedidoAPI = new StatusPedidoAPI(
                objectMapper,
                useCase
        );
    }

    @Test
    void alterarStatusPedido_DeveAlterarStatusQuandoPedidoExistirEStatusValido() {
        // Arrange
        UUID pedidoId = UUID.randomUUID();
        String status = "ABERTO";
        PedidoRequest pedidoRequest = new PedidoRequest(pedidoId, StatusPedidoRequest.valueOf(status));

        // Act
        assertDoesNotThrow(() -> statusPedidoAPI.alterarStatusPedido(new ObjectMapper().writeValueAsString(pedidoRequest)));

        // Assert
        verify(useCase, times(1)).alterar(any(Pedido.PedidoId.class), any(Pedido.StatusPedido.class));
    }

    @Test
    void alterarStatusPedido_DeveLancarExcecaoQuandoPedidoInexistente() {
        // Arrange
        UUID pedidoId = UUID.randomUUID();
        String status = "ABERTO";
        PedidoRequest pedidoRequest = new PedidoRequest(pedidoId, StatusPedidoRequest.valueOf(status));
        doThrow(ResponseStatusException.class).when(useCase).alterar(any(Pedido.PedidoId.class), any(Pedido.StatusPedido.class));

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> statusPedidoAPI.alterarStatusPedido(new ObjectMapper().writeValueAsString(pedidoRequest)));
    }

}
