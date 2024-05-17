package br.com.ismyburguer.pedido.web.api.controller;


import br.com.ismyburguer.core.adapter.in.WebAdapter;
import br.com.ismyburguer.pedido.adapter.interfaces.in.PagarPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.UUID;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Pedido", description = "Gerenciamento de Pedidos")
@WebAdapter
@RequestMapping("/pedidos")
public class PagarPedidoAPI {
    private final PagarPedidoUseCase useCase;

    public PagarPedidoAPI(PagarPedidoUseCase useCase) {
        this.useCase = useCase;
    }

    @Operation(security = @SecurityRequirement(name = "Bearer-Authentication"), description = "Pagar Pedido")
    @PutMapping("/{pedidoId}/pagamento")
    public String pagarPedido(
            @PathVariable @Valid @UUID(message = "O código do pedido informado está num formato inválido") String pedidoId
    ) {
        return useCase.pagar(new Pedido.PedidoId(pedidoId));
    }

}
