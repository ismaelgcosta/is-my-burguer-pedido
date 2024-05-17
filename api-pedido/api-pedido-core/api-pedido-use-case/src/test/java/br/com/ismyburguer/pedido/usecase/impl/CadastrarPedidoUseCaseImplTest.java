package br.com.ismyburguer.pedido.usecase.impl;

import br.com.ismyburguer.cliente.adapter.interfaces.out.ConsultarClienteUseCase;
import br.com.ismyburguer.cliente.entity.Cliente;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.gateway.out.CadastrarPedidoRepository;
import br.com.ismyburguer.pedido.usecase.validation.PedidoValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CadastrarPedidoUseCaseImplTest {

    @Mock
    private CadastrarPedidoRepository repository;

    @Mock
    private PedidoValidator validator;

    @Mock
    private ConsultarClienteUseCase consultarClienteUseCase;

    @InjectMocks
    private CadastrarPedidoUseCaseImpl useCase;

    @Mock
    private Jwt jwt;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @BeforeAll
    static void beforeAll() {
        mockStatic(SecurityContextHolder.class);
    }

    @BeforeEach
    void setUp() {
        lenient().when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        lenient().when(securityContext.getAuthentication()).thenReturn(authentication);
        lenient().when(authentication.getCredentials()).thenReturn(jwt);
        lenient().when(authentication.isAuthenticated()).thenReturn(true);
    }

    @Test
    public void deveCadastrarPedidoComClienteId() {
        // Dado
        Pedido pedido = new Pedido();
        pedido.setClienteId(new Pedido.ClienteId(UUID.randomUUID()));
        UUID pedidoId = UUID.randomUUID();

        // Quando
        when(repository.salvarPedido(pedido)).thenReturn(pedidoId);

        // Então
        assertEquals(pedidoId, useCase.cadastrar(pedido));
        verify(repository, times(1)).salvarPedido(pedido);
        verifyNoInteractions(consultarClienteUseCase);
    }

    @Test
    public void deveCadastrarPedidoSemClienteId() {
        // Dado
        Pedido pedido = new Pedido();
        UUID pedidoId = UUID.randomUUID();

        // Quando
        when(consultarClienteUseCase.existsByUsername(any())).thenReturn(true);
        Cliente cliente = new Cliente();
        Cliente.ClienteId clienteId = new Cliente.ClienteId(UUID.randomUUID());
        cliente.setClienteId(clienteId);
        when(consultarClienteUseCase.buscarPorUsername(any())).thenReturn(cliente);
        when(repository.salvarPedido(any())).thenReturn(pedidoId);

        // Então
        assertEquals(pedidoId, useCase.cadastrar(pedido));
        verify(consultarClienteUseCase, times(1)).existsByUsername(any());
        verify(consultarClienteUseCase, times(1)).buscarPorUsername(any());
        verify(repository, times(1)).salvarPedido(pedido);
        assertEquals(clienteId.getClienteId(), pedido.getClienteId().get().getClienteId());
    }
}
