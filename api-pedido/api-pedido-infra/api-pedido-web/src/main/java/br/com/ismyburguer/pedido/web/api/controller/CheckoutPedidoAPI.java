package br.com.ismyburguer.pedido.web.api.controller;


import br.com.ismyburguer.core.adapter.in.WebAdapter;
import br.com.ismyburguer.pedido.adapter.interfaces.in.FecharPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import io.swagger.v3.oas.annotations.Operation;
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
public class CheckoutPedidoAPI {
    private final FecharPedidoUseCase useCase;

    public CheckoutPedidoAPI(FecharPedidoUseCase useCase) {
        this.useCase = useCase;
    }

    @Operation(security = @SecurityRequirement(name = "Bearer-Authentication"), description = "Pedido Checkout")
    @PutMapping("/{pedidoId}/checkout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fecharPedido(
            @PathVariable @Valid @UUID(message = "O código do pedido informado está num formato inválido") String pedidoId
    ) {
        useCase.fechar(new Pedido.PedidoId(pedidoId));
    }

}
