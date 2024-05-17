package br.com.ismyburguer.pedido.usecase.impl;
import br.com.ismyburguer.core.exception.EntityNotFoundException;
import br.com.ismyburguer.pedido.adapter.interfaces.in.ConsultarPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.gateway.out.AlterarStatusPedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AlterarStatusPedidoUseCaseImplTest {

    @Mock
    private AlterarStatusPedidoRepository repository;

    @Mock
    private ConsultarPedidoUseCase consultarPedidoUseCase;

    @InjectMocks
    private AlterarStatusPedidoUseCaseImpl useCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void deveAlterarStatusDoPedidoComSucesso() {
        // Dado
        Pedido.PedidoId pedidoId = new Pedido.PedidoId(UUID.randomUUID());
        Pedido.StatusPedido novoStatus = Pedido.StatusPedido.EM_PREPARACAO;
        Pedido pedido = new Pedido(); // Criar um pedido válido para o teste
        pedido.alterarStatus(Pedido.StatusPedido.RECEBIDO);

        // Quando
        when(consultarPedidoUseCase.buscarPorId(pedidoId)).thenReturn(pedido);
        useCase.alterar(pedidoId, novoStatus);

        // Então
        verify(repository, times(1)).alterar(pedidoId, novoStatus);
    }

    @Test
    public void deveLancarExcecaoQuandoPedidoNaoEncontrado() {
        // Dado
        Pedido.PedidoId pedidoId = new Pedido.PedidoId(UUID.randomUUID());
        Pedido.StatusPedido novoStatus = Pedido.StatusPedido.EM_PREPARACAO;

        // Quando
        when(consultarPedidoUseCase.buscarPorId(pedidoId)).thenThrow(EntityNotFoundException.class);

        // Então
        assertThrows(EntityNotFoundException.class, () -> useCase.alterar(pedidoId, novoStatus));
        verifyNoInteractions(repository);
    }
}
