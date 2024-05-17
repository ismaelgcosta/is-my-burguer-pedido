package br.com.ismyburguer.produto.adapters.repository;
import br.com.ismyburguer.core.exception.EntityNotFoundException;
import br.com.ismyburguer.produto.adapters.model.ProdutoModel;
import br.com.ismyburguer.produto.gateway.out.InativarProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InativarProdutoRepositoryImplTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private InativarProdutoRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void inativar_DeveInativarProdutoExistente() {
        // Arrange
        UUID produtoId = UUID.randomUUID();
        ProdutoModel produtoModel = new ProdutoModel();

        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produtoModel));

        // Act
        repository.inativar(produtoId.toString());

        // Assert
        assertFalse(produtoModel.isAtivo());
        verify(produtoRepository, times(1)).findById(produtoId);
        verify(produtoRepository, times(1)).save(produtoModel);
    }

    @Test
    void inativar_DeveLancarEntityNotFoundExceptionQuandoProdutoNaoExistir() {
        // Arrange
        UUID produtoId = UUID.randomUUID();

        when(produtoRepository.findById(produtoId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> repository.inativar(produtoId.toString()));
        verify(produtoRepository, times(1)).findById(produtoId);
        verify(produtoRepository, never()).save(any());
    }

}
