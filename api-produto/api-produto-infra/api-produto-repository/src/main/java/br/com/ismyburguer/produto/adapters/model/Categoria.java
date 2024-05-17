package br.com.ismyburguer.produto.adapters.model;

public enum Categoria {

    LANCHE("Lanche"),
    ACOMPANHAMENTO("Acompanhamento"),
    BEBIDA("Bebida"),
    SOBREMESA("Sobremesa");

    private final String categoria;

    Categoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return categoria;
    }
}
