package br.com.ismyburguer.pedido.web.api.converter;

import br.com.ismyburguer.core.adapter.Converter;
import br.com.ismyburguer.core.adapter.in.WebConverter;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.web.api.response.BuscarPedidoResponse;
import br.com.ismyburguer.pedido.web.api.response.ListarPedidoResponse;
import jakarta.validation.ConstraintViolationException;

@WebConverter
public class ListarPedidoConverter implements Converter<Pedido, ListarPedidoResponse> {

    @Override
    public ListarPedidoResponse convert(Pedido source) {
        if (source == null) {
            throw new ConstraintViolationException("Não foi informado o corpo da requisição", null);
        }

        if (source != null) {
            source.validate();
        }

        return new ListarPedidoResponse(
                source.getPedidoId().map(Pedido.PedidoId::getPedidoId).map(String::valueOf).orElse(null),
                source.getClienteId().map(Pedido.ClienteId::getClienteId).map(String::valueOf).orElse(null),
                source.getStatusPedido().name(),
                source.getTotal().getValor()
        );
    }
}
