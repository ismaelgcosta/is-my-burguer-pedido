package br.com.ismyburguer.pagamento.adapter.response;

import br.com.ismyburguer.pagamento.adapter.enumeration.StatusPagamento;
import br.com.ismyburguer.pagamento.adapter.enumeration.TipoPagamento;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static br.com.ismyburguer.pagamento.entity.Pagamento.FormaPagamento.MERCADO_PAGO;
import static br.com.ismyburguer.pagamento.entity.Pagamento.StatusPagamento.NAO_AUTORIZADO;
import static br.com.ismyburguer.pagamento.entity.Pagamento.TipoPagamento.QR_CODE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PagamentoResponseTest {

    @Test
    void deveRespeitarAsRegrasMinimas() {
        EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(PagamentoResponse.class);
        EqualsVerifier.forClass(PagamentoResponse.class).suppress(
                Warning.STRICT_INHERITANCE,
                Warning.INHERITED_DIRECTLY_FROM_OBJECT,
                Warning.ALL_FIELDS_SHOULD_BE_USED,
                Warning.BIGDECIMAL_EQUALITY,
                Warning.NONFINAL_FIELDS).verify();

        Pagamento.PedidoId pedidoId = new Pagamento.PedidoId(UUID.randomUUID());
        BigDecimal total = BigDecimal.ONE;
        String qrCode = "qr_code";

        PagamentoResponse pagamentoResponse = new PagamentoResponse(
                pedidoId.getPedidoId(),
                StatusPagamento.NAO_AUTORIZADO,
                TipoPagamento.QR_CODE,
                FormaPagamento.MERCADO_PAGO,
                total,
                qrCode);

        assertEquals(total, pagamentoResponse.getValorTotal());
        assertEquals(pedidoId.getPedidoId(), pagamentoResponse.getPedidoId());
        assertEquals(qrCode, pagamentoResponse.getQrCode());
        assertEquals(NAO_AUTORIZADO.name(), pagamentoResponse.getStatusPagamento().name());
        assertEquals(MERCADO_PAGO.name(), pagamentoResponse.getFormaPagamento().name());
        assertEquals(QR_CODE.name(), pagamentoResponse.getTipoPagamento().name());
    }

}