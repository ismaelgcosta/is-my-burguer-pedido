package br.com.ismyburguer.produto.adapters.repository;

import br.com.ismyburguer.produto.adapters.converter.ProdutoToProdutoModelConverter;
import br.com.ismyburguer.produto.adapters.model.ProdutoModel;
import br.com.ismyburguer.produto.entity.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CadastrarProdutoRepositoryImplTest {

    private CadastrarProdutoRepositoryImpl repository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ProdutoToProdutoModelConverter converter;

    @BeforeEach
    void setUp() {
        repository = new CadastrarProdutoRepositoryImpl(produtoRepository, converter);
    }

    @Test
    void salvarProduto_DeveRetornarIdDoProdutoSalvo() {
        // Arrange
        Produto produto = new Produto();
        ProdutoModel model = new ProdutoModel();
        UUID produtoId = model.getProdutoId();
        when(converter.convert(produto)).thenReturn(model);
        when(produtoRepository.save(model)).thenReturn(model);

        // Act
        UUID result = repository.salvarProduto(produto);

        // Assert
        assertEquals(produtoId, result);
        verify(converter, times(1)).convert(produto);
        verify(produtoRepository, times(1)).save(model);
    }

}
