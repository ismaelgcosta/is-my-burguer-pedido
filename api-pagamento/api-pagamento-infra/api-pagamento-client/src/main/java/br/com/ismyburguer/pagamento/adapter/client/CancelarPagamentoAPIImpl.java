package br.com.ismyburguer.pagamento.adapter.client;

import br.com.ismyburguer.core.adapter.in.WebAdapter;
import br.com.ismyburguer.pagamento.adapter.converter.PagamentoToPagamentoRequestConverter;
import br.com.ismyburguer.pagamento.adapter.enumeration.StatusPagamento;
import br.com.ismyburguer.pagamento.adapter.request.PagamentoRequest;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.gateway.out.CancelarPagamentoAPI;
import br.com.ismyburguer.pagamento.gateway.out.ConsultarPagamentoAPI;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

import static br.com.ismyburguer.pagamento.adapter.enumeration.StatusPagamento.ESTORNADO;

@Validated
@WebAdapter
public class CancelarPagamentoAPIImpl implements CancelarPagamentoAPI {
    private final SqsTemplate sqsTemplate;

    @Setter
    @Value("${events.queues.is-my-burguer-pagamento-queue}")
    private String pagamentoQueue;

    private final ConsultarPagamentoAPI consultarPagamentoAPI;
    private final PagamentoToPagamentoRequestConverter converter;
    private final ObjectMapper objectMapper;

    public CancelarPagamentoAPIImpl(SqsTemplate sqsTemplate,
                                    ConsultarPagamentoAPI consultarPagamentoAPI,
                                    PagamentoToPagamentoRequestConverter converter,
                                    ObjectMapper objectMapper) {
        this.sqsTemplate = sqsTemplate;
        this.consultarPagamentoAPI = consultarPagamentoAPI;
        this.converter = converter;
        this.objectMapper = objectMapper;
    }

    @Override
    public void cancelar(String pedidoId) {
        PagamentoRequest pagamentoRequest = new PagamentoRequest(
                UUID.fromString(pedidoId),
                ESTORNADO
        );
        sqsTemplate.send(to -> {
            try {
                to.queue(pagamentoQueue).payload(objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(pagamentoRequest));
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("Erro ao realizar checkout do pedido", e);
            }
        });
    }
}
