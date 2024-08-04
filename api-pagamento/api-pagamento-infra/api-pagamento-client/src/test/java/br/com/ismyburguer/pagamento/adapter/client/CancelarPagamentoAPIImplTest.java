package br.com.ismyburguer.pagamento.adapter.client;

import br.com.ismyburguer.pagamento.adapter.enumeration.StatusPagamento;
import br.com.ismyburguer.pagamento.adapter.request.PagamentoRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.awspring.cloud.sqs.operations.SqsSendOptions;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CancelarPagamentoAPIImplTest {

    @Mock
    private SqsTemplate sqsTemplate;

    @Mock
    private ObjectMapper objectMapper;

    private CancelarPagamentoAPIImpl cancelarPagamentoAPI;
    private ObjectWriter writer;

    @BeforeEach
    void setup() throws JsonProcessingException {
        cancelarPagamentoAPI = new CancelarPagamentoAPIImpl(sqsTemplate, objectMapper);

        writer = mock(ObjectWriter.class);
        lenient().when(objectMapper.writer()).thenReturn(writer);
        lenient().when(writer.withDefaultPrettyPrinter()).thenReturn(writer);
        lenient().when(writer.writeValueAsString(any())).thenReturn("json-string");
    }

    @Test
    void deveEnviarPagamentoRequestParaFilaAoCancelarPagamento() throws JsonProcessingException {
        String pedidoId = UUID.randomUUID().toString();
        PagamentoRequest pagamentoRequest = new PagamentoRequest(UUID.fromString(pedidoId), StatusPagamento.ESTORNADO);
        String mensagemEsperada = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(pagamentoRequest);

        cancelarPagamentoAPI.cancelar(pedidoId);

        verify(sqsTemplate).send(any());
        verify(objectMapper).writer();
        verify(sqsTemplate).send(any());
    }

    @Test
    void deveLancarExcecaoCasoOcorraErroAoSerializarPagamentoRequest() throws JsonProcessingException {
        String pedidoId = UUID.randomUUID().toString();

        lenient().when(writer.writeValueAsString(any()))
                .thenThrow(new JsonProcessingException("Test exception") {
                });

        cancelarPagamentoAPI.setPagamentoQueue("test-queue-url");

        cancelarPagamentoAPI.cancelar(pedidoId);

        // Assert
        ArgumentCaptor<Consumer<SqsSendOptions<Object>>> captor = ArgumentCaptor.forClass(Consumer.class);
        verify(sqsTemplate).send(captor.capture());

        Consumer<SqsSendOptions<Object>> sqsSendOptionsConsumer = captor.getValue();
        SqsSendOptions<Object> sqsSendOptions = mock(SqsSendOptions.class);
        when(sqsSendOptions.queue("test-queue-url")).thenReturn(sqsSendOptions);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                sqsSendOptionsConsumer.accept(sqsSendOptions)
        );
    }

    @Test
    void deveLancarExcecaoCasoOcorraSucessoAoSerializarPagamentoRequest() {
        String pedidoId = UUID.randomUUID().toString();

        cancelarPagamentoAPI.setPagamentoQueue("test-queue-url");

        cancelarPagamentoAPI.cancelar(pedidoId);

        // Assert
        ArgumentCaptor<Consumer<SqsSendOptions<Object>>> captor = ArgumentCaptor.forClass(Consumer.class);
        verify(sqsTemplate).send(captor.capture());

        Consumer<SqsSendOptions<Object>> sqsSendOptionsConsumer = captor.getValue();
        SqsSendOptions<Object> sqsSendOptions = mock(SqsSendOptions.class);
        when(sqsSendOptions.queue("test-queue-url")).thenReturn(sqsSendOptions);

        // Act & Assert
        sqsSendOptionsConsumer.accept(sqsSendOptions);
    }
}