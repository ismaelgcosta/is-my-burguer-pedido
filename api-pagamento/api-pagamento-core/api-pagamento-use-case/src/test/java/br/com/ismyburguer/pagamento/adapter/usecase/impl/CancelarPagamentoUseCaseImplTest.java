package br.com.ismyburguer.pagamento.adapter.usecase.impl;

import static org.junit.jupiter.api.Assertions.*;
import br.com.ismyburguer.core.usecase.UseCase;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.CancelarPagamentoUseCase;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.gateway.out.CancelarPagamentoAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CancelarPagamentoUseCaseImplTest {

    @Mock
    private CancelarPagamentoAPI cancelarPagamentoAPI;

    private CancelarPagamentoUseCase cancelarPagamentoUseCase;

    @BeforeEach
    public void setup() {
        cancelarPagamentoUseCase = new CancelarPagamentoUseCaseImpl(cancelarPagamentoAPI);
    }

    @Test
    public void deveChamarCancelarPagamentoAPI() {
        UUID pedidoId = UUID.randomUUID();
        Pagamento.PedidoId pagamentoPedidoId = new Pagamento.PedidoId(pedidoId);

        cancelarPagamentoUseCase.cancelar(pagamentoPedidoId);

        verify(cancelarPagamentoAPI).cancelar(pedidoId.toString());
    }
}
