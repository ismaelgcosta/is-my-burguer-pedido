package br.com.ismyburguer.produto.web.api.controller;

import br.com.ismyburguer.produto.adapter.interfaces.in.AlterarProdutoUseCase;
import br.com.ismyburguer.produto.web.api.converter.AlterarProdutoRequestConverter;
import br.com.ismyburguer.produto.web.api.request.AlterarProdutoRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AlterarProdutoAPITest {

    @Mock
    private AlterarProdutoUseCase useCase;

    @Mock
    private AlterarProdutoRequestConverter converter;

    @InjectMocks
    private AlterarProdutoAPI api;

    @Test
    void testAlterarProduto() {
        // Arrange
        String produtoId = "123e4567-e89b-12d3-a456-556642440000";
        AlterarProdutoRequest request = new AlterarProdutoRequest();

        // Act
        api.alterarProduto(produtoId, request);

        // Assert
        verify(converter).convert(request);
        verify(useCase).alterar(eq(produtoId), any());
    }
}
