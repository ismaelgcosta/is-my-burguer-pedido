package br.com.ismyburguer.produto.adapters.converter;

import br.com.ismyburguer.produto.adapters.model.Categoria;
import br.com.ismyburguer.produto.adapters.model.ProdutoModel;
import br.com.ismyburguer.produto.entity.Produto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProdutoToProdutoModelConverterTest {

    @Test
    void testConvert() {
        // Arrange
        Produto produto = new Produto(
                new Produto.ProdutoId(UUID.fromString("123e4567-e89b-12d3-a456-556642440000")),
                new Produto.Descricao("Teste"),
                Produto.Categoria.ACOMPANHAMENTO,
                new Produto.Preco(BigDecimal.valueOf(10.0))
        );

        ProdutoToProdutoModelConverter converter = new ProdutoToProdutoModelConverter();

        // Act
        ProdutoModel produtoModel = converter.convert(produto);

        // Assert
        assertEquals("Teste", produtoModel.getDescricao());
        assertEquals(Categoria.ACOMPANHAMENTO, produtoModel.getCategoria());
        assertEquals(BigDecimal.valueOf(10.0), produtoModel.getPreco());
    }
}
