package br.com.ismyburguer.pedido.usecase.impl;

import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RetirarPedidoUseCaseImplTest {

    @Mock
    private AlterarStatusPedidoUseCase pedidoUseCase;

    @InjectMocks
    private RetirarPedidoUseCaseImpl useCase;

    @Test
    public void deveRetirarPedidoComSucesso() {
        // Dado
        Pedido.PedidoId pedidoId = new Pedido.PedidoId(UUID.randomUUID());

        // Quando
        useCase.retirar(pedidoId);

        // Então
        verify(pedidoUseCase, times(1)).alterar(pedidoId, Pedido.StatusPedido.FINALIZADO);
    }
}
