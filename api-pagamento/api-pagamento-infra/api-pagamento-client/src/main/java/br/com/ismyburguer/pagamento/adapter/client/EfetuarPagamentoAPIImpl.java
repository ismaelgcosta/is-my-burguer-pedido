package br.com.ismyburguer.pagamento.adapter.client;

import br.com.ismyburguer.core.adapter.in.WebAdapter;
import br.com.ismyburguer.pagamento.adapter.converter.PagamentoToPagamentoRequestConverter;
import br.com.ismyburguer.pagamento.adapter.request.PagamentoRequest;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.gateway.out.EfetuarPagamentoAPI;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;

@Validated
@WebAdapter
public class EfetuarPagamentoAPIImpl implements EfetuarPagamentoAPI {
    private final SqsTemplate sqsTemplate;

    @Setter
    @Value("${events.queues.is-my-burguer-pagamento-queue}")
    private String pagamentoQueue;

    private final PagamentoToPagamentoRequestConverter converter;
    private final ObjectMapper objectMapper;

    public EfetuarPagamentoAPIImpl(SqsTemplate sqsTemplate,
                                   PagamentoToPagamentoRequestConverter converter,
                                   ObjectMapper objectMapper) {
        this.sqsTemplate = sqsTemplate;
        this.converter = converter;
        this.objectMapper = objectMapper;
    }

    @Override
    public void pagar(Pagamento pagamento) {
        PagamentoRequest pagamentoRequest = converter.convert(pagamento);

        sqsTemplate.send(to -> {
            try {
                to.queue(pagamentoQueue).payload(objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(pagamentoRequest));
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("Erro ao realizar checkout do pedido", e);
            }
        });
    }
}
