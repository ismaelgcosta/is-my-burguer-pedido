package br.com.ismyburguer.pagamento.adapter.client;
import br.com.ismyburguer.core.adapter.out.FeignClientAPI;
import br.com.ismyburguer.pagamento.adapter.converter.PagamentoToPagamentoRequestConverter;
import br.com.ismyburguer.pagamento.adapter.request.PagamentoRequest;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.operations.SqsTemplate;
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
public class EfetuarPagamentoAPIImplTest {

    @Mock
    private SqsTemplate sqsTemplate;

    @Mock
    private PagamentoToPagamentoRequestConverter converter;

    @InjectMocks
    private EfetuarPagamentoAPIImpl efetuarPagamentoAPI;

    @Mock
    private PagamentoAPI pagamentoAPI;

    @BeforeEach
    void setUp() {
        pagamentoAPI = mock(PagamentoAPI.class);

        efetuarPagamentoAPI = new EfetuarPagamentoAPIImpl(
                sqsTemplate,
                converter,
                new ObjectMapper()
        );
    }
    @Test
    public void deveEfetuarPagamentoComSucesso() {
        // Dados de exemplo para o pagamento
        Pagamento pagamento = new Pagamento(/* adicione os parâmetros necessários */);
        PagamentoRequest pagamentoRequest = new PagamentoRequest(/* adicione os parâmetros necessários */);
        UUID pagamentoId = UUID.randomUUID();

        // Configuração do comportamento do conversor
        when(converter.convert(pagamento)).thenReturn(pagamentoRequest);

        // Configuração do comportamento do Feign Client
        when(pagamentoAPI.save(any(PagamentoRequest.class))).thenReturn(pagamentoId);

        // Execução do método a ser testado
        efetuarPagamentoAPI.pagar(pagamento);

        // Verificação dos resultados
        verify(sqsTemplate).send(pagamentoRequest);
    }

}
