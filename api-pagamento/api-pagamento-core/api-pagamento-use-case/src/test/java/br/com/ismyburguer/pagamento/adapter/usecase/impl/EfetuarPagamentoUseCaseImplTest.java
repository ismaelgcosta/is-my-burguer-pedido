package br.com.ismyburguer.pagamento.adapter.usecase.impl;

import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pagamento.gateway.out.EfetuarPagamentoAPI;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EfetuarPagamentoUseCaseImplTest {

    @Mock
    private EfetuarPagamentoAPI efetuarPagamentoAPI;

    @InjectMocks
    private EfetuarPagamentoUseCaseImpl useCase;

    @Test
    public void testPagar() {

        // Objeto Pagamento válido
        Pagamento pagamentoValido = new Pagamento(/* adicione os parâmetros necessários */);

        // Configuração do comportamento da mock API
        UUID uuidEsperado = UUID.randomUUID();
        when(efetuarPagamentoAPI.pagar(any(Pagamento.class))).thenReturn(uuidEsperado);

        // Teste de pagamento bem-sucedido
        UUID resultado = useCase.pagar(pagamentoValido);

        // Verifica se o UUID retornado não é nulo
        assertNotNull(resultado);

        // Verifica se o UUID retornado é igual ao UUID esperado
        assertEquals(uuidEsperado, resultado);
    }
}
