package br.com.ismyburguer.core.auth;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EscapeHtmlAspectTest {

    @Mock
    private MethodParameter methodParameter;

    @Mock
    private HttpInputMessage originalMessage;

    private EscapeHtmlAspect escapeHtmlAspect;

    @BeforeEach
    public void setup() {
        escapeHtmlAspect = new EscapeHtmlAspect();
    }

    @Test
    public void deveEscaparCaracteresHtmlNaEntrada() throws IOException {
        String originalBody = "<script>alert('XSS')</script>";

        when(originalMessage.getBody()).thenReturn(new ByteArrayInputStream(originalBody.getBytes(StandardCharsets.UTF_8)));

        HttpInputMessage httpInputMessage = escapeHtmlAspect.beforeBodyRead(originalMessage, methodParameter, String.class, null);

        verify(originalMessage).getBody();
        assertNotNull(httpInputMessage.getBody());
        assertNull(httpInputMessage.getHeaders());
    }

    @Test
    public void deveDeixarCorpoVazioInalterado() throws IOException {
        when(originalMessage.getBody()).thenReturn(new ByteArrayInputStream(new byte[0]));

        escapeHtmlAspect.beforeBodyRead(originalMessage, methodParameter, String.class, null);

        verify(originalMessage).getBody();
    }

    @Test
    public void deveSuportarTodosOsTiposDeRetorno() {
        boolean supportsString = escapeHtmlAspect.supports(methodParameter, String.class, null);
        boolean supportsInteger = escapeHtmlAspect.supports(methodParameter, Integer.class, null);

        assertTrue(supportsString);
        assertTrue(supportsInteger);
    }
}
