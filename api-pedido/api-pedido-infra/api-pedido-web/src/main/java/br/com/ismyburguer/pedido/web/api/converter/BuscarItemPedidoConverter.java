package br.com.ismyburguer.pedido.web.api.converter;

import br.com.ismyburguer.core.adapter.Converter;
import br.com.ismyburguer.core.adapter.in.WebConverter;
import br.com.ismyburguer.pedido.entity.ItemPedido;
import br.com.ismyburguer.pedido.web.api.response.BuscarItemPedidoResponse;
import jakarta.validation.ConstraintViolationException;

@WebConverter
public class BuscarItemPedidoConverter implements Converter<ItemPedido, BuscarItemPedidoResponse> {

    public BuscarItemPedidoResponse convert(ItemPedido source) {
        if (source == null) {
            throw new ConstraintViolationException("Não foi informado o corpo da requisição", null);
        }

        if (source != null) {
            source.validate();
        }

        return new BuscarItemPedidoResponse(
                source.getItemPedidoId().get().getItemPedidoId().toString(),
                source.getProdutoId().getProdutoId().toString(),
                source.getQuantidade().getValor(),
                source.getPreco().getValor(),
                source.getTotal().getValor()
        );
    }
}
