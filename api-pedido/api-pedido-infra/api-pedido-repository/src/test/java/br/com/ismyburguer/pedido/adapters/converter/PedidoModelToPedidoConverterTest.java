package br.com.ismyburguer.pedido.adapters.converter;

import br.com.ismyburguer.pedido.adapters.entity.PedidoModel;
import br.com.ismyburguer.pedido.entity.Pedido;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PedidoModelToPedidoConverterTest {

    private final ItemPedidoModelToItemPedidoConverter itemPedidoConverter = new ItemPedidoModelToItemPedidoConverter();
    private final PedidoModelToPedidoConverter converter = new PedidoModelToPedidoConverter(itemPedidoConverter);

    @Test
    public void convert_PedidoModelToPedido_Success() {
        // Gerar um objeto PedidoModel aleatório
        PedidoModel pedidoModel = EnhancedRandom.random(PedidoModel.class);

        // Executar a conversão
        Pedido pedido = converter.convert(pedidoModel);

        // Verificar se os atributos não são nulos
        assertNotNull(pedido.getPedidoId());
        assertNotNull(pedido.getStatusPedido());
        assertNotNull(pedido.getItens());

        // Verificar se os atributos são mapeados corretamente
        assertEquals(pedidoModel.getPedidoId(), pedido.getPedidoId().get().getPedidoId());
        assertEquals(pedidoModel.getStatusPedido().name(), pedido.getStatusPedido().name());
        assertEquals(pedidoModel.getClienteId().orElse(null), pedido.getClienteId().map(Pedido.ClienteId::getClienteId).orElse(null));
        assertEquals(pedidoModel.getItens().size(), pedido.getItens().size());
    }

}
