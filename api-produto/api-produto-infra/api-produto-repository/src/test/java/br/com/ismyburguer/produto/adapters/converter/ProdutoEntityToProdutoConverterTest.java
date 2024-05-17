package br.com.ismyburguer.produto.adapters.converter;

import br.com.ismyburguer.produto.adapters.model.Categoria;
import br.com.ismyburguer.produto.adapters.model.ProdutoModel;
import br.com.ismyburguer.produto.entity.Produto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProdutoEntityToProdutoConverterTest {

    @Test
    void testConvert() {
        // Arrange
        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setProdutoId(UUID.fromString("123e4567-e89b-12d3-a456-556642440000"));
        produtoModel.setDescricao("Teste");
        produtoModel.setCategoria(Categoria.BEBIDA);
        produtoModel.setPreco(BigDecimal.valueOf(10.0));

        ProdutoEntityToProdutoConverter converter = new ProdutoEntityToProdutoConverter();

        // Act
        Produto produto = converter.convert(produtoModel);

        // Assert
        assertEquals("123e4567-e89b-12d3-a456-556642440000", produto.getProdutoId().getProdutoId().toString());
        assertEquals("Teste", produto.getDescricao().getValor());
        assertEquals(Produto.Categoria.BEBIDA, produto.getCategoria());
        assertEquals(BigDecimal.valueOf(10.0), produto.getPreco().getValor());
    }

}
