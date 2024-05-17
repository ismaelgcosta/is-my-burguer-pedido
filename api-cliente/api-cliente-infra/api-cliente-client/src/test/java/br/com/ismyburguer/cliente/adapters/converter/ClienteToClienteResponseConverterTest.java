package br.com.ismyburguer.cliente.adapters.converter;

import br.com.ismyburguer.cliente.adapters.response.ClienteResponse;
import br.com.ismyburguer.cliente.entity.Cliente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class ClienteToClienteResponseConverterTest {

    private final ClienteToClienteResponseConverter converter = new ClienteToClienteResponseConverter();

    @Test
    public void deveConverterClienteParaClienteResponse() {
        // Dado
        UUID clienteId = UUID.randomUUID();
        Cliente cliente = new Cliente(
                new Cliente.ClienteId(clienteId),
                new Cliente.Nome("João", "Silva"),
                new Cliente.Email("joao@example.com"),
                new Cliente.CPF("12345678900"),
                new Cliente.Username("joaosilva")
        );

        // Quando
        ClienteResponse clienteResponse = converter.convert(cliente);

        // Então
        assertEquals("João", clienteResponse.getNome());
        assertEquals("Silva", clienteResponse.getSobrenome());
        assertEquals("joao@example.com", clienteResponse.getEmail());
        assertEquals("12345678900", clienteResponse.getCpf().get());
        assertEquals("joaosilva", clienteResponse.getUsername());
    }

    @Test
    public void deveConverterClienteSemCPFParaClienteResponse() {
        // Dado
        Cliente cliente = new Cliente(
                new Cliente.ClienteId(UUID.randomUUID()),
                new Cliente.Nome("Maria", "Santos"),
                new Cliente.Email("maria@example.com"),
                null,
                new Cliente.Username("mariasantos")
        );

        // Quando
        ClienteResponse clienteResponse = converter.convert(cliente);

        // Então
        assertEquals("Maria", clienteResponse.getNome());
        assertEquals("Santos", clienteResponse.getSobrenome());
        assertEquals("maria@example.com", clienteResponse.getEmail());
        assertEquals(empty(), clienteResponse.getCpf());
        assertEquals("mariasantos", clienteResponse.getUsername());
    }

    @Test
    public void deveConverterClienteSemUsernameParaClienteResponse() {
        // Dado
        Cliente cliente = new Cliente(
                new Cliente.ClienteId(UUID.randomUUID()),
                new Cliente.Nome("Ana", "Souza"),
                new Cliente.Email("ana@example.com"),
                new Cliente.CPF("98765432100"),
                null
        );

        // Quando
        ClienteResponse clienteResponse = converter.convert(cliente);

        // Então
        assertEquals("Ana", clienteResponse.getNome());
        assertEquals("Souza", clienteResponse.getSobrenome());
        assertEquals("ana@example.com", clienteResponse.getEmail());
        assertEquals("98765432100", clienteResponse.getCpf().get());
        assertNull(clienteResponse.getUsername());
    }

    @Test
    public void deveConverterClienteSemCPFENemUsernameParaClienteResponse() {
        // Dado
        Cliente cliente = new Cliente(
                new Cliente.ClienteId(UUID.randomUUID()),
                new Cliente.Nome("Pedro", "Ferreira"),
                new Cliente.Email("pedro@example.com"),
                null,
                null
        );

        // Quando
        ClienteResponse clienteResponse = converter.convert(cliente);

        // Então
        assertEquals("Pedro", clienteResponse.getNome());
        assertEquals("Ferreira", clienteResponse.getSobrenome());
        assertEquals("pedro@example.com", clienteResponse.getEmail());
        assertEquals(empty(), clienteResponse.getCpf());
        assertNull(clienteResponse.getUsername());
    }
}
