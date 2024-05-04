package br.com.ismyburguer.pedido.usecase.impl;

import br.com.ismyburguer.core.usecase.UseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import br.com.ismyburguer.pedido.adapter.interfaces.in.ConsultarPedidoUseCase;
import br.com.ismyburguer.pedido.gateway.out.AlterarStatusPedidoRepository;

@UseCase
public class AlterarStatusPedidoUseCaseImpl implements AlterarStatusPedidoUseCase {
    private final AlterarStatusPedidoRepository repository;
    private final ConsultarPedidoUseCase consultarPedidoUseCase;
    public AlterarStatusPedidoUseCaseImpl(AlterarStatusPedidoRepository repository, ConsultarPedidoUseCase consultarPedidoUseCase) {
        this.repository = repository;
        this.consultarPedidoUseCase = consultarPedidoUseCase;
    }

    @Override
    public void alterar(Pedido.PedidoId pedidoId, Pedido.StatusPedido statusPedido) {
        Pedido pedido = consultarPedidoUseCase.buscarPorId(pedidoId);
        pedido.alterarStatus(statusPedido);
        repository.alterar(pedidoId, statusPedido);
    }
}
