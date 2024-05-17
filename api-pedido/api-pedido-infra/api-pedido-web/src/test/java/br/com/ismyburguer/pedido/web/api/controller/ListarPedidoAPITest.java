package br.com.ismyburguer.pedido.web.api.controller;
import br.com.ismyburguer.pedido.adapter.interfaces.in.ListarPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.web.api.converter.ListarPedidoConverter;
import br.com.ismyburguer.pedido.web.api.response.ListarPedidoResponse;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarPedidoAPITest {

    @Mock
    ListarPedidoUseCase listarPedidoUseCase;

    @Mock
    ListarPedidoConverter listarPedidoConverter;

    @InjectMocks
    ListarPedidoAPI listarPedidoAPI;

    @Test
    void listar_DeveRetornarListaDePedidosQuandoHouverPedidos() {
        // Arrange
        List<Pedido> pedidos = EnhancedRandomBuilder.aNewEnhancedRandom().objects(Pedido.class, 3).toList();
        List<ListarPedidoResponse> pedidosConvertidos = EnhancedRandomBuilder.aNewEnhancedRandom().objects(ListarPedidoResponse.class, 3).toList();

        // Act
        when(listarPedidoUseCase.listar()).thenReturn(pedidos);
        when(listarPedidoConverter.convert(any())).thenReturn(pedidosConvertidos.get(0), pedidosConvertidos.get(1), pedidosConvertidos.get(2));

        List<ListarPedidoResponse> resposta = listarPedidoAPI.listar();

        // Assert
        assertEquals(pedidosConvertidos.size(), resposta.size());
        assertEquals(pedidosConvertidos, resposta);
    }

    @Test
    void listar_DeveRetornarListaVaziaQuandoNaoHouverPedidos() {
        // Arrange
        List<Pedido> pedidos = Collections.emptyList();

        // Act
        when(listarPedidoUseCase.listar()).thenReturn(pedidos);

        List<ListarPedidoResponse> resposta = listarPedidoAPI.listar();

        // Assert
        assertEquals(0, resposta.size());
    }

}
