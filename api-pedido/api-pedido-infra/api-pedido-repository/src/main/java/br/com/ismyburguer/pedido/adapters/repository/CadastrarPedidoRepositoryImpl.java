package br.com.ismyburguer.pedido.adapters.repository;

import br.com.ismyburguer.pedido.adapters.converter.PedidoToPedidoModelConverter;
import br.com.ismyburguer.pedido.adapters.entity.PedidoModel;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.gateway.out.CadastrarPedidoRepository;
import br.com.ismyburguer.core.adapter.out.PersistenceAdapter;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
@PersistenceAdapter
public class CadastrarPedidoRepositoryImpl implements CadastrarPedidoRepository {
    private final PedidoRepository pedidoRepository;
    private final PedidoToPedidoModelConverter converter;

    public CadastrarPedidoRepositoryImpl(PedidoRepository pedidoRepository,
                                          PedidoToPedidoModelConverter converter) {
        this.pedidoRepository = pedidoRepository;
        this.converter = converter;
    }

    @Override
    public UUID salvarPedido(Pedido pedido) {
        PedidoModel pedidoEntity = converter.convert(pedido);
        return pedidoRepository.save(pedidoEntity).getPedidoId();
    }
}
