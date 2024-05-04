package br.com.ismyburguer.pedido.adapters.repository;

import br.com.ismyburguer.core.adapter.out.PersistenceAdapter;
import br.com.ismyburguer.pedido.adapters.converter.PedidoModelToPedidoConverter;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.gateway.out.ConsultarPedidoRepository;
import br.com.ismyburguer.pedido.gateway.out.ListarPedidoRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@PersistenceAdapter
public class ListarPedidoRepositoryImpl implements ListarPedidoRepository {
    private final PedidoRepository pedidoRepository;
    private final PedidoModelToPedidoConverter converter;

    public ListarPedidoRepositoryImpl(PedidoRepository pedidoRepository,
                                      PedidoModelToPedidoConverter converter) {
        this.pedidoRepository = pedidoRepository;
        this.converter = converter;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Pedido> listar() {
        return pedidoRepository.findAll().stream().map(converter::convert).toList();
    }

}
