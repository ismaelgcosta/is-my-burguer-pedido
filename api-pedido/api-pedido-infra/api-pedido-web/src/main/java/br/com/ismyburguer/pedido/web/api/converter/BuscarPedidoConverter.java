package br.com.ismyburguer.pedido.web.api.converter;

import br.com.ismyburguer.core.adapter.Converter;
import br.com.ismyburguer.core.adapter.in.WebConverter;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.web.api.response.BuscarPedidoResponse;
import jakarta.validation.ConstraintViolationException;

@WebConverter
public class BuscarPedidoConverter implements Converter<Pedido, BuscarPedidoResponse> {

    private final BuscarItemPedidoConverter buscarItemPedidoConverter;

    public BuscarPedidoConverter(BuscarItemPedidoConverter buscarItemPedidoConverter) {
        this.buscarItemPedidoConverter = buscarItemPedidoConverter;
    }

    @Override
    public BuscarPedidoResponse convert(Pedido source) {
        if (source == null) {
            throw new ConstraintViolationException("Não foi informado o corpo da requisição", null);
        }

        if (source != null) {
            source.validate();
        }

        return new BuscarPedidoResponse(
                source.getPedidoId().map(Pedido.PedidoId::getPedidoId).map(String::valueOf).orElse(null),
                source.getClienteId().map(Pedido.ClienteId::getClienteId).map(String::valueOf).orElse(null),
                source.getItens()
                        .stream()
                        .map(buscarItemPedidoConverter::convert)
                        .toList(),
                source.getStatusPedido().name(),
                source.getTotal().getValor()
        );
    }
}
