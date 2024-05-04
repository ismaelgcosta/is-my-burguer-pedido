package br.com.ismyburguer.cliente.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class ClienteTest {

    @Test
    public void testGetCpf_RetornaOptionalVazioQuandoCpfNulo() {
        Cliente cliente = new Cliente(new Cliente.Nome("José"));
        assertTrue(cliente.getCpf().isEmpty());
    }

}