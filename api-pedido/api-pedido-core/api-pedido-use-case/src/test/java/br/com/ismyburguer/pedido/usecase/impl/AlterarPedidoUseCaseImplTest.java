package br.com.ismyburguer.pedido.usecase.impl;

import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.gateway.out.AlterarPedidoRepository;
import br.com.ismyburguer.pedido.usecase.validation.PedidoValidator;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.gateway.out.AlterarPedidoRepository;
import br.com.ismyburguer.pedido.usecase.validation.PedidoValidator;
import jakarta.validation.Valid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlterarPedidoUseCaseImplTest {

    @Mock
    private AlterarPedidoRepository repository;

    @Mock
    private PedidoValidator validator;

    @InjectMocks
    private AlterarPedidoUseCaseImpl useCase;

    @Test
    public void deveAlterarPedidoComSucesso() {
        // Dado
        String pedidoId = "pedido-id";
        Pedido pedido = new Pedido(); // Crie um pedido válido aqui

        // Quando
        useCase.alterar(pedidoId, pedido);

        // Então
        verify(validator, times(1)).validate(pedidoId, pedido);
        verify(repository, times(1)).alterarPedido(pedidoId, pedido);
    }
}
