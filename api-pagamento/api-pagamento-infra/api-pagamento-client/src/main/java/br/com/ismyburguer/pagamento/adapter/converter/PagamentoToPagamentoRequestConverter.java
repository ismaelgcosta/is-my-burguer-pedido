package br.com.ismyburguer.pagamento.adapter.converter;

import br.com.ismyburguer.core.adapter.Converter;
import br.com.ismyburguer.core.adapter.in.WebConverter;
import br.com.ismyburguer.pagamento.adapter.enumeration.StatusPagamento;
import br.com.ismyburguer.pagamento.adapter.enumeration.TipoPagamento;
import br.com.ismyburguer.pagamento.adapter.request.FormaPagamento;
import br.com.ismyburguer.pagamento.adapter.request.PagamentoRequest;
import br.com.ismyburguer.pagamento.entity.Pagamento;

@WebConverter
public class PagamentoToPagamentoRequestConverter implements Converter<Pagamento, PagamentoRequest> {
    @Override
    public PagamentoRequest convert(Pagamento source) {
        return new PagamentoRequest(
                source.getPagamentoId(),
                source.getPedidoId().getPedidoId(),
                StatusPagamento.valueOf(source.getStatusPagamento().name()),
                TipoPagamento.valueOf(source.getTipoPagamento().name()),
                FormaPagamento.valueOf(source.getFormaPagamento().name()),
                source.getTotal().getValor(),
                source.getQrCode()
        );
    }
}
