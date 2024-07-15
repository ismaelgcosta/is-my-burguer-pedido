package br.com.ismyburguer.pagamento.adapter.usecase.impl;

import br.com.ismyburguer.core.usecase.UseCase;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.CancelarPagamentoUseCase;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.EfetuarPagamentoUseCase;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.gateway.out.CancelarPagamentoAPI;
import br.com.ismyburguer.pagamento.gateway.out.EfetuarPagamentoAPI;
import jakarta.validation.Valid;

@UseCase
public class CancelarPagamentoUseCaseImpl implements CancelarPagamentoUseCase {
    private final CancelarPagamentoAPI cancelarPagamentoAPI;

    public CancelarPagamentoUseCaseImpl(CancelarPagamentoAPI cancelarPagamentoAPI) {
        this.cancelarPagamentoAPI = cancelarPagamentoAPI;
    }

    @Override
    public void cancelar(Pagamento.PedidoId pedidoId) {
        cancelarPagamentoAPI.cancelar(pedidoId.getPedidoId().toString());
    }
}
