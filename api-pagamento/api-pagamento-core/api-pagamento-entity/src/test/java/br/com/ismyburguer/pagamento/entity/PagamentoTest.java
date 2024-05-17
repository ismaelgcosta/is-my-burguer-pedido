package br.com.ismyburguer.pagamento.entity;

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
import static org.junit.jupiter.api.Assertions.assertEquals;

class PagamentoTest {

    @Test
    void deveRespeitarAsRegrasMinimas() {
        EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Pagamento.class);
        GetterSetterVerifier.forClass(Pagamento.class).verify();
        EqualsVerifier.forClass(Pagamento.class).suppress(
                Warning.STRICT_INHERITANCE,
                Warning.INHERITED_DIRECTLY_FROM_OBJECT,
                Warning.ALL_FIELDS_SHOULD_BE_USED,
                Warning.BIGDECIMAL_EQUALITY,
                Warning.NONFINAL_FIELDS).verify();

        Pagamento.PedidoId pedidoId = new Pagamento.PedidoId(UUID.randomUUID());
        Pagamento.Total total = new Pagamento.Total(BigDecimal.ONE);
        String qrCode = "qr_code";

        Pagamento pedido = new Pagamento(
                pedidoId,
                total,
                NAO_AUTORIZADO,
                MERCADO_PAGO,
                QR_CODE,
                qrCode);
        assertEquals(total.getValor(), pedido.getTotal().getValor());
        assertEquals(pedidoId, pedido.getPedidoId());
        assertEquals(qrCode, pedido.getQrCode());
        assertEquals(NAO_AUTORIZADO, pedido.getStatusPagamento());
        assertEquals(MERCADO_PAGO, pedido.getFormaPagamento());
        assertEquals(QR_CODE, pedido.getTipoPagamento());

        pedido = new Pagamento(pedidoId, total);
        assertEquals(total.getValor(), pedido.getTotal().getValor());
        assertEquals(pedidoId, pedido.getPedidoId());

        pedido.pago();

        assertEquals(PAGO, pedido.getStatusPagamento());
    }
}