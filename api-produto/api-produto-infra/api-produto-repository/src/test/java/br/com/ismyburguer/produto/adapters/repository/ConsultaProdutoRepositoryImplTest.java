package br.com.ismyburguer.produto.adapters.repository;
import br.com.ismyburguer.produto.adapters.converter.ProdutoEntityToProdutoConverter;
import br.com.ismyburguer.produto.adapters.model.ProdutoModel;
import br.com.ismyburguer.produto.entity.Produto;
import br.com.ismyburguer.produto.gateway.out.ConsultaProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultaProdutoRepositoryImplTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ProdutoEntityToProdutoConverter converter;

    @InjectMocks
    private ConsultaProdutoRepositoryImpl repository;

    @Test
    void obterPeloProdutoId_DeveRetornarProdutoQuandoEncontrado() {
        // Arrange
        UUID produtoId = UUID.randomUUID();
        ProdutoModel produtoModel = new ProdutoModel();
        Produto produto = new Produto();

        when(produtoRepository.findByProdutoIdAndAtivo(produtoId, true)).thenReturn(Optional.of(produtoModel));
        when(converter.convert(produtoModel)).thenReturn(produto);

        // Act
        Optional<Produto> result = repository.obterPeloProdutoId(produtoId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(produto, result.get());
        verify(produtoRepository, times(1)).findByProdutoIdAndAtivo(produtoId, true);
        verify(converter, times(1)).convert(produtoModel);
    }

    @Test
    void obterPeloProdutoId_DeveRetornarVazioQuandoNaoEncontrado() {
        // Arrange
        UUID produtoId = UUID.randomUUID();

        when(produtoRepository.findByProdutoIdAndAtivo(produtoId, true)).thenReturn(Optional.empty());

        // Act
        Optional<Produto> result = repository.obterPeloProdutoId(produtoId);

        // Assert
        assertTrue(result.isEmpty());
        verify(produtoRepository, times(1)).findByProdutoIdAndAtivo(produtoId, true);
        verify(converter, never()).convert(any());
    }

    @Test
    void existsById_DeveRetornarTrueQuandoProdutoExiste() {
        // Arrange
        UUID produtoId = UUID.randomUUID();

        when(produtoRepository.existsByProdutoIdAndAtivo(produtoId, true)).thenReturn(true);

        // Act
        boolean result = repository.existsById(produtoId);

        // Assert
        assertTrue(result);
        verify(produtoRepository, times(1)).existsByProdutoIdAndAtivo(produtoId, true);
    }

    @Test
    void existsById_DeveRetornarFalseQuandoProdutoNaoExiste() {
        // Arrange
        UUID produtoId = UUID.randomUUID();

        when(produtoRepository.existsByProdutoIdAndAtivo(produtoId, true)).thenReturn(false);

        // Act
        boolean result = repository.existsById(produtoId);

        // Assert
        assertFalse(result);
        verify(produtoRepository, times(1)).existsByProdutoIdAndAtivo(produtoId, true);
    }

}
