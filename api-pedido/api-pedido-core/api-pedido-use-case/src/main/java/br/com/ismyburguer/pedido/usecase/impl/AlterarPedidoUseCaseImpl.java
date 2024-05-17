package br.com.ismyburguer.pedido.usecase.impl;

import br.com.ismyburguer.core.usecase.UseCase;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.gateway.out.AlterarPedidoRepository;
import br.com.ismyburguer.pedido.usecase.validation.PedidoValidator;
import jakarta.validation.Valid;

@UseCase
public class AlterarPedidoUseCaseImpl implements AlterarPedidoUseCase {
    private final AlterarPedidoRepository repository;
    private final PedidoValidator validator;

    public AlterarPedidoUseCaseImpl(AlterarPedidoRepository repository, PedidoValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @Override
    public void alterar(String pedidoId, @Valid Pedido pedido) {
        validator.validate(pedidoId, pedido);
        repository.alterarPedido(pedidoId, pedido);
    }
}
