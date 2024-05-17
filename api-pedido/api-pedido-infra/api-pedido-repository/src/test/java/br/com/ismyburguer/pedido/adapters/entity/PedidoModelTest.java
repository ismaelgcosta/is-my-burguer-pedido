package br.com.ismyburguer.pedido.adapters.entity;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import io.github.benas.randombeans.api.EnhancedRandom;

import java.math.BigDecimal;
import java.util.UUID;

public class PedidoModelTest {

    @Test
    public void pedidoModel_ConstrutorComParametros_Sucesso() {
        // Dados de teste
        UUID clienteId = UUID.randomUUID();
        StatusPedidoEntity statusPedido = StatusPedidoEntity.ABERTO;
        BigDecimal valorTotal = new BigDecimal("100.00");

        // Criar instância do PedidoModel
        PedidoModel pedido = new PedidoModel(clienteId, statusPedido, valorTotal);

        // Verificar se os atributos foram definidos corretamente
        assertEquals(clienteId, pedido.getClienteId().orElse(null));
        assertEquals(statusPedido, pedido.getStatusPedido());
        assertEquals(valorTotal, pedido.getValorTotal());
    }

    @Test
    public void pedidoModel_LimparItens_Sucesso() {
        // Gerar um objeto PedidoModel com itens aleatórios
        PedidoModel pedido = EnhancedRandom.random(PedidoModel.class);
        assertNotNull(pedido.getItens());

        // Limpar os itens do pedido
        pedido.limparItens();

        // Verificar se os itens foram removidos com sucesso
        assertTrue(pedido.getItens().isEmpty());
    }

    @Test
    public void pedidoModel_AddItem_Sucesso() {
        // Gerar um objeto PedidoModel e um objeto ItemPedidoModel aleatórios
        PedidoModel pedido = EnhancedRandom.random(PedidoModel.class);
        ItemPedidoModel itemPedido = EnhancedRandom.random(ItemPedidoModel.class);

        // Adicionar o item ao pedido
        pedido.addItem(itemPedido);

        // Verificar se o item foi adicionado corretamente
        assertTrue(pedido.getItens().contains(itemPedido));
        assertEquals(pedido, itemPedido.getPedido());
    }
}
