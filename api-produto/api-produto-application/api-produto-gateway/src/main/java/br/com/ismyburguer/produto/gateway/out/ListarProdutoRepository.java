package br.com.ismyburguer.produto.gateway.out;

import br.com.ismyburguer.produto.entity.Produto;

import java.util.Collection;

public interface ListarProdutoRepository {
    Collection<Produto> listarProdutosPorCategoria(Produto.Categoria categoria);
}
