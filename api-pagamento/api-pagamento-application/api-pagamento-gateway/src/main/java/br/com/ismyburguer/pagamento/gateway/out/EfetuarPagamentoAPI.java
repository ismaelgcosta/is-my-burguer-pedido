package br.com.ismyburguer.pagamento.gateway.out;

import br.com.ismyburguer.pagamento.entity.Pagamento;

import java.util.UUID;

public interface EfetuarPagamentoAPI {

    void pagar(Pagamento pagamento);

}
