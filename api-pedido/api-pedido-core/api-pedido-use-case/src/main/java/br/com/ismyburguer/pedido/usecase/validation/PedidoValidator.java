package br.com.ismyburguer.pedido.usecase.validation;

import br.com.ismyburguer.cliente.adapter.interfaces.out.ConsultarClienteUseCase;
import br.com.ismyburguer.core.exception.BusinessException;
import br.com.ismyburguer.core.validation.DomainReferenceValidator;
import br.com.ismyburguer.core.validation.Validator;
import br.com.ismyburguer.pedido.adapter.interfaces.in.ConsultarPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.produto.adapter.interfaces.in.ConsultarProdutoUseCase;

import java.util.Optional;

@Validator
public class PedidoValidator {

    private final DomainReferenceValidator validator;
    private final ConsultarPedidoUseCase consultarPedidoUseCase;

    public PedidoValidator(DomainReferenceValidator validator, ConsultarPedidoUseCase consultarPedidoUseCase) {
        this.validator = validator;
        this.consultarPedidoUseCase = consultarPedidoUseCase;
    }

    public void validate(String pedidoId, Pedido pedido) {
        pedido.validate();

        if(pedidoId != null) {
            Pedido pedidoExistente = consultarPedidoUseCase.buscarPorId(new Pedido.PedidoId(pedidoId));
            if (pedidoExistente.getStatusPedido() != Pedido.StatusPedido.ABERTO) {
                throw new BusinessException("O Pedido não está mais aberto e não pode ser alterado");
            }
        }

        Optional<Pedido.ClienteId> clienteId = pedido.getClienteId();
        clienteId.ifPresent(id -> validator.validate(ConsultarClienteUseCase.class, "Cliente", id.getClienteId()));
        pedido
                .getItens()
                .forEach(item -> {
                    item.validate();
                    validator.validate(ConsultarProdutoUseCase.class, "Produto", item.getProdutoId().getProdutoId());
                });
    }
}
