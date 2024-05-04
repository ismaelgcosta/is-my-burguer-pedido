package br.com.ismyburguer.pedido.adapters.converter;

import br.com.ismyburguer.core.adapter.Converter;
import br.com.ismyburguer.core.adapter.out.PersistenceConverter;
import br.com.ismyburguer.pedido.adapters.entity.ItemPedidoModel;
import br.com.ismyburguer.pedido.entity.ItemPedido;
import br.com.ismyburguer.pedido.entity.Pedido;

@PersistenceConverter
public class ItemPedidoModelToItemPedidoConverter implements Converter<ItemPedidoModel, ItemPedido> {
    @Override
    public ItemPedido convert(ItemPedidoModel source) {
        return new ItemPedido(
                new ItemPedido.ItemPedidoId(source.getItemPedidoId()),
                new Pedido.PedidoId(source.getPedido().getPedidoId()),
                new ItemPedido.ProdutoId(source.getProdutoId()),
                new ItemPedido.Quantidade(source.getQuantidade()),
                new ItemPedido.Preco(source.getPreco())
        );
    }
}
