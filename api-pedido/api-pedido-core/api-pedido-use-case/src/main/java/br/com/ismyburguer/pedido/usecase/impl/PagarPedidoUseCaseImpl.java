package br.com.ismyburguer.pedido.usecase.impl;

import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.controlepedido.adapter.interfaces.in.GerarControlePedidoUseCase;
import br.com.ismyburguer.core.usecase.UseCase;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.ConsultarPagamentoUseCase;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.EfetuarPagamentoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import br.com.ismyburguer.pedido.adapter.interfaces.in.ConsultarPedidoUseCase;
import br.com.ismyburguer.pedido.adapter.interfaces.in.PagarPedidoUseCase;

import java.util.UUID;

@UseCase
public class PagarPedidoUseCaseImpl implements PagarPedidoUseCase {
    private final EfetuarPagamentoUseCase pagamentoUseCase;
    private final ConsultarPagamentoUseCase consultarPagamentoUseCase;
    private final ConsultarPedidoUseCase pedidoUseCase;
    private final AlterarStatusPedidoUseCase alterarStatusPedidoUseCase;
    private final GerarControlePedidoUseCase gerarControlePedidoUseCase;

    public PagarPedidoUseCaseImpl(EfetuarPagamentoUseCase pagamentoUseCase,
                                  ConsultarPedidoUseCase pedidoUseCase,
                                  ConsultarPagamentoUseCase consultarPagamentoUseCase,
                                  AlterarStatusPedidoUseCase alterarStatusPedidoUseCase,
                                  GerarControlePedidoUseCase gerarControlePedidoUseCase) {
        this.pagamentoUseCase = pagamentoUseCase;
        this.pedidoUseCase = pedidoUseCase;
        this.consultarPagamentoUseCase = consultarPagamentoUseCase;
        this.alterarStatusPedidoUseCase = alterarStatusPedidoUseCase;
        this.gerarControlePedidoUseCase = gerarControlePedidoUseCase;
    }

    @Override
    public String pagar(Pedido.PedidoId pedidoId) {
        Pedido pedido = pedidoUseCase.buscarPorId(pedidoId);
        alterarStatusPedidoUseCase.alterar(pedidoId, Pedido.StatusPedido.AGUARDANDO_PAGAMENTO);

        UUID uuid = pagamentoUseCase.pagar(new Pagamento(
                new Pagamento.PedidoId(pedido.getPedidoId().get().getPedidoId()),
                new Pagamento.Total(pedido.getTotal().getValor())
        ));
        Pagamento pagamento = consultarPagamentoUseCase.consultar(uuid.toString());
        Pagamento.StatusPagamento statusPagamento = pagamento.getStatusPagamento();

        switch (statusPagamento) {
            case AGUARDANDO_CONFIRMACAO -> alterarStatusPedidoUseCase.alterar(pedidoId, Pedido.StatusPedido.AGUARDANDO_PAGAMENTO);
            case NAO_AUTORIZADO -> alterarStatusPedidoUseCase.alterar(pedidoId, Pedido.StatusPedido.PAGAMENTO_NAO_AUTORIZADO);
            case PAGO -> {
                alterarStatusPedidoUseCase.alterar(pedidoId, Pedido.StatusPedido.PAGO);
                gerarControlePedidoUseCase.receberPedido(new ControlePedido.PedidoId(pedidoId.getPedidoId()));
            }
        }
        return pagamento.getQrCode();
    }
}
