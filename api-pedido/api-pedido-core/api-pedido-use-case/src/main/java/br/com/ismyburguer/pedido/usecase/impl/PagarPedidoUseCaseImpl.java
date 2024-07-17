package br.com.ismyburguer.pedido.usecase.impl;

import br.com.ismyburguer.core.usecase.UseCase;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.EfetuarPagamentoUseCase;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pedido.adapter.interfaces.in.ConsultarPedidoUseCase;
import br.com.ismyburguer.pedido.adapter.interfaces.in.PagarPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;

@UseCase
public class PagarPedidoUseCaseImpl implements PagarPedidoUseCase {
    private final EfetuarPagamentoUseCase pagamentoUseCase;
    private final ConsultarPedidoUseCase pedidoUseCase;

    public PagarPedidoUseCaseImpl(EfetuarPagamentoUseCase pagamentoUseCase,
                                  ConsultarPedidoUseCase pedidoUseCase) {
        this.pagamentoUseCase = pagamentoUseCase;
        this.pedidoUseCase = pedidoUseCase;
    }

    @Override
    public void pagar(Pedido.PedidoId pedidoId) {
        Pedido pedido = pedidoUseCase.buscarPorId(pedidoId);

        pagamentoUseCase.pagar(new Pagamento(
                new Pagamento.PedidoId(pedido.getPedidoId().orElseThrow(() -> new IllegalArgumentException("id está nulo")).getPedidoId()),
                new Pagamento.Total(pedido.getTotal().getValor())
        ));
    }
}
