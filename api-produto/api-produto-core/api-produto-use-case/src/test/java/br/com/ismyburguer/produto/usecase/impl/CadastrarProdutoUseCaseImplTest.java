package br.com.ismyburguer.produto.usecase.impl;
import br.com.ismyburguer.produto.entity.Produto;
import br.com.ismyburguer.produto.gateway.out.CadastrarProdutoRepository;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CadastrarProdutoUseCaseImplTest {

    @Mock
    private CadastrarProdutoRepository repository;

    @InjectMocks
    private CadastrarProdutoUseCaseImpl cadastrarProdutoUseCase;

    private Produto produto;

    @BeforeEach
    public void setUp() {
        produto = EnhancedRandom.random(Produto.class);
    }

    @Test
    public void cadastrar_DeveSalvarProdutoNoRepositorioERetornarUUID() {
        // Arrange
        UUID expectedUUID = UUID.randomUUID();
        when(repository.salvarProduto(produto)).thenReturn(expectedUUID);

        // Act
        UUID result = cadastrarProdutoUseCase.cadastrar(produto);

        // Assert
        assertEquals(expectedUUID, result);
        verify(repository, times(1)).salvarProduto(produto);
    }
}
