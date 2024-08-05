package br.com.ismyburguer.pedido.adapters.repository;

import br.com.ismyburguer.core.adapter.out.PersistenceAdapter;
import br.com.ismyburguer.core.exception.EntityNotFoundException;
import br.com.ismyburguer.pedido.adapters.entity.PedidoModel;
import br.com.ismyburguer.pedido.adapters.entity.StatusPedidoEntity;
import br.com.ismyburguer.pedido.gateway.out.FecharPedidoRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
@PersistenceAdapter
public class FecharPedidoRepositoryImpl implements FecharPedidoRepository {
    private final PedidoRepository pedidoRepository;

    public FecharPedidoRepositoryImpl(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    @Transactional
    public void fecharPedido(UUID pedidoId) {
        PedidoModel pedidoModel = pedidoRepository
                .findById(pedidoId)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não foi encontrado"));

        pedidoModel.setStatusPedido(StatusPedidoEntity.FECHADO);
        pedidoRepository.save(pedidoModel);
    }
}
