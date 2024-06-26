package br.com.ismyburguer.pedido.web.api.request;

import br.com.ismyburguer.core.validation.EnumNamePattern;
import br.com.ismyburguer.core.validation.Validation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class AlterarPedidoRequest implements Validation {

    @UUID(message = "o id informado está num formato inválido")
    private String clienteId;

    @Schema(description = "Status do Pedido",
            allowableValues = {
                    "ABERTO",
                    "FECHADO",
                    "PAGO",
                    "RECEBIDO",
                    "EM_PREPARACAO",
                    "PRONTO",
                    "FINALIZADO"
            })
    @EnumNamePattern(
            regexp = "ABERTO|FECHADO|PAGO|RECEBIDO|EM_PREPARACAO|PRONTO|FINALIZADO",
            message = "O campo status do pedido deve ser igual a {regexp}"
    )
    private String statusPedido;

    @Size(min = 1, message = "É necessário informar ao menos um item no pedido")
    private List<AlterarItemPedidoRequest> itens;

    public Optional<String> getClienteId() {
        return Optional.ofNullable(clienteId);
    }
}
