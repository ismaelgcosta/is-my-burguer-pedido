package br.com.ismyburguer.pedido.usecase.impl;

import br.com.ismyburguer.core.exception.BusinessException;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.ConsultarPagamentoUseCase;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.EfetuarPagamentoUseCase;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import br.com.ismyburguer.pedido.adapter.interfaces.in.ConsultarPedidoUseCase;
import br.com.ismyburguer.pedido.entity.ItemPedido;
import br.com.ismyburguer.pedido.entity.Pedido;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PagarPedidoUseCaseImplTest {

    @Mock
    private EfetuarPagamentoUseCase pagamentoUseCase;

    @Mock
    private ConsultarPedidoUseCase pedidoUseCase;

    @Mock
    private ConsultarPagamentoUseCase consultarPagamentoUseCase;

    @Mock
    private AlterarStatusPedidoUseCase alterarStatusPedidoUseCase;

    @InjectMocks
    private PagarPedidoUseCaseImpl useCase;

    @Test
    public void devePagarPedidoComSucesso() {
        // Dado
        Pedido.PedidoId pedidoId = new Pedido.PedidoId(UUID.randomUUID().toString());
        Pedido pedido = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Pedido.class);
        pedido.getItens().add(new ItemPedido(new ItemPedido.ProdutoId(UUID.randomUUID()), new ItemPedido.Quantidade(1), new ItemPedido.Preco(BigDecimal.TEN)));
        when(pedidoUseCase.buscarPorId(pedidoId)).thenReturn(pedido);

        Pagamento pagamentoMock = spy(new Pagamento(
                new Pagamento.PedidoId(pedidoId.getPedidoId()), new Pagamento.Total(BigDecimal.ONE)
        ));
        pagamentoMock.pago();
        pagamentoMock.setQrCode("qr-code");
        when(pagamentoUseCase.pagar(any())).thenReturn(UUID.randomUUID());
        when(consultarPagamentoUseCase.consultar(any())).thenReturn(pagamentoMock);

        // Quando
        String resultado = useCase.pagar(pedidoId);

        // Então
        assertNotNull(resultado);
        verify(alterarStatusPedidoUseCase, times(1)).alterar(pedidoId, Pedido.StatusPedido.AGUARDANDO_PAGAMENTO);
        verify(pagamentoUseCase, times(1)).pagar(any());
        verify(consultarPagamentoUseCase, times(1)).consultar(anyString());
    }

    @Test
    public void deveLancarExcecaoAoPagarPedidoComPagamentoNaoAutorizado() {
        // Dado
        Pedido.PedidoId pedidoId = new Pedido.PedidoId(UUID.randomUUID().toString());
        Pedido pedido = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Pedido.class);
        when(pedidoUseCase.buscarPorId(pedidoId)).thenReturn(pedido);

        Pagamento pagamentoMock = spy(new Pagamento(
                new Pagamento.PedidoId(pedidoId.getPedidoId()), new Pagamento.Total(BigDecimal.ONE)
        ));

        // Quando e Então
        assertThrows(ConstraintViolationException.class, () -> useCase.pagar(pedidoId));
        verify(alterarStatusPedidoUseCase, times(1)).alterar(pedidoId, Pedido.StatusPedido.AGUARDANDO_PAGAMENTO);
        verify(pagamentoUseCase, times(0)).pagar(any());
        verify(consultarPagamentoUseCase, times(0)).consultar(anyString());
    }
}
