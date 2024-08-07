package br.com.ismyburguer.pagamento.entity;

import br.com.ismyburguer.core.validation.Validation;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Pagamento implements Validation {

    private UUID pagamentoId;

    @NotNull(message = "Informe o código do Pedido")
    private PedidoId pedidoId;

    @NotNull(message = "Informe o valor Total do Pedido")
    private Total total;

    private StatusPagamento statusPagamento = StatusPagamento.AGUARDANDO_CONFIRMACAO;

    private FormaPagamento formaPagamento = FormaPagamento.MERCADO_PAGO;

    private TipoPagamento tipoPagamento = TipoPagamento.QR_CODE;

    @Setter
    private String qrCode;

    public Pagamento(PedidoId pedidoId, Total total) {
        this.pedidoId = pedidoId;
        this.total = total;
    }

    public void pago() {
        this.statusPagamento = StatusPagamento.PAGO;
    }

    public void estornado() {
        this.statusPagamento = StatusPagamento.PAGO;
    }

    @Getter
    public enum StatusPagamento {

        AGUARDANDO_CONFIRMACAO("Aguardando Confirmação do Pagamento"),
        ESTORNADO("Estornado"),
        NAO_AUTORIZADO("Não Autorizado"),
        PAGO("Pago");

        private final String descricao;

        StatusPagamento(String descricao) {
            this.descricao = descricao;
        }

    }

    @Getter
    public enum FormaPagamento {

        MERCADO_PAGO("Mercado Pago");

        private final String descricao;

        FormaPagamento(String descricao) {
            this.descricao = descricao;
        }
    }

    @Getter
    public enum TipoPagamento {

        QR_CODE("QR Code");

        private final String descricao;

        TipoPagamento(String descricao) {
            this.descricao = descricao;
        }

    }

    @Getter
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class PedidoId {

        @NotNull(message = "Informe o código do Pedido")
        private UUID pedidoId;

    }

    @Getter
    @AllArgsConstructor
    public static class Total {

        @NotNull(message = "Informe o valor Total do Pedido")
        @DecimalMin(value = "0.01", message = "O valor total do item deve ser de no mínimo R$0,01")
        private BigDecimal valor;

    }

}
