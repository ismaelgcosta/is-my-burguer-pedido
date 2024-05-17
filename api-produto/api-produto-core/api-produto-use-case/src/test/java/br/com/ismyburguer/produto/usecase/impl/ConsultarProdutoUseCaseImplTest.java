package br.com.ismyburguer.produto.usecase.impl;
import br.com.ismyburguer.core.exception.EntityNotFoundException;
import br.com.ismyburguer.produto.adapter.interfaces.in.ConsultarProdutoUseCase;
import br.com.ismyburguer.produto.entity.Produto;
import br.com.ismyburguer.produto.gateway.out.ConsultaProdutoRepository;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConsultarProdutoUseCaseImplTest {

    @Mock
    private ConsultaProdutoRepository repository;

    @InjectMocks
    private ConsultarProdutoUseCaseImpl consultarProdutoUseCase;

    private Produto produto;
    private UUID produtoId;

    @BeforeEach
    public void setUp() {
        produto = EnhancedRandom.random(Produto.class);
        produtoId = produto.getProdutoId().getProdutoId();
    }

    @Test
    public void buscar_DeveRetornarProdutoQuandoEncontrado() {
        // Arrange
        ConsultarProdutoUseCase.ConsultaProdutoQuery query = new ConsultarProdutoUseCase.ConsultaProdutoQuery(produtoId.toString());
        when(repository.obterPeloProdutoId(produtoId)).thenReturn(Optional.of(produto));

        // Act
        Produto result = consultarProdutoUseCase.buscar(query);

        // Assert
        assertEquals(produto, result);
        verify(repository, times(1)).obterPeloProdutoId(produtoId);
    }

    @Test
    public void buscar_DeveLancarExcecaoQuandoProdutoNaoEncontrado() {
        // Arrange
        ConsultarProdutoUseCase.ConsultaProdutoQuery query = new ConsultarProdutoUseCase.ConsultaProdutoQuery(produtoId.toString());
        when(repository.obterPeloProdutoId(produtoId)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            consultarProdutoUseCase.buscar(query);
        });
        assertEquals("Produto não foi encontrado", exception.getMessage());
        verify(repository, times(1)).obterPeloProdutoId(produtoId);
    }

    @Test
    public void existsById_DeveRetornarTrueQuandoProdutoExiste() {
        // Arrange
        when(repository.existsById(produtoId)).thenReturn(true);

        // Act
        boolean result = consultarProdutoUseCase.existsById(produtoId);

        // Assert
        assertTrue(result);
        verify(repository, times(1)).existsById(produtoId);
    }

    @Test
    public void existsById_DeveRetornarFalseQuandoProdutoNaoExiste() {
        // Arrange
        when(repository.existsById(produtoId)).thenReturn(false);

        // Act
        boolean result = consultarProdutoUseCase.existsById(produtoId);

        // Assert
        assertFalse(result);
        verify(repository, times(1)).existsById(produtoId);
    }
}
