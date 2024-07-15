package br.com.ismyburguer.pedido.usecase.impl.steps;

import br.com.ismyburguer.core.exception.BusinessException;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.ConsultarPagamentoUseCase;
import br.com.ismyburguer.pagamento.adapter.interfaces.in.EfetuarPagamentoUseCase;
import br.com.ismyburguer.pagamento.entity.Pagamento;
import br.com.ismyburguer.pedido.adapter.interfaces.in.AlterarStatusPedidoUseCase;
import br.com.ismyburguer.pedido.adapter.interfaces.in.ConsultarPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.usecase.impl.PagarPedidoUseCaseImpl;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PagarPedidoSteps {

    @Mock
    private EfetuarPagamentoUseCase pagamentoUseCase;

    @Mock
    private ConsultarPedidoUseCase pedidoUseCase;

    @Mock
    private ConsultarPagamentoUseCase consultarPagamentoUseCase;

    @Mock
    private AlterarStatusPedidoUseCase alterarStatusPedidoUseCase;

    @InjectMocks
    private PagarPedidoUseCaseImpl pagarPedidoUseCase;

    private Pedido.PedidoId pedidoId;
    private String qrCode;
    private Exception excecao;

    @Given("que o pedido está disponível")
    public void que_o_pedido_esta_disponivel() {
        MockitoAnnotations.openMocks(this);
        pedidoId = new Pedido.PedidoId(UUID.randomUUID().toString());
        Pedido pedido = mock(Pedido.class);
        when(pedido.getPedidoId()).thenReturn(Optional.of(pedidoId));
        when(pedido.getTotal()).thenReturn(new Pedido.Total(BigDecimal.valueOf(100.0)));
        when(pedidoUseCase.buscarPorId(pedidoId)).thenReturn(pedido);
    }

    @Given("o pagamento é autorizado")
    public void o_pagamento_e_autorizado() {
        Pagamento pagamento = mock(Pagamento.class);
        when(pagamento.getStatusPagamento()).thenReturn(Pagamento.StatusPagamento.PAGO);
        when(pagamento.getQrCode()).thenReturn("qr-code");
        when(consultarPagamentoUseCase.consultar(anyString())).thenReturn(pagamento);
    }

    @Given("o pagamento não é autorizado")
    public void o_pagamento_nao_e_autorizado() {
        Pagamento pagamento = mock(Pagamento.class);
        when(pagamento.getStatusPagamento()).thenReturn(Pagamento.StatusPagamento.NAO_AUTORIZADO);
        when(consultarPagamentoUseCase.consultar(anyString())).thenReturn(pagamento);
    }

    @Given("a consulta de pagamento falha")
    public void a_consulta_de_pagamento_falha() {
        when(consultarPagamentoUseCase.consultar(anyString())).thenThrow(new BusinessException("Erro na consulta de pagamento"));
    }

    @When("eu tento pagar o pedido")
    public void eu_tento_pagar_o_pedido() {
        try {
            pagarPedidoUseCase.pagar(pedidoId);
        } catch (Exception e) {
            excecao = e;
        }
    }

    @Then("o QR code do pagamento deve ser retornado")
    public void o_qr_code_do_pagamento_deve_ser_retornado() {
        assertNotNull(qrCode);
    }

    @Then("o status do pedido deve ser alterado para {string}")
    public void o_status_do_pedido_deve_ser_alterado_para(String status) {
        verify(alterarStatusPedidoUseCase, times(1)).alterar(pedidoId, Pedido.StatusPedido.valueOf(status));
    }

    @Then("uma exceção de negócio deve ser lançada")
    public void uma_excecao_de_negocio_deve_ser_lancada() {
        assertNotNull(excecao);
        assertThrows(BusinessException.class, () -> { throw excecao; });
    }
}
