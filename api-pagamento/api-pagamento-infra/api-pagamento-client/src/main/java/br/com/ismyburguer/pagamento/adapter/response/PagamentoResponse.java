package br.com.ismyburguer.pagamento.adapter.response;

import br.com.ismyburguer.pagamento.adapter.enumeration.StatusPagamento;
import br.com.ismyburguer.pagamento.adapter.enumeration.TipoPagamento;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "pagamentoId")
@RequiredArgsConstructor
public class PagamentoResponse {

    private UUID pagamentoId = UUID.randomUUID();

    @NonNull
    private UUID pedidoId;

    @NonNull
    private StatusPagamento statusPagamento;

    @NonNull
    private TipoPagamento tipoPagamento;

    @NonNull
    private FormaPagamento formaPagamento;

    @NonNull
    private BigDecimal valorTotal;

    @NonNull
    private String qrCode;


}
