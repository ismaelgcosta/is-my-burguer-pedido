package br.com.ismyburguer.produto.usecase.impl;

import br.com.ismyburguer.produto.gateway.out.InativarProdutoRepository;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class InativarProdutoUseCaseImplTest {

    @Mock
    private InativarProdutoRepository repository;

    @InjectMocks
    private InativarProdutoUseCaseImpl inativarProdutoUseCase;

    private String produtoId;

    @BeforeEach
    public void setUp() {
        produtoId = EnhancedRandom.random(String.class);
    }

    @Test
    public void inativar_DeveChamarRepositorioUmaVez() {
        // Act
        inativarProdutoUseCase.inativar(produtoId);

        // Assert
        verify(repository, times(1)).inativar(produtoId);
    }
}
