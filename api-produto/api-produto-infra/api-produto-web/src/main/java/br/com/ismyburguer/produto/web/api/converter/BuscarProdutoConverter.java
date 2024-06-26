package br.com.ismyburguer.produto.web.api.converter;

import br.com.ismyburguer.core.adapter.Converter;
import br.com.ismyburguer.core.adapter.in.WebConverter;
import br.com.ismyburguer.produto.entity.Produto;
import br.com.ismyburguer.produto.web.api.response.BuscarProdutoResponse;
import jakarta.validation.ConstraintViolationException;

@WebConverter
public class BuscarProdutoConverter implements Converter<Produto, BuscarProdutoResponse> {

    @Override
    public BuscarProdutoResponse convert(Produto source) {
        if(source == null) {
            throw new ConstraintViolationException("Não foi informado o corpo da requisição", null);
        }

        if(source != null) {
            source.validate();
        }

        return new BuscarProdutoResponse(
                source.getDescricao().getValor(),
                source.getCategoria().name(),
                source.getPreco().getValor()
        );
    }

}
