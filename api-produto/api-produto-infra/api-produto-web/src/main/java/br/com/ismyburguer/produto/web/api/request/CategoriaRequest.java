package br.com.ismyburguer.produto.web.api.request;

import lombok.Getter;

@Getter
public enum CategoriaRequest {

    LANCHE("Lanche"),
    ACOMPANHAMENTO("Acompanhamento"),
    BEBIDA("Bebida"),
    SOBREMESA("Sobremesa");

    private final String categoria;

    CategoriaRequest(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return categoria;
    }
}