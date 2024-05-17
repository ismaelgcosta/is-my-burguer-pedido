package br.com.ismyburguer.pagamento.adapter.converter;
import br.com.ismyburguer.pagamento.adapter.enumeration.StatusPagamento;
import br.com.ismyburguer.pagamento.adapter.enumeration.TipoPagamento;
import br.com.ismyburguer.pagamento.adapter.response.FormaPagamento;
import br.com.ismyburguer.pagamento.adapter.response.PagamentoResponse;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PagamentoResponseToPagamentoConverterTest {

    @Test
    public void deveConverterPagamentoResponseParaPagamentoComSucesso() {
        // Dados de exemplo para PagamentoResponse
        UUID pedidoId = UUID.fromString("123e4567-e89b-12d3-a456-556642440000");
        BigDecimal valorTotal = BigDecimal.valueOf(100.0);
        StatusPagamento statusPagamento = StatusPagamento.PAGO;
        FormaPagamento formaPagamento = FormaPagamento.MERCADO_PAGO;
        TipoPagamento tipoPagamento = TipoPagamento.QR_CODE;
        String qrCode = "1234567890";

        PagamentoResponse pagamentoResponse = new PagamentoResponse(pedidoId, statusPagamento, tipoPagamento, formaPagamento, valorTotal, qrCode);

        // Criar o conversor
        PagamentoResponseToPagamentoConverter converter = new PagamentoResponseToPagamentoConverter();

        // Converter o PagamentoResponse em Pagamento
        Pagamento pagamento = converter.convert(pagamentoResponse);

        // Verificar se a conversão foi realizada corretamente
        assertEquals(pedidoId, pagamento.getPedidoId().getPedidoId());
        assertEquals(valorTotal, pagamento.getTotal().getValor());
        assertEquals(Pagamento.StatusPagamento.PAGO, pagamento.getStatusPagamento());
        assertEquals(Pagamento.FormaPagamento.MERCADO_PAGO, pagamento.getFormaPagamento());
        assertEquals(Pagamento.TipoPagamento.QR_CODE, pagamento.getTipoPagamento());
        assertEquals(qrCode, pagamento.getQrCode());
    }
}
