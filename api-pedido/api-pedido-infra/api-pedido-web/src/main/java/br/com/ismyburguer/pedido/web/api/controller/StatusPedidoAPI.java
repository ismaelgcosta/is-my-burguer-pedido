package br.com.ismyburguer.pedido.web.api.controller;


import br.com.ismyburguer.core.adapter.in.WebAdapter;
import br.com.ismyburguer.core.validation.EnumNamePattern;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Pedido", description = "Gerenciamento de Pedidos")
@WebAdapter
@RequestMapping("/pedidos")
public class StatusPedidoAPI {
    private final AlterarStatusPedidoUseCase useCase;

    public StatusPedidoAPI(AlterarStatusPedidoUseCase useCase) {
        this.useCase = useCase;
    }

    @Operation(security = @SecurityRequirement(name = "Bearer Authentication"), description = "Alterar Status do Pedido")
    @PutMapping("/{pedidoId}/{status}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarStatusPedido(
            @PathVariable @Valid @UUID(message = "O código do pedido informado está num formato inválido") String pedidoId,
            @PathVariable @Schema(description = "Status do Pedido",
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
            ) String status
    ) {
        useCase.alterar(new Pedido.PedidoId(pedidoId), Pedido.StatusPedido.valueOf(status));
    }

}
