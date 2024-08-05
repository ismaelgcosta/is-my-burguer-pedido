package br.com.ismyburguer.pedido.adapters.repository;

import br.com.ismyburguer.core.adapter.out.PersistenceAdapter;
import br.com.ismyburguer.core.exception.EntityNotFoundException;
import br.com.ismyburguer.pedido.adapters.entity.PedidoModel;
import br.com.ismyburguer.pedido.adapters.entity.StatusPedidoEntity;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.gateway.out.AlterarStatusPedidoRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Validated
@PersistenceAdapter
public class AlterarStatusPedidoRepositoryImpl implements AlterarStatusPedidoRepository {
    private final PedidoRepository pedidoRepository;
    public AlterarStatusPedidoRepositoryImpl(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Transactional
    public void alterar(Pedido.PedidoId pedidoId, Pedido.StatusPedido statusPedido) {
        Optional<PedidoModel> entity = pedidoRepository.findById(pedidoId.getPedidoId());
        if(entity.isEmpty()) {
            throw new EntityNotFoundException("Pedido não foi encontrado");
        }
        PedidoModel oldEntity = entity.get();
        oldEntity.setStatusPedido(StatusPedidoEntity.valueOf(statusPedido.name()));
        pedidoRepository.save(oldEntity);
    }
}
