package br.com.ismyburguer.pedido.usecase.impl;

import br.com.ismyburguer.core.exception.EntityNotFoundException;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.gateway.out.ConsultarPedidoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConsultarPedidoUseCaseImplTest {

    @Mock
    private ConsultarPedidoRepository repository;

    @InjectMocks
    private ConsultarPedidoUseCaseImpl useCase;

    @Test
    public void deveBuscarPedidoPorIdComSucesso() {
        // Dado
        UUID pedidoId = UUID.randomUUID();
        Pedido pedido = new Pedido(); // Crie um pedido válido aqui
        when(repository.obterPeloPedidoId(pedidoId)).thenReturn(Optional.of(pedido));

        // Quando
        Pedido result = useCase.buscarPorId(new Pedido.PedidoId(pedidoId));

        // Então
        assertNotNull(result);
        assertEquals(pedido, result);
        verify(repository, times(1)).obterPeloPedidoId(pedidoId);
    }

    @Test
    public void deveLancarEntityNotFoundExceptionQuandoPedidoNaoEncontrado() {
        // Dado
        UUID pedidoId = UUID.randomUUID();
        when(repository.obterPeloPedidoId(any())).thenReturn(Optional.empty());

        // Quando e Então
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            useCase.buscarPorId(new Pedido.PedidoId(pedidoId));
        });
        assertEquals("Pedido não foi encontrado", exception.getMessage());
        verify(repository, times(1)).obterPeloPedidoId(pedidoId);
    }
}
