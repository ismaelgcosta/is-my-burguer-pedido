package br.com.ismyburguer.cliente.entity;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
class ClienteTest {

    @Test
    public void testGetCpf_RetornaOptionalVazioQuandoCpfNulo() {
        Cliente cliente = new Cliente(new Cliente.Nome("José"));
        assertTrue(cliente.getCpf().isEmpty());
    }

    @Test
    void deveRespeitarAsRegrasMinimas() {
        EnhancedRandomBuilder.aNewEnhancedRandom().nextObject(Cliente.class);
        GetterSetterVerifier.forClass(Cliente.class).verify();
        EqualsVerifier.forClass(Cliente.class).suppress(
                Warning.STRICT_INHERITANCE,
                Warning.INHERITED_DIRECTLY_FROM_OBJECT,
                Warning.ALL_FIELDS_SHOULD_BE_USED,
                Warning.BIGDECIMAL_EQUALITY,
                Warning.NONFINAL_FIELDS).verify();

        Cliente.ClienteId clienteId = new Cliente.ClienteId(UUID.randomUUID());
        Cliente cliente = new Cliente(
                clienteId,
                new Cliente.Nome("nome"),
                new Cliente.Email("meuemail@mail.com.br"),
                new Cliente.CPF("12345678909"),
                new Cliente.Username("username"));
        assertEquals(clienteId, cliente.getClienteId());
        //assertEquals(Cliente.StatusCliente.FECHADO, cliente.getStatusCliente());

    }

}