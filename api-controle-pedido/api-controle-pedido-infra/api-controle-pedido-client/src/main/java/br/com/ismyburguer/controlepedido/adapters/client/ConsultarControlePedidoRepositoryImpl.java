package br.com.ismyburguer.controlepedido.adapters.client;

import br.com.ismyburguer.controlepedido.adapters.converter.ControlePedidoResponseToControlePedidoConverter;
import br.com.ismyburguer.controlepedido.adapters.response.ControlePedidoResponse;
import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.controlepedido.gateway.out.ConsultarControlePedidoRepository;
import br.com.ismyburguer.core.adapter.out.PersistenceAdapter;

import java.util.Optional;
import java.util.UUID;

@PersistenceAdapter
public class ConsultarControlePedidoRepositoryImpl implements ConsultarControlePedidoRepository {
    private final ControlePedidoRepository controlePedidoRepository;
    private final ControlePedidoResponseToControlePedidoConverter converter;

    public ConsultarControlePedidoRepositoryImpl(ControlePedidoRepository controlePedidoRepository,
                                                 ControlePedidoResponseToControlePedidoConverter converter) {
        this.controlePedidoRepository = controlePedidoRepository;
        this.converter = converter;
    }

    @Override
    public Optional<ControlePedido> consultar(UUID pedidoId) {
        Optional<ControlePedidoResponse> controlePedidoEntity = controlePedidoRepository.findByPedidoId(pedidoId);
        return controlePedidoEntity.map(converter::convert);
    }

}
