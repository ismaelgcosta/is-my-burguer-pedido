package br.com.ismyburguer.pagamento.adapter.usecase.impl;

import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.gateway.out.ConsultarPagamentoAPI;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConsultaPagamentoUseCaseImplTest {

    @Mock
    private ConsultarPagamentoAPI pagamentoAPI;

    @InjectMocks
    private ConsultaPagamentoUseCaseImpl consultaPagamentoUseCase;

    @Test
    public void testConsultarPagamento() {
        // Arrange
        String pagamentoId = "123e4567-e89b-12d3-a456-556642440000";
        Pagamento mockPagamento = new Pagamento();

        // Define o comportamento do mock
        when(pagamentoAPI.consultar(pagamentoId)).thenReturn(mockPagamento);

        // Act
        Pagamento resultado = consultaPagamentoUseCase.consultar(pagamentoId);

        // Assert
        assertNotNull(resultado);
        assertEquals(mockPagamento, resultado);
        verify(pagamentoAPI, times(1)).consultar(pagamentoId);
    }
}
