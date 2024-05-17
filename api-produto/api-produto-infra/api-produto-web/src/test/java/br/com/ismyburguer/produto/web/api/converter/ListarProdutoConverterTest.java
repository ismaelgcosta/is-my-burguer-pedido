package br.com.ismyburguer.produto.web.api.converter;

import br.com.ismyburguer.produto.entity.Produto;
import br.com.ismyburguer.produto.web.api.response.ListarProdutoResponse;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ListarProdutoConverterTest {

    private ListarProdutoConverter converter;

    @BeforeEach
    void setUp() {
        converter = new ListarProdutoConverter();
    }

    @Test
    void convert_DeveConverterCorretamenteQuandoProdutoValido() {
        // Arrange
        Produto produto = new Produto(
                new Produto.Descricao("Hambúrguer Vegano"),
                Produto.Categoria.LANCHE,
                new Produto.Preco(BigDecimal.valueOf(15.0))
        );
        produto.setProdutoId(new Produto.ProdutoId(UUID.fromString("123e4567-e89b-12d3-a456-556642440000")));

        // Act
        ListarProdutoResponse response = converter.convert(produto);

        // Assert
        assertNotNull(response);
        assertEquals("123e4567-e89b-12d3-a456-556642440000", response.getProdutoId());
        assertEquals("Hambúrguer Vegano", response.getDescricao());
        assertEquals("LANCHE", response.getCategoria());
        assertEquals(BigDecimal.valueOf(15.0), response.getPreco());
    }

    @Test
    void convert_DeveLancarExcecaoQuandoProdutoNulo() {
        // Arrange
        Produto produto = null;

        // Act + Assert
        assertThrows(ConstraintViolationException.class, () -> converter.convert(produto));
    }

    @Test
    void convert_DeveLancarExcecaoQuandoProdutoInvalido() {
        // Arrange
        Produto produto = new Produto();

        // Act + Assert
        assertThrows(ConstraintViolationException.class, () -> converter.convert(produto));
    }

}
