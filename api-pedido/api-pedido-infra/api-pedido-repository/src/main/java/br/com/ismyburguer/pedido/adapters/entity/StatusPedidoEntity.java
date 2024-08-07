package br.com.ismyburguer.pedido.adapters.entity;

public enum StatusPedidoEntity {

    ABERTO("Aberto"),
    FECHADO("Fechado"),
    PAGO("Pago"),
    AGUARDANDO_PAGAMENTO("Aguardando Pagamento"),
    AGUARDANDO_CONFIRMACAO_PAGAMENTO("Aguardando Confirmação de Pagamento"),
    PAGAMENTO_NAO_AUTORIZADO("Pagamento Não Autorizado"),
    CANCELADO("Cancelado"),
    RECEBIDO("Recebido"),
    EM_PREPARACAO("Em Preparação"),
    PRONTO("Pronto"),
    FINALIZADO("Finalizado");

    private final String descricao;

    StatusPedidoEntity(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }

}
