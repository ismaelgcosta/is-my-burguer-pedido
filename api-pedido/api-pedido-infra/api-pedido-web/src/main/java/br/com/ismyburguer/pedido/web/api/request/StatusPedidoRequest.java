package br.com.ismyburguer.pedido.web.api.request;

public enum StatusPedidoRequest {

    ABERTO,
    FECHADO,
    AGUARDANDO_PAGAMENTO,
    AGUARDANDO_CONFIRMACAO_PAGAMENTO,
    CANCELADO,
    PAGO,
    PAGAMENTO_NAO_AUTORIZADO,
    RECEBIDO,
    EM_PREPARACAO,
    PRONTO,
    FINALIZADO;
}
