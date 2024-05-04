package br.com.ismyburguer.controlepedido.adapters.client;

import br.com.ismyburguer.controlepedido.adapters.converter.ControlePedidoResponseToControlePedidoConverter;
import br.com.ismyburguer.controlepedido.adapters.response.StatusControlePedido;
import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.controlepedido.gateway.out.ListarControlePedidoRepository;
import br.com.ismyburguer.core.adapter.out.PersistenceAdapter;

import java.util.Comparator;
import java.util.List;

@PersistenceAdapter
public class ListarControlePedidoRepositoryImpl implements ListarControlePedidoRepository {
    private final ControlePedidoRepository controlePedidoRepository;
    private final ControlePedidoResponseToControlePedidoConverter converter;

    public ListarControlePedidoRepositoryImpl(ControlePedidoRepository controlePedidoRepository,
                                              ControlePedidoResponseToControlePedidoConverter converter) {
        this.controlePedidoRepository = controlePedidoRepository;
        this.converter = converter;
    }

    @Override
    public List<ControlePedido> listar() {
        return controlePedidoRepository.findAllByStatusControlePedidoNot(StatusControlePedido.RETIRADO)
                .stream()
                .map(converter::convert)
                .sorted(Comparator.comparing(ControlePedido::getRecebidoEm))
                .toList();
    }
}
