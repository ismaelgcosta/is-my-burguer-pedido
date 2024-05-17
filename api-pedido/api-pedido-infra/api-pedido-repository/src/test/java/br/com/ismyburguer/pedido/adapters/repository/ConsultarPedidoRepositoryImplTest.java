package br.com.ismyburguer.pedido.adapters.repository;
import br.com.ismyburguer.pedido.adapters.converter.PedidoModelToPedidoConverter;
import br.com.ismyburguer.pedido.adapters.entity.PedidoModel;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.gateway.out.ConsultarPedidoRepository;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConsultarPedidoRepositoryImplTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private PedidoModelToPedidoConverter converter;

    @InjectMocks
    private ConsultarPedidoRepositoryImpl consultarPedidoRepository;

    private Pedido pedido;
    private PedidoModel pedidoModel;
    private UUID pedidoId;

    @BeforeEach
    public void setUp() {
        pedido = EnhancedRandom.random(Pedido.class);
        pedidoModel = EnhancedRandom.random(PedidoModel.class);
        pedidoId = UUID.randomUUID();
    }

    @Test
    public void obterPeloPedidoId_DeveRetornarPedidoQuandoEncontrado() {
        // Arrange
        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(pedidoModel));
        when(converter.convert(pedidoModel)).thenReturn(pedido);

        // Act
        Optional<Pedido> result = consultarPedidoRepository.obterPeloPedidoId(pedidoId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(pedido, result.get());
        verify(pedidoRepository, times(1)).findById(pedidoId);
        verify(converter, times(1)).convert(pedidoModel);
    }

    @Test
    public void obterPeloPedidoId_DeveRetornarVazioQuandoNaoEncontrado() {
        // Arrange
        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.empty());

        // Act
        Optional<Pedido> result = consultarPedidoRepository.obterPeloPedidoId(pedidoId);

        // Assert
        assertTrue(result.isEmpty());
        verify(pedidoRepository, times(1)).findById(pedidoId);
        verify(converter, times(0)).convert(any(PedidoModel.class));
    }
}
