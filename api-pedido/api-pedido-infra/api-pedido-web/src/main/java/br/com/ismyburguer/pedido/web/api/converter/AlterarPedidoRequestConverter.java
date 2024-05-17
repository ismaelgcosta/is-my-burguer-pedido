package br.com.ismyburguer.pedido.web.api.converter;

import br.com.ismyburguer.core.adapter.Converter;
import br.com.ismyburguer.core.adapter.in.WebConverter;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.web.api.request.AlterarPedidoRequest;
import jakarta.validation.ConstraintViolationException;

import java.util.UUID;

@WebConverter
public class AlterarPedidoRequestConverter implements Converter<AlterarPedidoRequest, Pedido> {

    private final AlterarItemPedidoRequestConverter itemPedidoRequestConverter;

    public AlterarPedidoRequestConverter(AlterarItemPedidoRequestConverter itemPedidoRequestConverter) {
        this.itemPedidoRequestConverter = itemPedidoRequestConverter;
    }

    public Pedido convert(AlterarPedidoRequest source) {
        if (source == null) {
            throw new ConstraintViolationException("Não foi informado o corpo da requisição", null);
        }

        if (source != null) {
            source.validate();
        }

        return new Pedido(
                source.getClienteId().map(UUID::fromString).map(Pedido.ClienteId::new).orElse(null),
                null,
                source.getItens()
                        .stream()
                        .map(itemPedidoRequestConverter::convert)
                        .toList()
        );
    }
}
