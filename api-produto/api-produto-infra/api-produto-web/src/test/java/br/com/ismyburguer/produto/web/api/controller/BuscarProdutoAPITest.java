package br.com.ismyburguer.produto.web.api.controller;

import br.com.ismyburguer.produto.adapter.interfaces.in.ConsultarProdutoUseCase;
import br.com.ismyburguer.produto.entity.Produto;
import br.com.ismyburguer.produto.web.api.converter.BuscarProdutoConverter;
import br.com.ismyburguer.produto.web.api.response.BuscarProdutoResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BuscarProdutoAPITest {

    @Mock
    private ConsultarProdutoUseCase consultarProdutoUseCase;

    @Mock
    private BuscarProdutoConverter buscarProdutoConverter;

    @InjectMocks
    private BuscarProdutoAPI api;

    @Test
    void testObterProduto() {
        // Arrange
        String produtoId = "123e4567-e89b-12d3-a456-556642440000";
        ConsultarProdutoUseCase.ConsultaProdutoQuery query = new ConsultarProdutoUseCase.ConsultaProdutoQuery(produtoId);
        BuscarProdutoResponse expectedResponse = new BuscarProdutoResponse();
        when(consultarProdutoUseCase.buscar(query)).thenReturn(new Produto());
        when(buscarProdutoConverter.convert(any(Produto.class))).thenReturn(expectedResponse);

        // Act
        BuscarProdutoResponse response = api.obterProduto(produtoId);

        // Assert
        assertEquals(expectedResponse, response);
    }
}
