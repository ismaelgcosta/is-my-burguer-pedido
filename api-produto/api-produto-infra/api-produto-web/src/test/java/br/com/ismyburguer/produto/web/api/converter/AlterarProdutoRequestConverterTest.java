package br.com.ismyburguer.produto.web.api.converter;

import br.com.ismyburguer.produto.entity.Produto;
import br.com.ismyburguer.produto.web.api.request.AlterarProdutoRequest;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AlterarProdutoRequestConverterTest {

    private AlterarProdutoRequestConverter converter;

    @BeforeEach
    void setUp() {
        converter = new AlterarProdutoRequestConverter();
    }

    @Test
    void convert_DeveConverterCorretamenteQuandoRequestValido() {
        // Arrange
        AlterarProdutoRequest request = new AlterarProdutoRequest();
        request.setDescricao("Hambúrguer Vegano");
        request.setCategoria("LANCHE");
        request.setPreco(BigDecimal.valueOf(15.0));

        // Act
        Produto produto = converter.convert(request);

        // Assert
        assertNotNull(produto);
        assertEquals("Hambúrguer Vegano", produto.getDescricao().getValor());
        assertEquals(Produto.Categoria.LANCHE, produto.getCategoria());
        assertEquals(BigDecimal.valueOf(15.0), produto.getPreco().getValor());
    }

    @Test
    void convert_DeveLancarExcecaoQuandoRequestNulo() {
        // Arrange
        AlterarProdutoRequest request = null;

        // Act + Assert
        assertThrows(ConstraintViolationException.class, () -> converter.convert(request));
    }

    @Test
    void convert_DeveLancarExcecaoQuandoRequestInvalido() {
        // Arrange
        AlterarProdutoRequest request = new AlterarProdutoRequest();

        // Act + Assert
        assertThrows(ConstraintViolationException.class, () -> converter.convert(request));
    }
}
