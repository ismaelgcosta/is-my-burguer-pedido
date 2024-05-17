package br.com.ismyburguer.cliente.adapters.converter;

import br.com.ismyburguer.cliente.adapters.response.ClienteResponse;
import br.com.ismyburguer.cliente.entity.Cliente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ClienteResponseToClienteConverterTest {

    private final ClienteResponseToClienteConverter converter = new ClienteResponseToClienteConverter();

    @Test
    public void deveConverterClienteResponseParaCliente() {
        // Dado
        ClienteResponse clienteResponse = new ClienteResponse();
        UUID clienteId = UUID.randomUUID();
        clienteResponse.setClienteId(clienteId);
        clienteResponse.setNome("João");
        clienteResponse.setSobrenome("Silva");
        clienteResponse.setEmail("joao@example.com");
        clienteResponse.setUsername("joaosilva");
        clienteResponse.setCpf("12345678900");

        // Quando
        Cliente cliente = converter.convert(clienteResponse);

        // Então
        assertEquals(clienteId, cliente.getClienteId().getClienteId());
        assertEquals("João", cliente.getNome().getNome());
        assertEquals("Silva", cliente.getNome().getSobrenome());
        assertEquals("joao@example.com", cliente.getEmail().getEndereco());
        assertEquals("12345678900", cliente.getCpf().get().getNumero());
        assertEquals("joaosilva", cliente.getUsername().get().getUsername());
    }

    @Test
    public void deveConverterClienteResponseSemCPFParaCliente() {
        // Dado
        ClienteResponse clienteResponse = new ClienteResponse();
        UUID clienteId = UUID.randomUUID();
        clienteResponse.setClienteId(clienteId);
        clienteResponse.setNome("Maria");
        clienteResponse.setSobrenome("Santos");
        clienteResponse.setEmail("maria@example.com");
        clienteResponse.setUsername("mariasantos");

        // Quando
        Cliente cliente = converter.convert(clienteResponse);

        // Então
        assertEquals(clienteId, cliente.getClienteId().getClienteId());
        assertEquals("Maria", cliente.getNome().getNome());
        assertEquals("Santos", cliente.getNome().getSobrenome());
        assertEquals("maria@example.com", cliente.getEmail().getEndereco());
        assertEquals(Optional.empty(), cliente.getCpf());
        assertEquals("mariasantos", cliente.getUsername().get().getUsername());
    }

    @Test
    public void deveConverterClienteResponseSemUsernameParaCliente() {
        // Dado
        ClienteResponse clienteResponse = new ClienteResponse();
        UUID clienteId = UUID.randomUUID();
        clienteResponse.setClienteId(clienteId);
        clienteResponse.setNome("Ana");
        clienteResponse.setSobrenome("Souza");
        clienteResponse.setEmail("ana@example.com");
        clienteResponse.setCpf("98765432100");

        // Quando
        Cliente cliente = converter.convert(clienteResponse);

        // Então
        assertEquals(clienteId, cliente.getClienteId().getClienteId());
        assertEquals("Ana", cliente.getNome().getNome());
        assertEquals("Souza", cliente.getNome().getSobrenome());
        assertEquals("ana@example.com", cliente.getEmail().getEndereco());
        assertEquals("98765432100", cliente.getCpf().get().getNumero());
        assertEquals(Optional.empty(), cliente.getUsername());
    }

    @Test
    public void deveConverterClienteResponseSemCPFENemUsernameParaCliente() {
        // Dado
        ClienteResponse clienteResponse = new ClienteResponse();
        UUID clienteId = UUID.randomUUID();
        clienteResponse.setClienteId(clienteId);
        clienteResponse.setNome("Pedro");
        clienteResponse.setSobrenome("Ferreira");
        clienteResponse.setEmail("pedro@example.com");

        // Quando
        Cliente cliente = converter.convert(clienteResponse);

        // Então
        assertEquals(clienteId, cliente.getClienteId().getClienteId());
        assertEquals("Pedro", cliente.getNome().getNome());
        assertEquals("Ferreira", cliente.getNome().getSobrenome());
        assertEquals("pedro@example.com", cliente.getEmail().getEndereco());

        assertEquals(Optional.empty(), cliente.getCpf());
        assertEquals(Optional.empty(), cliente.getUsername());
    }
}
