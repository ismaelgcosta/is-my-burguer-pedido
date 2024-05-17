package br.com.ismyburguer.pedido.adapters.converter;
import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.ismyburguer.pedido.adapters.entity.ItemPedidoModel;
import br.com.ismyburguer.pedido.entity.ItemPedido;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import org.junit.jupiter.api.Test;

public class ItemPedidoToItemPedidoModelConverterTest {

    private final ItemPedidoToItemPedidoModelConverter converter = new ItemPedidoToItemPedidoModelConverter();

    @Test
    public void convert_ItemPedidoToItemPedidoModel_Success() {
        // Criar um objeto ItemPedido para conversão
        ItemPedido itemPedido = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(ItemPedido.class);

        // Executar a conversão
        ItemPedidoModel itemPedidoModel = converter.convert(itemPedido);

        // Verificar se os atributos são mapeados corretamente
        assertEquals(itemPedido.getItemPedidoId().get().getItemPedidoId(), itemPedidoModel.getItemPedidoId());
        assertEquals(itemPedido.getProdutoId().getProdutoId(), itemPedidoModel.getProdutoId());
        assertEquals(itemPedido.getQuantidade().getValor(), itemPedidoModel.getQuantidade());
        assertEquals(itemPedido.getPreco().getValor(), itemPedidoModel.getPreco());
        assertEquals(itemPedido.getTotal().getValor(), itemPedidoModel.getValorTotal());
    }
}
