package br.com.ismyburguer.produto.web.api.controller;

import br.com.ismyburguer.produto.adapter.interfaces.in.ListarProdutoUseCase;
import br.com.ismyburguer.produto.entity.Produto;
import br.com.ismyburguer.produto.web.api.converter.ListarProdutoConverter;
import br.com.ismyburguer.produto.web.api.response.ListarProdutoResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListarProdutoAPITest {

    @Mock
    private ListarProdutoUseCase listarProdutoUseCase;

    @Mock
    private ListarProdutoConverter listarProdutoConverter;

    @InjectMocks
    private ListarProdutoAPI api;

    @Test
    void deveListarProdutos() {
        // Arrange
        String categoria = "LANCHE";
        List<ListarProdutoResponse> expectedResult = List.of(new ListarProdutoResponse(), new ListarProdutoResponse());

        when(listarProdutoUseCase.listar(any())).thenReturn(List.of(new Produto(), new Produto()));
        when(listarProdutoConverter.convert(any())).thenReturn(new ListarProdutoResponse());

        // Act
        List<ListarProdutoResponse> result = api.listarProdutos(categoria);

        // Assert
        assertEquals(expectedResult.size(), result.size());
        verify(listarProdutoUseCase).listar(new ListarProdutoUseCase.ListarProdutoQuery(categoria));
        verify(listarProdutoConverter, times(2)).convert(any());
    }
}
