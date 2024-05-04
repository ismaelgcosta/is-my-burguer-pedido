package br.com.ismyburguer.pagamento.adapter.response;

import br.com.ismyburguer.pagamento.adapter.enumeration.StatusPagamento;
import br.com.ismyburguer.pagamento.adapter.enumeration.TipoPagamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoResponse {

    private UUID pagamentoId = UUID.randomUUID();

    private UUID pedidoId;

    private StatusPagamento statusPagamento;

    private TipoPagamento tipoPagamento;

    private FormaPagamento formaPagamento;

    private BigDecimal valorTotal;

    private String qrCode;

    public PagamentoResponse(UUID pedidoId, StatusPagamento statusPagamento, TipoPagamento tipoPagamento, FormaPagamento formaPagamento, BigDecimal valorTotal) {
        this.pedidoId = pedidoId;
        this.statusPagamento = statusPagamento;
        this.tipoPagamento = tipoPagamento;
        this.formaPagamento = formaPagamento;
        this.valorTotal = valorTotal;
    }

    public PagamentoResponse(UUID pedidoId, StatusPagamento statusPagamento, TipoPagamento tipoPagamento, FormaPagamento formaPagamento, BigDecimal valorTotal, String qrCode) {
        this.pedidoId = pedidoId;
        this.statusPagamento = statusPagamento;
        this.tipoPagamento = tipoPagamento;
        this.formaPagamento = formaPagamento;
        this.valorTotal = valorTotal;
        this.qrCode = qrCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PagamentoResponse that)) return false;

        return new EqualsBuilder().append(getPagamentoId(), that.getPagamentoId()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getPagamentoId()).toHashCode();
    }
}
