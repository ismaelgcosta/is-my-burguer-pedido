package br.com.ismyburguer.pedido.web.api.controller;


import br.com.ismyburguer.pedido.web.api.converter.AlterarPedidoRequestConverter;
import br.com.ismyburguer.pedido.web.api.request.AlterarPedidoRequest;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarPedidoUseCase;
import br.com.ismyburguer.core.adapter.in.WebAdapter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Pedido", description = "Gerenciamento de Pedidos")
@WebAdapter
@RequestMapping("/pedidos")
public class AlterarPedidoAPI {
    private final AlterarPedidoUseCase useCase;
    private final AlterarPedidoRequestConverter converter;

    public AlterarPedidoAPI(AlterarPedidoUseCase useCase,
                            AlterarPedidoRequestConverter converter) {
        this.useCase = useCase;
        this.converter = converter;
    }

    @Operation(security = @SecurityRequirement(name = "Bearer-Authentication"), description = "Alterar Pedido")
    @PutMapping("/{pedidoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarPedido(
            @PathVariable @Valid @UUID(message = "O código do pedido informado está num formato inválido") String pedidoId,
            @RequestBody AlterarPedidoRequest request
    ) {
        useCase.alterar(pedidoId, converter.convert(request));
    }

}
