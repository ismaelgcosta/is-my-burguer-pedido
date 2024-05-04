package br.com.ismyburguer.produto.adapter.interfaces.in;

import br.com.ismyburguer.produto.entity.Produto;

public interface AlterarProdutoUseCase {
    void alterar(String produtoId, Produto produto);
}
