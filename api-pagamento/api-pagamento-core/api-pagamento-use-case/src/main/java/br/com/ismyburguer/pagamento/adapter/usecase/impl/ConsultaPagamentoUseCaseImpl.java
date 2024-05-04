package br.com.ismyburguer.pagamento.adapter.usecase.impl;

import br.com.ismyburguer.core.usecase.UseCase;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.ConsultarPagamentoUseCase;
import br.com.ismyburguer.pagamento.gateway.out.ConsultarPagamentoAPI;
import org.hibernate.validator.constraints.UUID;

@UseCase
public class ConsultaPagamentoUseCaseImpl implements ConsultarPagamentoUseCase {
    private final ConsultarPagamentoAPI repository;
    public ConsultaPagamentoUseCaseImpl(ConsultarPagamentoAPI repository) {
        this.repository = repository;
    }

    @Override
    public Pagamento consultar(@UUID String pagamentoId) {
        return repository.consultar(pagamentoId);
    }
}
