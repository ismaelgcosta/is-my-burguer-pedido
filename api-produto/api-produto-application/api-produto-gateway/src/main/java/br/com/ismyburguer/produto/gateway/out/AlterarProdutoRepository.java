package br.com.ismyburguer.produto.gateway.out;

import br.com.ismyburguer.produto.entity.Produto;
import jakarta.validation.Valid;

public interface AlterarProdutoRepository {
    void alterarProduto(String produtoId, @Valid Produto produto);
}
