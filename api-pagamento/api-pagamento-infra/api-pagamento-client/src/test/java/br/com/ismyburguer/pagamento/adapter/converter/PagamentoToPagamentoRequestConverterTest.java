package br.com.ismyburguer.pagamento.adapter.converter;

import br.com.ismyburguer.pagamento.adapter.enumeration.StatusPagamento;
import br.com.ismyburguer.pagamento.adapter.enumeration.TipoPagamento;
import br.com.ismyburguer.pagamento.adapter.request.FormaPagamento;
import br.com.ismyburguer.pagamento.adapter.request.PagamentoRequest;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.encrypt.Encryptors;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PagamentoToPagamentoRequestConverterTest {

    @Test
    public void deveConverterPagamentoParaPagamentoRequestComSucesso() {
        // Dados de exemplo para Pagamento
        String pedidoId = "123e4567-e89b-12d3-a456-556642440000";
        BigDecimal valorTotal = BigDecimal.valueOf(100.0);
        Pagamento.StatusPagamento statusPagamento = Pagamento.StatusPagamento.PAGO;
        Pagamento.TipoPagamento tipoPagamento = Pagamento.TipoPagamento.QR_CODE;
        Pagamento.FormaPagamento formaPagamento = Pagamento.FormaPagamento.MERCADO_PAGO;
        String qrCode = "1234567890";
        UUID pagamentoId = UUID.randomUUID();

        Pagamento pagamento = new Pagamento(
                pagamentoId,
                new Pagamento.PedidoId(UUID.fromString(pedidoId)),
                new Pagamento.Total(valorTotal),
                statusPagamento,
                formaPagamento,
                tipoPagamento,
                qrCode
        );

        // Criar o conversor
        PagamentoToPagamentoRequestConverter converter = new PagamentoToPagamentoRequestConverter();

        // Converter o Pagamento em PagamentoRequest
        PagamentoRequest pagamentoRequest = converter.convert(pagamento);

        // Verificar se a conversão foi realizada corretamente
        assertEquals(pagamentoId, pagamentoRequest.getPagamentoId());
        assertEquals(pedidoId, pagamentoRequest.getPedidoId().toString());
        assertEquals(StatusPagamento.valueOf(statusPagamento.name()), pagamentoRequest.getStatusPagamento());
        assertEquals(TipoPagamento.valueOf(tipoPagamento.name()), pagamentoRequest.getTipoPagamento());
        assertEquals(FormaPagamento.valueOf(formaPagamento.name()), pagamentoRequest.getFormaPagamento());
        assertEquals(valorTotal, pagamentoRequest.getValorTotal());
        assertEquals(qrCode, pagamentoRequest.getQrCode());
    }
}
