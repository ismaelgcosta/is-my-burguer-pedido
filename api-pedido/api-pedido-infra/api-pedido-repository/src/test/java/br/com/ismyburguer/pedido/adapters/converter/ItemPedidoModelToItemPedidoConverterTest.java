package br.com.ismyburguer.pedido.adapters.converter;

import br.com.ismyburguer.core.adapter.Converter;
import br.com.ismyburguer.pedido.adapters.entity.ItemPedidoModel;
import br.com.ismyburguer.pedido.entity.ItemPedido;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemPedidoModelToItemPedidoConverterTest {

    @Test
    public void deveConverterItemPedidoModelParaItemPedidoCorretamente() {
        // Dado
        ItemPedidoModel itemPedidoModel = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(ItemPedidoModel.class);

        // Quando
        Converter<ItemPedidoModel, ItemPedido> converter = new ItemPedidoModelToItemPedidoConverter();
        ItemPedido itemPedido = converter.convert(itemPedidoModel);

        // Então
        assertEquals(itemPedidoModel.getItemPedidoId(), itemPedido.getItemPedidoId().get().getItemPedidoId());
        assertEquals(itemPedidoModel.getProdutoId(), itemPedido.getProdutoId().getProdutoId());
        assertEquals(itemPedidoModel.getPedido().getPedidoId(), itemPedido.getPedidoId().getPedidoId());
        assertEquals(itemPedidoModel.getQuantidade(), itemPedido.getQuantidade().getValor());
        assertEquals(itemPedidoModel.getPreco(), itemPedido.getPreco().getValor());
    }
}
