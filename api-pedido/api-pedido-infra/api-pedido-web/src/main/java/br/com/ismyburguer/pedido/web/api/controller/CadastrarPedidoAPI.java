package br.com.ismyburguer.pedido.web.api.controller;

import br.com.ismyburguer.pedido.adapter.interfaces.in.CadastrarPedidoUseCase;
import br.com.ismyburguer.pedido.web.api.converter.CadastrarPedidoRequestConverter;
import br.com.ismyburguer.pedido.web.api.request.CadastrarPedidoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
@Tag(name = "Pedido", description = "Gerenciamento de Pedidos")
@RestController
@RequestMapping("/pedidos")
public class CadastrarPedidoAPI {
    private final CadastrarPedidoUseCase cadastrarPedidoUseCase;
    private final CadastrarPedidoRequestConverter cadastrarPedidoRequestConverter;

    public CadastrarPedidoAPI(CadastrarPedidoUseCase cadastrarPedidoUseCase,
                              CadastrarPedidoRequestConverter cadastrarPedidoRequestConverter) {
        this.cadastrarPedidoUseCase = cadastrarPedidoUseCase;
        this.cadastrarPedidoRequestConverter = cadastrarPedidoRequestConverter;
    }

    @Operation(security = @SecurityRequirement(name = "Bearer-Authentication"), description = "Cadastrar Pedido")
    @PostMapping
    public UUID cadastrarPedido(@RequestBody CadastrarPedidoRequest cadastrarPedidoRequest) {
        return cadastrarPedidoUseCase.cadastrar(cadastrarPedidoRequestConverter.convert(cadastrarPedidoRequest));
    }

}
