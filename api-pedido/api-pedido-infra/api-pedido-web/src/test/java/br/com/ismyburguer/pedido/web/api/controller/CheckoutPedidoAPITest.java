package br.com.ismyburguer.pedido.web.api.controller;
import br.com.ismyburguer.pedido.adapter.interfaces.in.FecharPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckoutPedidoAPITest {

    @Mock
    FecharPedidoUseCase fecharPedidoUseCase;

    @InjectMocks
    CheckoutPedidoAPI checkoutPedidoAPI;

    @Test
    void fecharPedido_DeveFecharPedidoQuandoPedidoValido() {
        // Arrange
        String pedidoId = UUID.randomUUID().toString();

        // Act
        checkoutPedidoAPI.fecharPedido(pedidoId);

        // Assert
        verify(fecharPedidoUseCase, times(1)).fechar(new Pedido.PedidoId(pedidoId));
    }

}
