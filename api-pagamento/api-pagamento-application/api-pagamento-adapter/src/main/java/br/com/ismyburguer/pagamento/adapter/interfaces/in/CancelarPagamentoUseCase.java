package br.com.ismyburguer.pagamento.adapter.interfaces.in;

import br.com.ismyburguer.pagamento.entity.Pagamento;
import jakarta.validation.Valid;

public interface CancelarPagamentoUseCase {
    void cancelar(@Valid Pagamento.PedidoId pedidoId);
}
