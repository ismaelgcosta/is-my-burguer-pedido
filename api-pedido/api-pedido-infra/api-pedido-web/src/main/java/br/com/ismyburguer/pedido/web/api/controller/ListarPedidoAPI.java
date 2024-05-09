package br.com.ismyburguer.pedido.web.api.controller;


import br.com.ismyburguer.core.adapter.in.WebAdapter;
import br.com.ismyburguer.pedido.adapter.interfaces.in.ListarPedidoUseCase;
import br.com.ismyburguer.pedido.web.api.converter.ListarPedidoConverter;
import br.com.ismyburguer.pedido.web.api.response.ListarPedidoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "Pedido", description = "Gerenciamento de Pedidos")
@WebAdapter
@RequestMapping("/pedidos")
public class ListarPedidoAPI {
    private final ListarPedidoUseCase listarPedidoUseCase;
    private final ListarPedidoConverter listarPedidoConverter;

    public ListarPedidoAPI(ListarPedidoUseCase listarPedidoUseCase,
                           ListarPedidoConverter listarPedidoConverter) {
        this.listarPedidoUseCase = listarPedidoUseCase;
        this.listarPedidoConverter = listarPedidoConverter;
    }

    @Operation(security = @SecurityRequirement(name = "Bearer-Authentication"), description = "Listar Pedidos")
    @GetMapping
    public List<ListarPedidoResponse> listar() {
        return listarPedidoUseCase.listar().stream().map(listarPedidoConverter::convert).toList();
    }

}
