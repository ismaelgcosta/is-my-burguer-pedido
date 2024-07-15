package br.com.ismyburguer.pagamento.adapter.usecase.impl;

import br.com.ismyburguer.core.usecase.UseCase;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.EfetuarPagamentoUseCase;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.gateway.out.EfetuarPagamentoAPI;
import jakarta.validation.Valid;

import java.util.UUID;

@UseCase
public class EfetuarPagamentoUseCaseImpl implements EfetuarPagamentoUseCase {
    private final EfetuarPagamentoAPI efetuarPagamentoAPI;
    public EfetuarPagamentoUseCaseImpl(EfetuarPagamentoAPI efetuarPagamentoAPI) {
        this.efetuarPagamentoAPI = efetuarPagamentoAPI;
    }

    @Override
    public void pagar(@Valid Pagamento pagamento) {
        efetuarPagamentoAPI.pagar(pagamento);
    }
}
