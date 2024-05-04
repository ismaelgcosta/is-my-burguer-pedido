package br.com.ismyburguer.pedido.web.api.converter;

import br.com.ismyburguer.core.adapter.Converter;
import br.com.ismyburguer.core.adapter.in.WebConverter;
import br.com.ismyburguer.pedido.web.api.request.CadastrarPedidoRequest;
import br.com.ismyburguer.pedido.entity.Pedido;
import jakarta.validation.ConstraintViolationException;

import java.util.UUID;

@WebConverter
public class CadastrarPedidoRequestConverter implements Converter<CadastrarPedidoRequest, Pedido> {

    private final CadastrarItemPedidoRequestConverter itemPedidoRequestConverter;

    public CadastrarPedidoRequestConverter(CadastrarItemPedidoRequestConverter itemPedidoRequestConverter) {
        this.itemPedidoRequestConverter = itemPedidoRequestConverter;
    }

    public Pedido convert(CadastrarPedidoRequest source) {
        if (source == null) {
            throw new ConstraintViolationException("Não foi informado o corpo da requisição", null);
        }

        if (source != null) {
            source.validate();
        }

        return new Pedido(
                source.getClienteId().map(UUID::fromString).map(Pedido.ClienteId::new).orElse(null),
                source.getItens()
                        .stream()
                        .map(itemPedidoRequestConverter::convert)
                        .toList()
        );
    }
}
