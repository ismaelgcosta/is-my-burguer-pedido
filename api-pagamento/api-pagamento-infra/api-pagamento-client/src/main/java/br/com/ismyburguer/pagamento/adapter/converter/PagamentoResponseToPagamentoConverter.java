package br.com.ismyburguer.pagamento.adapter.converter;

import br.com.ismyburguer.core.adapter.Converter;
import br.com.ismyburguer.core.adapter.in.WebConverter;
import br.com.ismyburguer.pagamento.adapter.response.PagamentoResponse;
import br.com.ismyburguer.pagamento.entity.Pagamento;

@WebConverter
public class PagamentoResponseToPagamentoConverter implements Converter<PagamentoResponse, Pagamento> {
    @Override
    public Pagamento convert(PagamentoResponse source) {
        return new Pagamento(
                source.getPagamentoId(),
                new Pagamento.PedidoId(source.getPedidoId()),
                new Pagamento.Total(source.getValorTotal()),
                Pagamento.StatusPagamento.valueOf(source.getStatusPagamento().name()),
                Pagamento.FormaPagamento.valueOf(source.getFormaPagamento().name()),
                Pagamento.TipoPagamento.valueOf(source.getTipoPagamento().name()),
                source.getQrCode()
        );
    }
}
