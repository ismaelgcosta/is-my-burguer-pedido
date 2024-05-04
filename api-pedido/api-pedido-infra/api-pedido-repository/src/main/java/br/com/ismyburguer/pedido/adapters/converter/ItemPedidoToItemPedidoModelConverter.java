package br.com.ismyburguer.pedido.adapters.converter;

import br.com.ismyburguer.core.adapter.Converter;
import br.com.ismyburguer.core.adapter.out.PersistenceConverter;
import br.com.ismyburguer.pedido.adapters.entity.ItemPedidoModel;
import br.com.ismyburguer.pedido.entity.ItemPedido;

@PersistenceConverter
public class ItemPedidoToItemPedidoModelConverter implements Converter<ItemPedido, ItemPedidoModel> {
    @Override
    public ItemPedidoModel convert(ItemPedido source) {
        return new ItemPedidoModel(
                source.getItemPedidoId().map(ItemPedido.ItemPedidoId::getItemPedidoId).orElse(null),
                source.getProdutoId().getProdutoId(),
                source.getQuantidade().getValor(),
                source.getPreco().getValor(),
                source.getTotal().getValor()
        );
    }
}
