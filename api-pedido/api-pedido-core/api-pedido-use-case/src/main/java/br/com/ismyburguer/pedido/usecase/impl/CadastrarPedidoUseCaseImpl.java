package br.com.ismyburguer.pedido.usecase.impl;

import br.com.ismyburguer.cliente.adapter.interfaces.out.ConsultarClienteUseCase;
import br.com.ismyburguer.cliente.entity.Cliente;
import br.com.ismyburguer.core.usecase.UseCase;
import br.com.ismyburguer.pedido.adapter.interfaces.in.CadastrarPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.gateway.out.CadastrarPedidoRepository;
import br.com.ismyburguer.pedido.usecase.validation.PedidoValidator;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Map;
import java.util.UUID;

@UseCase
public class CadastrarPedidoUseCaseImpl implements CadastrarPedidoUseCase {
    private final CadastrarPedidoRepository repository;
    private final PedidoValidator validator;
    private final ConsultarClienteUseCase consultarClienteUseCase;

    public CadastrarPedidoUseCaseImpl(CadastrarPedidoRepository repository, PedidoValidator validator, ConsultarClienteUseCase consultarClienteUseCase) {
        this.repository = repository;
        this.validator = validator;
        this.consultarClienteUseCase = consultarClienteUseCase;
    }

    @Override
    public UUID cadastrar(@Valid Pedido pedido) {
        validator.validate(null, pedido);

        if (pedido.getClienteId().isEmpty() && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            Map<String, Object> claims = ((Jwt) SecurityContextHolder.getContext().getAuthentication().getCredentials()).getClaims();
            String username = claims.containsKey("username") ? String.valueOf(claims.get("username")) : null;

            if(consultarClienteUseCase.existsByUsername(username)) {
                ConsultarClienteUseCase.ConsultaClientePorUsername consultaClientePorUsername = new ConsultarClienteUseCase.ConsultaClientePorUsername(username);
                Cliente cliente = consultarClienteUseCase.buscarPorUsername(consultaClientePorUsername);
                pedido.setClienteId(new Pedido.ClienteId(cliente.getClienteId().getClienteId()));
            }
        }

        return repository.salvarPedido(pedido);
    }
}
