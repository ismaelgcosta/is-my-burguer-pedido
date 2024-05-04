package br.com.ismyburguer.pedido.adapters.repository;

import br.com.ismyburguer.core.adapter.out.PersistenceAdapter;
import br.com.ismyburguer.core.exception.EntityNotFoundException;
import br.com.ismyburguer.pedido.adapters.converter.PedidoToPedidoModelConverter;
import br.com.ismyburguer.pedido.adapters.entity.PedidoModel;
import br.com.ismyburguer.pedido.adapters.entity.StatusPedidoEntity;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.gateway.out.AlterarPedidoRepository;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.UUID;

@Validated
@PersistenceAdapter
public class AlterarPedidoRepositoryImpl implements AlterarPedidoRepository {
    private final PedidoRepository pedidoRepository;
    private final PedidoToPedidoModelConverter converter;

    public AlterarPedidoRepositoryImpl(PedidoRepository pedidoRepository,
                                        PedidoToPedidoModelConverter converter) {
        this.pedidoRepository = pedidoRepository;
        this.converter = converter;
    }

    public void alterarPedido(String pedidoId, @Valid Pedido pedido) {
        UUID uuid = UUID.fromString(pedidoId);
        Optional<PedidoModel> entity = pedidoRepository.findById(uuid);
        if(entity.isEmpty()) {
            throw new EntityNotFoundException("Pedido não foi encontrado");
        }
        PedidoModel oldEntity = entity.get();
        oldEntity.limparItens();
        pedido.alterarStatus(entity.map(PedidoModel::getStatusPedido).map(StatusPedidoEntity::name).map(Pedido.StatusPedido::valueOf).get());
        pedidoRepository.save(oldEntity);

        PedidoModel pedidoModel = converter.convert(pedido);
        pedidoModel.setPedidoId(uuid);
        pedidoRepository.save(pedidoModel);
    }
}
