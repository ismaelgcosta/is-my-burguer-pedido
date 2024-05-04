package br.com.ismyburguer.pagamento.adapter.request;

import lombok.Getter;

@Getter
public enum FormaPagamento {

    MERCADO_PAGO("Mercado Pago");

    private final String descricao;

    FormaPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}