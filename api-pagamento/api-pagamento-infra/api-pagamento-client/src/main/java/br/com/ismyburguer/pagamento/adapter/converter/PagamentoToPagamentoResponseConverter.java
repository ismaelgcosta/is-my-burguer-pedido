package br.com.ismyburguer.pagamento.adapter.converter;

import br.com.ismyburguer.core.adapter.Converter;
import br.com.ismyburguer.core.adapter.in.WebConverter;
import br.com.ismyburguer.pagamento.adapter.enumeration.StatusPagamento;
import br.com.ismyburguer.pagamento.adapter.enumeration.TipoPagamento;
import br.com.ismyburguer.pagamento.adapter.response.FormaPagamento;
import br.com.ismyburguer.pagamento.adapter.response.PagamentoResponse;
import br.com.ismyburguer.pagamento.entity.Pagamento;

@WebConverter
public class PagamentoToPagamentoResponseConverter implements Converter<Pagamento, PagamentoResponse> {
    @Override
    public PagamentoResponse convert(Pagamento source) {
        return new PagamentoResponse(
                source.getPedidoId().getPedidoId(),
                StatusPagamento.valueOf(source.getStatusPagamento().name()),
                TipoPagamento.valueOf(source.getTipoPagamento().name()),
                FormaPagamento.valueOf(source.getFormaPagamento().name()),
                source.getTotal().getValor(),
                source.getQrCode()
        );
    }
}
