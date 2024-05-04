package br.com.ismyburguer.pagamento.adapter.client;

import br.com.ismyburguer.core.adapter.in.WebAdapter;
import br.com.ismyburguer.core.adapter.out.FeignClientAPI;
import br.com.ismyburguer.core.exception.EntityNotFoundException;
import br.com.ismyburguer.pagamento.adapter.converter.PagamentoResponseToPagamentoConverter;
import br.com.ismyburguer.pagamento.adapter.response.PagamentoResponse;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.gateway.out.ConsultarPagamentoAPI;
import feign.FeignException;
import org.hibernate.validator.constraints.UUID;

@WebAdapter
public class ConsultarPagamentoAPIImpl implements ConsultarPagamentoAPI {
    private final PagamentoAPI pagamentoAPI;
    private final PagamentoResponseToPagamentoConverter converter;

    public ConsultarPagamentoAPIImpl(FeignClientAPI feignClientAPI,
                                     PagamentoResponseToPagamentoConverter converter) {
        this.pagamentoAPI = feignClientAPI.createClient(PagamentoAPI.class);
        this.converter = converter;
    }

    @Override
    public Pagamento consultar(@UUID String pagamentoId) {
        try {
            PagamentoResponse pagamento = pagamentoAPI.findById(java.util.UUID.fromString(pagamentoId));
            return converter.convert(pagamento);
        } catch (FeignException e) {
            throw new EntityNotFoundException("Pagamento não encontrado");
        }
    }
}
