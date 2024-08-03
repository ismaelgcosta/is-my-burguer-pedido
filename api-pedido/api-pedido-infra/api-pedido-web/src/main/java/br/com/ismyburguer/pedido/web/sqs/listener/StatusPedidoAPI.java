package br.com.ismyburguer.pedido.web.sqs.listener;

import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.web.api.request.PedidoRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(value = {"dev", "production"})
public class StatusPedidoAPI {

    private final ObjectMapper objectMapper;
    private final AlterarStatusPedidoUseCase useCase;

    public StatusPedidoAPI(ObjectMapper objectMapper, AlterarStatusPedidoUseCase useCase) {
        this.objectMapper = objectMapper;
        this.useCase = useCase;
    }

    @SqsListener(value = "${events.queues.is-my-burguer-pedido-queue}")
    public void alterarStatusPedido(String message) throws JsonProcessingException {
        PedidoRequest pedidoRequest = objectMapper.readValue(message, PedidoRequest.class);
        useCase.alterar(new Pedido.PedidoId(pedidoRequest.getPedidoId()), Pedido.StatusPedido.valueOf(pedidoRequest.getStatusPedido().name()));
    }

}
