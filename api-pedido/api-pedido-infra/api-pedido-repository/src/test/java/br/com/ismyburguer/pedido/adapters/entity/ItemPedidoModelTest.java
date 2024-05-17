package br.com.ismyburguer.pedido.adapters.entity;

import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ItemPedidoModelTest {

    @Test
    public void itemPedidoModel_ConstructorWithParameters_Success() {
        // Dados de teste
        UUID itemPedidoId = UUID.randomUUID();
        UUID produtoId = UUID.randomUUID();
        int quantidade = 5;
        BigDecimal preco = new BigDecimal("10.00");
        BigDecimal valorTotal = new BigDecimal("50.00");

        // Criar instância do ItemPedidoModel
        ItemPedidoModel itemPedido = new ItemPedidoModel(itemPedidoId, produtoId, quantidade, preco, valorTotal);

        // Verificar se os atributos foram definidos corretamente
        assertEquals(itemPedidoId, itemPedido.getItemPedidoId());
        assertEquals(produtoId, itemPedido.getProdutoId());
        assertEquals(quantidade, itemPedido.getQuantidade());
        assertEquals(preco, itemPedido.getPreco());
        assertEquals(valorTotal, itemPedido.getValorTotal());
    }

    @Test
    public void itemPedidoModel_ToString_Success() {
        // Gerar um objeto ItemPedidoModel aleatório
        ItemPedidoModel itemPedido = EnhancedRandom.random(ItemPedidoModel.class);

        // Verificar se o método toString não lança exceção
        assertNotNull(itemPedido.toString());
    }
}
