package br.com.ismyburguer.pagamento.adapter.client;

import br.com.ismyburguer.pagamento.adapter.request.PagamentoRequest;
import br.com.ismyburguer.pagamento.adapter.response.PagamentoResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.UUID;

public interface PagamentoAPI {

    @Headers("Content-Type: application/json")
    @RequestLine("GET /pagamentos/{pedidoId}")
    PagamentoResponse findById(@Param("pedidoId") UUID pedidoId);

    @Headers("Content-Type: application/json")
    @RequestLine("POST /pagamentos")
    UUID save(PagamentoRequest pagamento);
}
