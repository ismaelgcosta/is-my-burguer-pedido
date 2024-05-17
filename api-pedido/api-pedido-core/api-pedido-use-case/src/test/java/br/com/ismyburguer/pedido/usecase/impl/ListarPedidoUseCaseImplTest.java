package br.com.ismyburguer.pedido.usecase.impl;
import br.com.ismyburguer.pedido.adapter.interfaces.in.ListarPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.gateway.out.ListarPedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListarPedidoUseCaseImplTest {

    @Mock
    private ListarPedidoRepository repository;

    @InjectMocks
    private ListarPedidoUseCaseImpl useCase;

    @Test
    public void deveListarPedidosComSucesso() {
        // Dado
        Pedido pedido1 = new Pedido();
        Pedido pedido2 = new Pedido();
        List<Pedido> pedidosMock = Arrays.asList(pedido1, pedido2);
        when(repository.listar()).thenReturn(pedidosMock);

        // Quando
        List<Pedido> resultado = useCase.listar();

        // Então
        assertEquals(pedidosMock.size(), resultado.size());
        assertEquals(pedidosMock.get(0), resultado.get(0));
        assertEquals(pedidosMock.get(1), resultado.get(1));
        verify(repository, times(1)).listar();
    }
}
