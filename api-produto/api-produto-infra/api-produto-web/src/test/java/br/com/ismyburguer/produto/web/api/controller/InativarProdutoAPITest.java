package br.com.ismyburguer.produto.web.api.controller;

import br.com.ismyburguer.produto.adapter.interfaces.in.InativarProdutoUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class InativarProdutoAPITest {

    @Mock
    private InativarProdutoUseCase inativarProdutoUseCase;

    @InjectMocks
    private InativarProdutoAPI api;

    @Test
    void deveInativarProduto() {
        // Arrange
        String produtoId = "12345678-1234-1234-1234-123456789abc";

        // Act
        api.inativarProduto(produtoId);

        // Assert
        verify(inativarProdutoUseCase, times(1)).inativar(produtoId);
    }
}
