package br.com.ismyburguer.produto.web.api.controller;

import br.com.ismyburguer.produto.adapter.interfaces.in.CadastrarProdutoUseCase;
import br.com.ismyburguer.produto.entity.Produto;
import br.com.ismyburguer.produto.web.api.converter.CadastrarProdutoRequestConverter;
import br.com.ismyburguer.produto.web.api.request.CriarProdutoRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CadastrarProdutoAPITest {

    @Mock
    private CadastrarProdutoUseCase cadastrarProdutoUseCase;

    @Mock
    private CadastrarProdutoRequestConverter cadastrarProdutoRequestConverter;

    @InjectMocks
    private CadastrarProdutoAPI api;

    @Test
    void deveCadastrarProduto() {
        // Arrange
        CriarProdutoRequest request = new CriarProdutoRequest();
        Produto produto = new Produto();
        UUID produtoId = UUID.randomUUID();
        when(cadastrarProdutoRequestConverter.convert(request)).thenReturn(produto);
        when(cadastrarProdutoUseCase.cadastrar(any(Produto.class))).thenReturn(produtoId);

        // Act
        UUID result = api.cadastrarProduto(request);

        // Assert
        assertEquals(produtoId, result);
    }
}
