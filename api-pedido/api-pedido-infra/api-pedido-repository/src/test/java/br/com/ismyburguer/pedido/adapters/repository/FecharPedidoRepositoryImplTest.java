package br.com.ismyburguer.pedido.adapters.repository;

import br.com.ismyburguer.core.exception.EntityNotFoundException;
import br.com.ismyburguer.pedido.adapters.entity.PedidoModel;
import br.com.ismyburguer.pedido.adapters.entity.StatusPedidoEntity;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FecharPedidoRepositoryImplTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private FecharPedidoRepositoryImpl fecharPedidoRepository;

    private PedidoModel pedidoModel;
    private UUID pedidoId;

    @BeforeEach
    public void setUp() {
        pedidoModel = EnhancedRandom.random(PedidoModel.class);
        pedidoId = UUID.randomUUID();
    }

    @Test
    public void fecharPedido_DeveFecharPedidoQuandoEncontrado() {
        // Arrange
        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(pedidoModel));

        // Act
        fecharPedidoRepository.fecharPedido(pedidoId);

        // Assert
        verify(pedidoRepository, times(1)).findById(pedidoId);
        verify(pedidoRepository, times(1)).save(pedidoModel);
        assertEquals(StatusPedidoEntity.FECHADO, pedidoModel.getStatusPedido());
    }

    @Test
    public void fecharPedido_DeveLancarExcecaoQuandoNaoEncontrado() {
        // Arrange
        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> fecharPedidoRepository.fecharPedido(pedidoId));

        verify(pedidoRepository, times(1)).findById(pedidoId);
        verify(pedidoRepository, times(0)).save(any(PedidoModel.class));
    }
}
