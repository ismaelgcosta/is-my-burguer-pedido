package br.com.ismyburguer.pedido.adapters.repository;
import br.com.ismyburguer.core.exception.EntityNotFoundException;
import br.com.ismyburguer.pedido.adapters.converter.PedidoToPedidoModelConverter;
import br.com.ismyburguer.pedido.adapters.entity.PedidoModel;
import br.com.ismyburguer.pedido.adapters.entity.StatusPedidoEntity;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.gateway.out.AlterarPedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

import io.github.benas.randombeans.api.EnhancedRandom;

@ExtendWith(MockitoExtension.class)
public class AlterarPedidoRepositoryImplTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private PedidoToPedidoModelConverter converter;

    @InjectMocks
    private AlterarPedidoRepositoryImpl alterarPedidoRepository;

    private PedidoModel pedidoModel;
    private Pedido pedido;

    @BeforeEach
    public void setUp() {
        pedidoModel = EnhancedRandom.random(PedidoModel.class);
        pedido = EnhancedRandom.random(Pedido.class);
    }

    @Test
    public void alterarPedido_PedidoExistente_AlteraComSucesso() {
        // Arrange
        String pedidoId = UUID.randomUUID().toString();
        UUID uuid = UUID.fromString(pedidoId);

        when(pedidoRepository.findById(uuid)).thenReturn(Optional.of(pedidoModel));
        when(converter.convert(any(Pedido.class))).thenReturn(pedidoModel);

        // Act
        alterarPedidoRepository.alterarPedido(pedidoId, pedido);

        // Assert
        verify(pedidoRepository, times(2)).save(any(PedidoModel.class));
    }

    @Test
    public void alterarPedido_PedidoNaoExistente_LancaExcecao() {
        // Arrange
        String pedidoId = UUID.randomUUID().toString();
        UUID uuid = UUID.fromString(pedidoId);

        when(pedidoRepository.findById(uuid)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            alterarPedidoRepository.alterarPedido(pedidoId, pedido);
        });

        verify(pedidoRepository, never()).save(any(PedidoModel.class));
    }
}
