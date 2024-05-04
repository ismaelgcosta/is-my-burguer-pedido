package br.com.ismyburguer.produto.adapters.converter;

import br.com.ismyburguer.core.adapter.Converter;
import br.com.ismyburguer.core.adapter.out.PersistenceConverter;
import br.com.ismyburguer.produto.adapters.model.ProdutoModel;
import br.com.ismyburguer.produto.entity.Produto;

@PersistenceConverter
public class ProdutoEntityToProdutoConverter implements Converter<ProdutoModel, Produto> {
    @Override
    public Produto convert(ProdutoModel source) {
        return new Produto(
                new Produto.ProdutoId(source.getProdutoId()),
                new Produto.Descricao(source.getDescricao()),
                Produto.Categoria.valueOf(source.getCategoria().name()),
                new Produto.Preco(source.getPreco())
        );
    }
}
