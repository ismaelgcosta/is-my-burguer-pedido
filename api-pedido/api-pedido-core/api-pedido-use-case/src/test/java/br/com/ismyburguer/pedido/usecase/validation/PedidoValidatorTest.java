package br.com.ismyburguer.pedido.usecase.validation;

import br.com.ismyburguer.cliente.adapter.interfaces.out.ConsultarClienteUseCase;
import br.com.ismyburguer.core.validation.DomainReferenceValidator;
import br.com.ismyburguer.pedido.adapter.interfaces.in.ConsultarPedidoUseCase;
import br.com.ismyburguer.pedido.entity.ItemPedido;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.produto.adapter.interfaces.in.ConsultarProdutoUseCase;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoValidatorTest {

    @Mock
    private DomainReferenceValidator validator;

    @Mock
    private ConsultarPedidoUseCase consultarPedidoUseCase;

    private PedidoValidator pedidoValidator;

    @BeforeEach
    void setUp() {
        pedidoValidator = new PedidoValidator(validator, consultarPedidoUseCase);
    }

    @Test
    void validate_PedidoAbertoValido_NaoDeveLancarExcecao() {
        // Arrange
        UUID pedidoId = UUID.randomUUID();
        Pedido pedido = new Pedido();
        UUID produtoId = UUID.randomUUID();
        pedido.getItens().add(new ItemPedido(new ItemPedido.ProdutoId(produtoId), new ItemPedido.Quantidade(1), new ItemPedido.Preco(BigDecimal.TEN)));
        lenient().when(consultarPedidoUseCase.buscarPorId(any())).thenReturn(new Pedido());

        // Act & Assert
        assertDoesNotThrow(() -> pedidoValidator.validate(pedidoId.toString(), pedido));
    }

    @Test
    void validate_PedidoFechadoValido_DeveLancarExcecao() {
        // Arrange
        UUID pedidoId = UUID.randomUUID();
        Pedido pedido = new Pedido();
        Pedido pedidoExistente = new Pedido();
        pedidoExistente.alterarStatus(Pedido.StatusPedido.FECHADO);
        lenient().when(consultarPedidoUseCase.buscarPorId(any())).thenReturn(pedidoExistente);

        // Act & Assert
        assertThrows(ConstraintViolationException.class, () -> pedidoValidator.validate(pedidoId.toString(), pedido));
    }

    @Test
    void validate_PedidoComClienteIdValido_NaoDeveLancarExcecao() {
        // Arrange
        UUID clienteId = UUID.randomUUID();
        UUID produtoId = UUID.randomUUID();
        Pedido pedido = new Pedido();
        pedido.getItens().add(new ItemPedido(new ItemPedido.ProdutoId(produtoId), new ItemPedido.Quantidade(1), new ItemPedido.Preco(BigDecimal.TEN)));
        pedido.setClienteId(new Pedido.ClienteId(clienteId));
        lenient().when(consultarPedidoUseCase.buscarPorId(any())).thenReturn(new Pedido());

        // Act & Assert
        assertDoesNotThrow(() -> pedidoValidator.validate(null, pedido));
        verify(validator, times(1)).validate(eq(ConsultarClienteUseCase.class), eq("Cliente"), eq(clienteId));
    }

    @Test
    void validate_PedidoComProdutoValido_NaoDeveLancarExcecao() {
        // Arrange
        UUID produtoId = UUID.randomUUID();
        Pedido pedido = new Pedido();
        pedido.getItens().add(new ItemPedido(new ItemPedido.ProdutoId(produtoId), new ItemPedido.Quantidade(1), new ItemPedido.Preco(BigDecimal.TEN)));
        lenient().when(consultarPedidoUseCase.buscarPorId(any())).thenReturn(new Pedido());

        // Act & Assert
        assertDoesNotThrow(() -> pedidoValidator.validate(null, pedido));
        verify(validator, times(1)).validate(eq(ConsultarProdutoUseCase.class), eq("Produto"), eq(produtoId));
    }

    @Test
    void validate_PedidoInvalido_DeveLancarExcecao() {
        // Arrange
        Pedido pedido = new Pedido();

        // Act & Assert
        assertThrows(ConstraintViolationException.class, () -> pedidoValidator.validate(null, pedido));
    }
}
