package br.com.ismyburguer.produto.adapters.converter;

import br.com.ismyburguer.core.adapter.Converter;
import br.com.ismyburguer.core.adapter.out.PersistenceConverter;
import br.com.ismyburguer.produto.adapters.model.Categoria;
import br.com.ismyburguer.produto.adapters.model.ProdutoModel;
import br.com.ismyburguer.produto.entity.Produto;

@PersistenceConverter
public class ProdutoToProdutoModelConverter implements Converter<Produto, ProdutoModel> {

    @Override
    public ProdutoModel convert(Produto source) {
        return new ProdutoModel(
                source.getDescricao().getValor(),
                Categoria.valueOf(source.getCategoria().name()),
                source.getPreco().getValor()
        );
    }

}
