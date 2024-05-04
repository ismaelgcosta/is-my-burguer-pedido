package br.com.ismyburguer.pedido.web.api.converter;

import br.com.ismyburguer.core.adapter.Converter;
import br.com.ismyburguer.core.adapter.in.WebConverter;
import br.com.ismyburguer.pedido.web.api.request.CadastrarItemPedidoRequest;
import br.com.ismyburguer.pedido.entity.ItemPedido;
import jakarta.validation.ConstraintViolationException;

import java.util.UUID;

@WebConverter
public class CadastrarItemPedidoRequestConverter implements Converter<CadastrarItemPedidoRequest, ItemPedido> {

    public CadastrarItemPedidoRequestConverter() {
    }

    public ItemPedido convert(CadastrarItemPedidoRequest source) {
        if(source == null) {
            throw new ConstraintViolationException("Não foi informado o corpo da requisição", null);
        }

        if(source != null) {
            source.validate();
        }

        return new ItemPedido(
                new ItemPedido.ProdutoId(UUID.fromString(source.getProdutoId())),
                new ItemPedido.Quantidade(source.getQuantidade()),
                new ItemPedido.Preco(source.getPreco())
        );
    }
}
