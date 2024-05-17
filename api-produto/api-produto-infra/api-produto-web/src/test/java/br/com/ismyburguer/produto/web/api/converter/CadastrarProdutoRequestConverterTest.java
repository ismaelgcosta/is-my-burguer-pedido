package br.com.ismyburguer.produto.web.api.converter;

import br.com.ismyburguer.produto.entity.Produto;
import br.com.ismyburguer.produto.web.api.request.CriarProdutoRequest;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CadastrarProdutoRequestConverterTest {

    private CadastrarProdutoRequestConverter converter;

    @BeforeEach
    void setUp() {
        converter = new CadastrarProdutoRequestConverter();
    }

    @Test
    void convert_DeveConverterCorretamenteQuandoProdutoValido() {
        // Arrange
        CriarProdutoRequest request = new CriarProdutoRequest();
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
        CriarProdutoRequest request = null;

        // Act + Assert
        assertThrows(ConstraintViolationException.class, () -> converter.convert(request));
    }

    @Test
    void convert_DeveLancarExcecaoQuandoRequestInvalido() {
        // Arrange
        CriarProdutoRequest request = new CriarProdutoRequest();

        // Act + Assert
        assertThrows(ConstraintViolationException.class, () -> converter.convert(request));
    }

}
