package br.com.ismyburguer.produto.adapters.repository;

import br.com.ismyburguer.core.exception.EntityNotFoundException;
import br.com.ismyburguer.produto.adapters.converter.ProdutoToProdutoModelConverter;
import br.com.ismyburguer.produto.adapters.model.ProdutoModel;
import br.com.ismyburguer.produto.entity.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlterarProdutoRepositoryImplTest {

    private AlterarProdutoRepositoryImpl repository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ProdutoToProdutoModelConverter converter;

    @BeforeEach
    void setUp() {
        repository = new AlterarProdutoRepositoryImpl(produtoRepository, converter);
    }

    @Test
    void alterarProduto_ProdutoNaoEncontrado_DeveLancarExcecao() {
        // Arrange
        UUID produtoId = UUID.randomUUID();
        Produto produto = new Produto();
        when(produtoRepository.existsById(any())).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> repository.alterarProduto(produtoId.toString(), produto));
    }

    @Test
    void alterarProduto_ProdutoEncontrado_DeveAlterar() {
        // Arrange
        UUID produtoId = UUID.randomUUID();
        Produto produto = new Produto();
        ProdutoModel produtoModel = spy(new ProdutoModel());
        when(produtoRepository.existsById(produtoId)).thenReturn(true);
        when(converter.convert(produto)).thenReturn(produtoModel);

        // Act
        repository.alterarProduto(produtoId.toString(), produto);

        // Assert
        verify(produtoRepository, times(1)).save(produtoModel);
        verify(produtoModel, times(1)).setProdutoId(produtoId);
    }
}
