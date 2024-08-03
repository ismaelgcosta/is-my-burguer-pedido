package br.com.ismyburguer.pagamento.adapter.client;

import br.com.ismyburguer.pagamento.adapter.converter.PagamentoToPagamentoRequestConverter;
import br.com.ismyburguer.pagamento.adapter.request.PagamentoRequest;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.awspring.cloud.sqs.operations.SqsSendOptions;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;
import java.util.function.Consumer;

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
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        pagamentoAPI = mock(PagamentoAPI.class);
        objectMapper = spy(new ObjectMapper());

        efetuarPagamentoAPI = new EfetuarPagamentoAPIImpl(
                sqsTemplate,
                converter,
                objectMapper
        );

        efetuarPagamentoAPI.setPagamentoQueue("test-queue-url");
    }

    @Test
    public void deveEfetuarPagamentoComSucesso() throws JsonProcessingException {
        // Dados de exemplo para o pagamento
        Pagamento pagamento = new Pagamento(/* adicione os parâmetros necessários */);
        PagamentoRequest pagamentoRequest = new PagamentoRequest(/* adicione os parâmetros necessários */);

        // Configuração do comportamento do conversor
        when(converter.convert(pagamento)).thenReturn(pagamentoRequest);

        ObjectWriter writer = mock(ObjectWriter.class);
        lenient().when(objectMapper.writer()).thenReturn(writer);
        lenient().when(writer.withDefaultPrettyPrinter()).thenReturn(writer);
        lenient().when(writer.writeValueAsString(pagamentoRequest)).thenReturn("json-string");

        // Execução do método a ser testado
        efetuarPagamentoAPI.pagar(pagamento);

        // Assert
        ArgumentCaptor<Consumer<SqsSendOptions<Object>>> captor = ArgumentCaptor.forClass(Consumer.class);
        verify(sqsTemplate).send(captor.capture());

        Consumer<SqsSendOptions<Object>> sqsSendOptionsConsumer = captor.getValue();
        SqsSendOptions<Object> sqsSendOptions = mock(SqsSendOptions.class);
        when(sqsSendOptions.queue("test-queue-url")).thenReturn(sqsSendOptions);
        when(sqsSendOptions.payload(any())).thenReturn(sqsSendOptions);

        // Invoke the consumer to verify its behavior
        sqsSendOptionsConsumer.accept(sqsSendOptions);

        verify(sqsSendOptions).queue("test-queue-url");
        verify(sqsSendOptions).payload(any());

    }

}
