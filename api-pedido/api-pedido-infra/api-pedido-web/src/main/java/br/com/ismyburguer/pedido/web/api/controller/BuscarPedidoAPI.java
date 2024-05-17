package br.com.ismyburguer.pedido.web.api.controller;


import br.com.ismyburguer.core.adapter.in.WebAdapter;
import br.com.ismyburguer.pedido.adapter.interfaces.in.ConsultarPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.web.api.converter.BuscarPedidoConverter;
import br.com.ismyburguer.pedido.web.api.response.BuscarPedidoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Pedido", description = "Gerenciamento de Pedidos")
@WebAdapter
@RequestMapping("/pedidos")
public class BuscarPedidoAPI {
    private final ConsultarPedidoUseCase consultarPedidoUseCase;
    private final BuscarPedidoConverter buscarPedidoConverter;

    public BuscarPedidoAPI(ConsultarPedidoUseCase consultarPedidoUseCase,
                           BuscarPedidoConverter buscarPedidoConverter) {
        this.consultarPedidoUseCase = consultarPedidoUseCase;
        this.buscarPedidoConverter = buscarPedidoConverter;
    }

    @Operation(security = @SecurityRequirement(name = "Bearer-Authentication"), description = "Consultar Pedido")
    @GetMapping("/{pedidoId}")
    public BuscarPedidoResponse obter(@Valid @UUID @PathVariable(name = "pedidoId") String pedidoId) {
        return buscarPedidoConverter.convert(consultarPedidoUseCase.buscarPorId(new Pedido.PedidoId(pedidoId)));
    }

}
