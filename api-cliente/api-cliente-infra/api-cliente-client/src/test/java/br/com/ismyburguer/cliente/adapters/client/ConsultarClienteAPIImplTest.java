package br.com.ismyburguer.cliente.adapters.client;

import br.com.ismyburguer.cliente.adapters.converter.ClienteResponseToClienteConverter;
import br.com.ismyburguer.cliente.adapters.response.ClienteResponse;
import br.com.ismyburguer.cliente.entity.Cliente;
import br.com.ismyburguer.core.adapter.out.FeignClientAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConsultarClienteAPIImplTest {

    @Mock
    private FeignClientAPI feignClientAPI;

    @Mock
    private ClienteAPI clienteAPI;

    @Mock
    private ClienteResponseToClienteConverter converter;

    @InjectMocks
    private ConsultarClienteAPIImpl consultarClienteAPI;

    @BeforeEach
    void setUp() {
        when(feignClientAPI.createClient(ClienteAPI.class)).thenReturn(clienteAPI);

        consultarClienteAPI = new ConsultarClienteAPIImpl(
                feignClientAPI,
                converter
        );
    }

    @Test
    public void deveRetornarTrueQuandoClienteExistirPeloId() {
        UUID clienteId = UUID.randomUUID();
        when(clienteAPI.existsById(clienteId)).thenReturn(true);

        boolean result = consultarClienteAPI.existsById(clienteId);

        assertEquals(true, result);
        verify(clienteAPI, times(1)).existsById(clienteId);
    }

    @Test
    public void deveRetornarTrueQuandoClienteExistirPeloUsername() {
        String username = "username";
        when(clienteAPI.existsByUsername(username)).thenReturn(true);

        boolean result = consultarClienteAPI.existsByUsername(username);

        assertEquals(true, result);
        verify(clienteAPI, times(1)).existsByUsername(username);
    }

    @Test
    public void deveRetornarClienteQuandoExistirPeloUsername() {
        String username = "username";
        ClienteResponse clienteResponse = new ClienteResponse();
        Cliente cliente = new Cliente();
        when(clienteAPI.findByUsername(username)).thenReturn(clienteResponse);
        when(converter.convert(clienteResponse)).thenReturn(cliente);

        Optional<Cliente> result = consultarClienteAPI.obterPeloClienteUsername(username);

        assertEquals(Optional.of(cliente), result);
        verify(clienteAPI, times(1)).findByUsername(username);
        verify(converter, times(1)).convert(clienteResponse);
    }

    @Test
    public void deveRetornarVazioQuandoClienteNaoExistirPeloUsername() {
        String username = "username";
        when(clienteAPI.findByUsername(username)).thenReturn(null);

        Optional<Cliente> result = consultarClienteAPI.obterPeloClienteUsername(username);

        assertEquals(Optional.empty(), result);
        verify(clienteAPI, times(1)).findByUsername(username);
        verifyNoInteractions(converter);
    }
}
