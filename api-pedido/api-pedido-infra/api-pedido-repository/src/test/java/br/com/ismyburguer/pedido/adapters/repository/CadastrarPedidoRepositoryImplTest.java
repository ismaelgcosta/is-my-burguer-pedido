package br.com.ismyburguer.pedido.adapters.repository;

import br.com.ismyburguer.pedido.adapters.converter.PedidoToPedidoModelConverter;
import br.com.ismyburguer.pedido.adapters.entity.PedidoModel;
import br.com.ismyburguer.pedido.entity.Pedido;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CadastrarPedidoRepositoryImplTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private PedidoToPedidoModelConverter converter;

    @InjectMocks
    private CadastrarPedidoRepositoryImpl cadastrarPedidoRepository;

    private Pedido pedido;
    private PedidoModel pedidoModel;

    @BeforeEach
    public void setUp() {
        pedido = EnhancedRandom.random(Pedido.class);
        pedidoModel = EnhancedRandom.random(PedidoModel.class);
    }

    @Test
    public void salvarPedido_DeveSalvarPedidoERetornarUUID() {
        // Arrange
        when(converter.convert(pedido)).thenReturn(pedidoModel);
        when(pedidoRepository.save(pedidoModel)).thenReturn(pedidoModel);

        // Act
        UUID result = cadastrarPedidoRepository.salvarPedido(pedido);

        // Assert
        assertEquals(pedidoModel.getPedidoId(), result);
        verify(pedidoRepository, times(1)).save(pedidoModel);
    }
}
