package br.com.ismyburguer.pedido.adapters.converter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import br.com.ismyburguer.pedido.adapters.entity.PedidoModel;
import br.com.ismyburguer.pedido.entity.ItemPedido;
import br.com.ismyburguer.pedido.entity.Pedido;
import org.junit.jupiter.api.Test;
import io.github.benas.randombeans.api.EnhancedRandom;

public class PedidoToPedidoModelConverterTest {

    private final ItemPedidoToItemPedidoModelConverter itemPedidoConverter = new ItemPedidoToItemPedidoModelConverter();
    private final PedidoToPedidoModelConverter converter = new PedidoToPedidoModelConverter(itemPedidoConverter);

    @Test
    public void convert_PedidoToPedidoModel_Success() {
        // Gerar um objeto Pedido aleatório
        Pedido pedido = EnhancedRandom.random(Pedido.class);
        pedido.getItens().clear();
        pedido.getItens().add(EnhancedRandom.random(ItemPedido.class));
        pedido.getItens().add(EnhancedRandom.random(ItemPedido.class));
        pedido.getItens().add(EnhancedRandom.random(ItemPedido.class));

        // Executar a conversão
        PedidoModel pedidoModel = converter.convert(pedido);

        // Verificar se os atributos não são nulos
        assertNotNull(pedidoModel.getStatusPedido());
        assertNotNull(pedidoModel.getItens());

        // Verificar se os atributos são mapeados corretamente
        assertEquals(pedido.getStatusPedido().name(), pedidoModel.getStatusPedido().name());
        assertEquals(pedido.getClienteId().map(Pedido.ClienteId::getClienteId).orElse(null), pedidoModel.getClienteId().orElse(null));
        assertEquals(pedido.getTotal().getValor(), pedidoModel.getValorTotal());
        assertEquals(pedido.getItens().size(), pedidoModel.getItens().size());
    }

}
