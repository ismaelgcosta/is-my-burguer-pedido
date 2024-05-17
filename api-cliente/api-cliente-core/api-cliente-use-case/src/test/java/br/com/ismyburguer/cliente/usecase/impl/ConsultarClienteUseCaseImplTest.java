package br.com.ismyburguer.cliente.usecase.impl;

import br.com.ismyburguer.cliente.adapter.interfaces.out.ConsultarClienteUseCase;
import br.com.ismyburguer.cliente.entity.Cliente;
import br.com.ismyburguer.cliente.gateway.out.ConsultarClienteAPI;
import br.com.ismyburguer.core.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConsultarClienteUseCaseImplTest {

    @Mock
    private ConsultarClienteAPI api;

    @InjectMocks
    private ConsultarClienteUseCaseImpl useCase;

    private static final UUID CLIENTE_ID = UUID.randomUUID();
    private static final String USERNAME = "johndoe";

    @Test
    void deveBuscarClientePorUsername() {
        // Dado que a API retorna um cliente com o username fornecido
        Cliente cliente = new Cliente(new Cliente.Nome("nome"));
        cliente.setUsername(new Cliente.Username(USERNAME));
        cliente.setClienteId(new Cliente.ClienteId(CLIENTE_ID));
        when(api.obterPeloClienteUsername(USERNAME)).thenReturn(Optional.of(cliente));

        // Quando
        Cliente resultado = useCase.buscarPorUsername(new ConsultarClienteUseCase.ConsultaClientePorUsername(USERNAME));

        // Então
        assertNotNull(resultado);
        assertEquals(new Cliente.ClienteId(CLIENTE_ID), resultado.getClienteId());
        assertEquals(new Cliente.Username(USERNAME), resultado.getUsername().get());
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoEncontradoPorUsername() {
        // Dado que a API não retorna nenhum cliente para o username fornecido
        when(api.obterPeloClienteUsername(USERNAME)).thenReturn(Optional.empty());

        // Quando e Então
        assertThrows(EntityNotFoundException.class, () -> useCase.buscarPorUsername(new ConsultarClienteUseCase.ConsultaClientePorUsername(USERNAME)));
    }

    @Test
    void deveVerificarSeClienteExistePorId() {
        // Dado que a API indica que o cliente com o ID fornecido existe
        when(api.existsById(CLIENTE_ID)).thenReturn(true);

        // Quando
        boolean resultado = useCase.existsById(CLIENTE_ID);

        // Então
        assertTrue(resultado);
    }

    @Test
    void deveVerificarSeClienteNaoExistePorId() {
        // Dado que a API indica que o cliente com o ID fornecido não existe
        when(api.existsById(CLIENTE_ID)).thenReturn(false);

        // Quando
        boolean resultado = useCase.existsById(CLIENTE_ID);

        // Então
        assertFalse(resultado);
    }

    @Test
    void deveVerificarSeClienteExistePorUsername() {
        // Dado que a API indica que o cliente com o username fornecido existe
        when(api.existsByUsername(USERNAME)).thenReturn(true);

        // Quando
        boolean resultado = useCase.existsByUsername(USERNAME);

        // Então
        assertTrue(resultado);
    }

    @Test
    void deveVerificarSeClienteNaoExistePorUsername() {
        // Dado que a API indica que o cliente com o username fornecido não existe
        when(api.existsByUsername(USERNAME)).thenReturn(false);

        // Quando
        boolean resultado = useCase.existsByUsername(USERNAME);

        // Então
        assertFalse(resultado);
    }
}
