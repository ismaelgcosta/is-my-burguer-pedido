package br.com.ismyburguer.pedido.usecase.impl;

import br.com.ismyburguer.core.usecase.UseCase;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.CancelarPagamentoUseCase;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.ConsultarPagamentoUseCase;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import br.com.ismyburguer.pedido.adapter.interfaces.in.ConsultarPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.gateway.out.AlterarStatusPedidoRepository;

@UseCase
public class AlterarStatusPedidoUseCaseImpl implements AlterarStatusPedidoUseCase {
    private final AlterarStatusPedidoRepository repository;
    private final ConsultarPedidoUseCase consultarPedidoUseCase;
    private final CancelarPagamentoUseCase cancelarPagamentoUseCase;
    public AlterarStatusPedidoUseCaseImpl(AlterarStatusPedidoRepository repository, ConsultarPedidoUseCase consultarPedidoUseCase, CancelarPagamentoUseCase cancelarPagamentoUseCase) {
        this.repository = repository;
        this.consultarPedidoUseCase = consultarPedidoUseCase;
        this.cancelarPagamentoUseCase = cancelarPagamentoUseCase;
    }

    @Override
    public void alterar(Pedido.PedidoId pedidoId, Pedido.StatusPedido statusPedido) {
        Pedido pedido = consultarPedidoUseCase.buscarPorId(pedidoId);
        pedido.alterarStatus(statusPedido);
        if(statusPedido == Pedido.StatusPedido.CANCELADO) {
            cancelarPagamentoUseCase.cancelar(new Pagamento.PedidoId(pedidoId.getPedidoId()));
        }
        repository.alterar(pedidoId, statusPedido);
    }
}
