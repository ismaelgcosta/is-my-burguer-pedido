package br.com.ismyburguer.pagamento.adapter.client;

import br.com.ismyburguer.core.adapter.out.FeignClientAPI;
import br.com.ismyburguer.core.exception.EntityNotFoundException;
import br.com.ismyburguer.pagamento.adapter.converter.PagamentoResponseToPagamentoConverter;
import br.com.ismyburguer.pagamento.adapter.response.PagamentoResponse;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import feign.FeignException;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConsultarPagamentoAPIImplTest {

    @Mock
    private FeignClientAPI feignClientAPI;

    @Mock
    private PagamentoResponseToPagamentoConverter converter;

    @Mock
    private PagamentoAPI pagamentoAPI;

    @InjectMocks
    private ConsultarPagamentoAPIImpl consultarPagamentoAPI;

    @BeforeEach
    void setUp() {
        pagamentoAPI = mock(PagamentoAPI.class);
        when(feignClientAPI.createClient(PagamentoAPI.class)).thenReturn(pagamentoAPI);

        consultarPagamentoAPI = new ConsultarPagamentoAPIImpl(
                feignClientAPI,
                converter
        );
    }
    @Test
    public void deveConsultarPagamentoComSucesso() {
        // Mock dos dados de retorno do pagamento
        UUID pagamentoId = UUID.randomUUID();
        PagamentoResponse pagamentoResponseMock = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(PagamentoResponse.class);
        Pagamento pagamentoMock = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Pagamento.class);

        // Configuração do comportamento do Feign Client
        when(pagamentoAPI.findById(any(UUID.class))).thenReturn(pagamentoResponseMock);

        // Configuração do comportamento do conversor
        when(converter.convert(any(PagamentoResponse.class))).thenReturn(pagamentoMock);

        // Execução do método a ser testado
        Pagamento pagamentoRetornado = consultarPagamentoAPI.consultar(pagamentoId.toString());

        // Verificação dos resultados
        assertNotNull(pagamentoRetornado);
        assertEquals(pagamentoMock, pagamentoRetornado);

        verify(converter).convert(any());
    }

    @Test
    public void deveLancarExcecaoQuandoPagamentoNaoEncontrado() {
        // Mock do UUID do pagamento
        UUID pagamentoId = UUID.randomUUID();

        // Configuração do comportamento do Feign Client para lançar FeignException
        when(pagamentoAPI.findById(any(UUID.class))).thenThrow(FeignException.class);

        // Execução do método a ser testado e verificação da exceção
        assertThrows(EntityNotFoundException.class, () -> consultarPagamentoAPI.consultar(pagamentoId.toString()));
    }

    @Test
    public void deveLancarExcecaoQuandoConversaoFalha() {
        // Mock dos dados de retorno do pagamento
        UUID pagamentoId = UUID.randomUUID();
        PagamentoResponse pagamentoResponseMock = EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(PagamentoResponse.class);

        // Configuração do comportamento do Feign Client
        when(pagamentoAPI.findById(any(UUID.class))).thenReturn(pagamentoResponseMock);

        // Configuração do comportamento do conversor para lançar exceção
        when(converter.convert(any())).thenThrow(RuntimeException.class);

        // Execução do método a ser testado e verificação da exceção
        assertThrows(RuntimeException.class, () -> consultarPagamentoAPI.consultar(pagamentoId.toString()));
    }
}
