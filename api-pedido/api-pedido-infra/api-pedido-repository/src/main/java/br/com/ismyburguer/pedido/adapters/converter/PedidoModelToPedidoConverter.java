package br.com.ismyburguer.pedido.adapters.converter;

import br.com.ismyburguer.core.adapter.Converter;
import br.com.ismyburguer.core.adapter.out.PersistenceConverter;
import br.com.ismyburguer.pedido.adapters.entity.PedidoModel;
import br.com.ismyburguer.pedido.entity.Pedido;

@PersistenceConverter
public class PedidoModelToPedidoConverter implements Converter<PedidoModel, Pedido> {

    private final ItemPedidoModelToItemPedidoConverter itemPedidoConverter;

    public PedidoModelToPedidoConverter(ItemPedidoModelToItemPedidoConverter itemPedidoConverter) {
        this.itemPedidoConverter = itemPedidoConverter;
    }

    @Override
    public Pedido convert(PedidoModel source) {
        return new Pedido(
                new Pedido.PedidoId(source.getPedidoId()),
                source.getClienteId().map(Pedido.ClienteId::new).orElse(null),
                Pedido.StatusPedido.valueOf(source.getStatusPedido().name()),
                source.getItens().stream().map(itemPedidoConverter::convert).toList()
        );
    }
}
