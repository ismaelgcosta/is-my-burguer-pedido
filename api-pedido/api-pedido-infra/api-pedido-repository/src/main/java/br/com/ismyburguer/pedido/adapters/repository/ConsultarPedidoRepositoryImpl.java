package br.com.ismyburguer.pedido.adapters.repository;

import br.com.ismyburguer.core.adapter.out.PersistenceAdapter;
import br.com.ismyburguer.pedido.adapters.converter.PedidoModelToPedidoConverter;
import br.com.ismyburguer.pedido.adapters.entity.PedidoModel;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.gateway.out.ConsultarPedidoRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@PersistenceAdapter
public class ConsultarPedidoRepositoryImpl implements ConsultarPedidoRepository {
    private final PedidoRepository pedidoRepository;
    private final PedidoModelToPedidoConverter converter;

    public ConsultarPedidoRepositoryImpl(PedidoRepository pedidoRepository,
                                          PedidoModelToPedidoConverter converter) {
        this.pedidoRepository = pedidoRepository;
        this.converter = converter;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Optional<Pedido> obterPeloPedidoId(UUID pedidoId) {
        Optional<PedidoModel> pedidoEntity = pedidoRepository.findById(pedidoId);
        return pedidoEntity.map(converter::convert);
    }

}
