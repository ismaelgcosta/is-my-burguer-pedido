package br.com.ismyburguer.produto.adapters.repository;

import br.com.ismyburguer.core.adapter.out.PersistenceAdapter;
import br.com.ismyburguer.produto.adapters.converter.ProdutoEntityToProdutoConverter;
import br.com.ismyburguer.produto.adapters.model.Categoria;
import br.com.ismyburguer.produto.entity.Produto;
import br.com.ismyburguer.produto.gateway.out.ListarProdutoRepository;

import java.util.Collection;

@PersistenceAdapter
public class ListarProdutoRepositoryImpl implements ListarProdutoRepository {
    private final ProdutoRepository produtoRepository;
    private final ProdutoEntityToProdutoConverter converter;

    public ListarProdutoRepositoryImpl(ProdutoRepository produtoRepository,
                                       ProdutoEntityToProdutoConverter converter) {
        this.produtoRepository = produtoRepository;
        this.converter = converter;
    }

    @Override
    public Collection<Produto> listarProdutosPorCategoria(Produto.Categoria categoria) {
        return produtoRepository
                .findAllByCategoriaAndAtivoIsTrue(Categoria.valueOf(categoria.name()))
                .stream()
                .map(converter::convert)
                .toList();
    }

}
