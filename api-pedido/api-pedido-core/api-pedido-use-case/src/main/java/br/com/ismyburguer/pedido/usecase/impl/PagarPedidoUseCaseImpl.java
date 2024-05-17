package br.com.ismyburguer.pedido.usecase.impl;

import br.com.ismyburguer.core.exception.BusinessException;
import br.com.ismyburguer.core.usecase.UseCase;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.ConsultarPagamentoUseCase;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.EfetuarPagamentoUseCase;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import br.com.ismyburguer.pedido.adapter.interfaces.in.ConsultarPedidoUseCase;
import br.com.ismyburguer.pedido.adapter.interfaces.in.PagarPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;

import java.util.UUID;

@UseCase
public class PagarPedidoUseCaseImpl implements PagarPedidoUseCase {
    private final EfetuarPagamentoUseCase pagamentoUseCase;
    private final ConsultarPagamentoUseCase consultarPagamentoUseCase;
    private final ConsultarPedidoUseCase pedidoUseCase;
    private final AlterarStatusPedidoUseCase alterarStatusPedidoUseCase;

    public PagarPedidoUseCaseImpl(EfetuarPagamentoUseCase pagamentoUseCase,
                                  ConsultarPedidoUseCase pedidoUseCase,
                                  ConsultarPagamentoUseCase consultarPagamentoUseCase,
                                  AlterarStatusPedidoUseCase alterarStatusPedidoUseCase) {
        this.pagamentoUseCase = pagamentoUseCase;
        this.pedidoUseCase = pedidoUseCase;
        this.consultarPagamentoUseCase = consultarPagamentoUseCase;
        this.alterarStatusPedidoUseCase = alterarStatusPedidoUseCase;
    }

    @Override
    public String pagar(Pedido.PedidoId pedidoId) {
        Pedido pedido = pedidoUseCase.buscarPorId(pedidoId);
        alterarStatusPedidoUseCase.alterar(pedidoId, Pedido.StatusPedido.AGUARDANDO_PAGAMENTO);

        UUID pagamentoId = pagamentoUseCase.pagar(new Pagamento(
                new Pagamento.PedidoId(pedido.getPedidoId().get().getPedidoId()),
                new Pagamento.Total(pedido.getTotal().getValor())
        ));

        return consultarPagamento(pagamentoId);
    }

    private String consultarPagamento(UUID pagamentoId) {
        Pagamento pagamento = consultarPagamentoUseCase.consultar(pagamentoId.toString());
        Pagamento.StatusPagamento statusPagamento = pagamento.getStatusPagamento();

        return switch (statusPagamento) {
            case AGUARDANDO_CONFIRMACAO -> consultarPagamento(pagamentoId);
            case NAO_AUTORIZADO -> throw new BusinessException("Pagamento não autorizado. Tente novamente");
            case PAGO -> pagamento.getQrCode();
        };
    }
}
