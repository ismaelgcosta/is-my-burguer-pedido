package br.com.ismyburguer.pagamento.adapter.client;

import br.com.ismyburguer.core.adapter.in.WebAdapter;
import br.com.ismyburguer.core.adapter.out.FeignClientAPI;
import br.com.ismyburguer.pagamento.adapter.converter.PagamentoToPagamentoRequestConverter;
import br.com.ismyburguer.pagamento.adapter.converter.PagamentoToPagamentoResponseConverter;
import br.com.ismyburguer.pagamento.adapter.request.PagamentoRequest;
import br.com.ismyburguer.pagamento.adapter.response.PagamentoResponse;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.gateway.out.EfetuarPagamentoAPI;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
@WebAdapter
public class EfetuarPagamentoAPIImpl implements EfetuarPagamentoAPI {
    private final PagamentoAPI pagamentoAPI;
    private final PagamentoToPagamentoRequestConverter converter;

    public EfetuarPagamentoAPIImpl(FeignClientAPI feignClientAPI,
                                   PagamentoToPagamentoRequestConverter converter) {
        this.pagamentoAPI = feignClientAPI.createClient(PagamentoAPI.class);
        this.converter = converter;
    }

    @Override
    public UUID pagar(Pagamento pagamento) {
        PagamentoRequest pagamentoRequest = converter.convert(pagamento);
        return pagamentoAPI.save(pagamentoRequest);
    }
}
