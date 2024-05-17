package br.com.ismyburguer.produto.web.api.converter;

import br.com.ismyburguer.core.adapter.Converter;
import br.com.ismyburguer.core.adapter.in.WebConverter;
import br.com.ismyburguer.produto.entity.Produto;
import br.com.ismyburguer.produto.web.api.response.ListarProdutoResponse;
import jakarta.validation.ConstraintViolationException;

@WebConverter
public class ListarProdutoConverter implements Converter<Produto, ListarProdutoResponse> {

    @Override
    public ListarProdutoResponse convert(Produto source) {
        if(source == null) {
            throw new ConstraintViolationException("Não foi informado o corpo da requisição", null);
        }

        if(source != null) {
            source.validate();
        }

        return new ListarProdutoResponse(
                source.getProdutoId().getProdutoId().toString(),
                source.getDescricao().getValor(),
                source.getCategoria().name(),
                source.getPreco().getValor()
        );
    }

}
