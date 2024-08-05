package br.com.ismyburguer.pedido.usecase.impl;

import br.com.ismyburguer.core.usecase.UseCase;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.CancelarPagamentoUseCase;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import br.com.ismyburguer.pedido.adapter.interfaces.in.ConsultarPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.gateway.out.AlterarStatusPedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlterarStatusPedidoUseCaseImplTest {

    @Mock
    private AlterarStatusPedidoRepository repository;
    @Mock
    private ConsultarPedidoUseCase consultarPedidoUseCase;
    @Mock
    private CancelarPagamentoUseCase cancelarPagamentoUseCase;

    private AlterarStatusPedidoUseCase alterarStatusPedidoUseCase;

    @BeforeEach
    public void setup() {
        alterarStatusPedidoUseCase = new AlterarStatusPedidoUseCaseImpl(repository, consultarPedidoUseCase, cancelarPagamentoUseCase);
    }

    @Test
    public void deveAlterarStatusDoPedidoParaCanceladoEChamarCancelarPagamento() {
        String pedidoId = UUID.randomUUID().toString();
        Pedido.StatusPedido novoStatus = Pedido.StatusPedido.CANCELADO;
        Pedido pedidoMock = mock(Pedido.class);

        when(consultarPedidoUseCase.buscarPorId(new Pedido.PedidoId(pedidoId))).thenReturn(pedidoMock);

        alterarStatusPedidoUseCase.alterar(new Pedido.PedidoId(pedidoId), novoStatus);

        verify(pedidoMock).alterarStatus(novoStatus);
        verify(repository).alterar(new Pedido.PedidoId(pedidoId), novoStatus);
        verify(cancelarPagamentoUseCase).cancelar(new Pagamento.PedidoId(UUID.fromString(pedidoId)));
    }

    @Test
    public void deveAlterarStatusDoPedidoParaQualquerStatusSemChamarCancelarPagamento() {
        String pedidoId = UUID.randomUUID().toString();
        Pedido.StatusPedido novoStatus = Pedido.StatusPedido.PAGO;
        Pedido pedidoMock = mock(Pedido.class);

        when(consultarPedidoUseCase.buscarPorId(new Pedido.PedidoId(pedidoId))).thenReturn(pedidoMock);

        alterarStatusPedidoUseCase.alterar(new Pedido.PedidoId(pedidoId), novoStatus);

        verify(pedidoMock, times(2)).alterarStatus(novoStatus);
        verify(repository).alterar(new Pedido.PedidoId(pedidoId), novoStatus);
        verify(cancelarPagamentoUseCase, never()).cancelar(any());
    }
}
