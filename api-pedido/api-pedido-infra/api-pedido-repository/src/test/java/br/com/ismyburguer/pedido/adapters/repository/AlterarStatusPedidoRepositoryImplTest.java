package br.com.ismyburguer.pedido.adapters.repository;
import br.com.ismyburguer.core.exception.EntityNotFoundException;
import br.com.ismyburguer.pedido.adapters.entity.PedidoModel;
import br.com.ismyburguer.pedido.adapters.entity.StatusPedidoEntity;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.gateway.out.AlterarStatusPedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

import io.github.benas.randombeans.api.EnhancedRandom;

@ExtendWith(MockitoExtension.class)
public class AlterarStatusPedidoRepositoryImplTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private AlterarStatusPedidoRepositoryImpl alterarStatusPedidoRepository;

    private PedidoModel pedidoModel;

    @BeforeEach
    public void setUp() {
        pedidoModel = EnhancedRandom.random(PedidoModel.class);
    }

    @Test
    public void alterarStatusPedido_PedidoExistente_AlteraComSucesso() {
        // Arrange
        Pedido.PedidoId pedidoId = new Pedido.PedidoId(UUID.randomUUID());
        Pedido.StatusPedido novoStatus = Pedido.StatusPedido.FECHADO;

        when(pedidoRepository.findById(pedidoId.getPedidoId())).thenReturn(Optional.of(pedidoModel));

        // Act
        alterarStatusPedidoRepository.alterar(pedidoId, novoStatus);

        // Assert
        assertEquals(StatusPedidoEntity.FECHADO, pedidoModel.getStatusPedido());
        verify(pedidoRepository, times(1)).save(any(PedidoModel.class));
    }

    @Test
    public void alterarStatusPedido_PedidoNaoExistente_LancaExcecao() {
        // Arrange
        Pedido.PedidoId pedidoId = new Pedido.PedidoId(UUID.randomUUID());
        Pedido.StatusPedido novoStatus = Pedido.StatusPedido.FECHADO;

        when(pedidoRepository.findById(pedidoId.getPedidoId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            alterarStatusPedidoRepository.alterar(pedidoId, novoStatus);
        });

        verify(pedidoRepository, never()).save(any(PedidoModel.class));
    }
}
