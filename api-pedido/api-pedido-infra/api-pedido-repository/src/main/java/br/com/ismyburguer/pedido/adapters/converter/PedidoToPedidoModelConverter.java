package br.com.ismyburguer.pedido.adapters.converter;

import br.com.ismyburguer.core.adapter.Converter;
import br.com.ismyburguer.core.adapter.out.PersistenceConverter;
import br.com.ismyburguer.pedido.adapters.entity.PedidoModel;
import br.com.ismyburguer.pedido.adapters.entity.StatusPedidoEntity;
import br.com.ismyburguer.pedido.entity.Pedido;

@PersistenceConverter
public class PedidoToPedidoModelConverter implements Converter<Pedido, PedidoModel> {

    private final ItemPedidoToItemPedidoModelConverter itemPedidoEntityConverter;

    public PedidoToPedidoModelConverter(ItemPedidoToItemPedidoModelConverter itemPedidoEntityConverter) {
        this.itemPedidoEntityConverter = itemPedidoEntityConverter;
    }

    @Override
    public PedidoModel convert(Pedido source) {
        PedidoModel pedidoModel = new PedidoModel(
                source.getClienteId().map(Pedido.ClienteId::getClienteId).orElse(null),
                StatusPedidoEntity.valueOf(source.getStatusPedido().name()),
                source.getTotal().getValor()
        );
        source.getPedidoId().map(Pedido.PedidoId::getPedidoId).ifPresent(pedidoModel::setPedidoId);

        source.getItens()
                .stream()
                .map(itemPedidoEntityConverter::convert)
                .forEach(pedidoModel::addItem);
        return pedidoModel;
    }
}
