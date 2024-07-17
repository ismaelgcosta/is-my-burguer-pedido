package br.com.ismyburguer.pedido.web.sqs.listener;


import br.com.ismyburguer.core.adapter.in.WebAdapter;
import br.com.ismyburguer.core.validation.EnumNamePattern;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.web.api.request.PedidoRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
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
