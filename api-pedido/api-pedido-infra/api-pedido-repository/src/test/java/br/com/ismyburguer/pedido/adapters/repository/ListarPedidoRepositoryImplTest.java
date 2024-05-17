package br.com.ismyburguer.pedido.adapters.repository;
import br.com.ismyburguer.pedido.adapters.converter.PedidoModelToPedidoConverter;
import br.com.ismyburguer.pedido.adapters.entity.PedidoModel;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.gateway.out.ListarPedidoRepository;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListarPedidoRepositoryImplTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private PedidoModelToPedidoConverter converter;

    @InjectMocks
    private ListarPedidoRepositoryImpl listarPedidoRepository;

    private List<PedidoModel> pedidoModels;
    private List<Pedido> pedidos;

    @BeforeEach
    public void setUp() {
        pedidoModels = EnhancedRandom.randomListOf(10, PedidoModel.class);
        pedidos = EnhancedRandom.randomListOf(10, Pedido.class);

        for (int i = 0; i < pedidoModels.size(); i++) {
            when(converter.convert(pedidoModels.get(i))).thenReturn(pedidos.get(i));
        }
    }

    @Test
    public void listar_DeveRetornarListaDePedidos() {
        // Arrange
        when(pedidoRepository.findAll()).thenReturn(pedidoModels);

        // Act
        List<Pedido> resultado = listarPedidoRepository.listar();

        // Assert
        verify(pedidoRepository, times(1)).findAll();
        verify(converter, times(pedidoModels.size())).convert(any(PedidoModel.class));
        assertEquals(pedidos, resultado);
    }
}
