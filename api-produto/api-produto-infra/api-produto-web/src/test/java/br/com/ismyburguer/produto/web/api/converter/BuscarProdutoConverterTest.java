package br.com.ismyburguer.produto.web.api.converter;

import br.com.ismyburguer.produto.entity.Produto;
import br.com.ismyburguer.produto.web.api.response.BuscarProdutoResponse;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BuscarProdutoConverterTest {

    private BuscarProdutoConverter converter;

    @BeforeEach
    void setUp() {
        converter = new BuscarProdutoConverter();
    }

    @Test
    void convert_DeveConverterCorretamenteQuandoProdutoValido() {
        // Arrange
        Produto produto = new Produto(
                new Produto.Descricao("Hambúrguer Vegano"),
                Produto.Categoria.LANCHE,
                new Produto.Preco(BigDecimal.valueOf(15.0))
        );

        // Act
        BuscarProdutoResponse response = converter.convert(produto);

        // Assert
        assertNotNull(response);
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
