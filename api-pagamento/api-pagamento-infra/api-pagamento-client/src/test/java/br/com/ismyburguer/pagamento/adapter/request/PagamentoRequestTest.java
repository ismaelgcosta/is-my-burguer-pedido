package br.com.ismyburguer.pagamento.adapter.request;

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
import static br.com.ismyburguer.pagamento.entity.Pagamento.StatusPagamento.PAGO;
import static br.com.ismyburguer.pagamento.entity.Pagamento.TipoPagamento.QR_CODE;
import static org.junit.jupiter.api.Assertions.*;

class PagamentoRequestTest {

    @Test
    void deveRespeitarAsRegrasMinimas() {
        EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(PagamentoRequest.class);
        EqualsVerifier.forClass(PagamentoRequest.class).suppress(
                Warning.STRICT_INHERITANCE,
                Warning.INHERITED_DIRECTLY_FROM_OBJECT,
                Warning.ALL_FIELDS_SHOULD_BE_USED,
                Warning.BIGDECIMAL_EQUALITY,
                Warning.NONFINAL_FIELDS).verify();

        Pagamento.PedidoId pedidoId = new Pagamento.PedidoId(UUID.randomUUID());
        BigDecimal total = BigDecimal.ONE;
        String qrCode = "qr_code";

        PagamentoRequest pagamentoRequest = new PagamentoRequest(
                pedidoId.getPedidoId(),
                StatusPagamento.NAO_AUTORIZADO,
                TipoPagamento.QR_CODE,
                FormaPagamento.MERCADO_PAGO,
                total,
                qrCode);

        assertEquals(total, pagamentoRequest.getValorTotal());
        assertEquals(pedidoId.getPedidoId(), pagamentoRequest.getPedidoId());
        assertEquals(qrCode, pagamentoRequest.getQrCode());
        assertEquals(NAO_AUTORIZADO.name(), pagamentoRequest.getStatusPagamento().name());
        assertEquals(MERCADO_PAGO.name(), pagamentoRequest.getFormaPagamento().name());
        assertEquals(QR_CODE.name(), pagamentoRequest.getTipoPagamento().name());
    }

}