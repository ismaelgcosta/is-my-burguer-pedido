package br.com.ismyburguer.controlepedido.adapters.converter;

import br.com.ismyburguer.controlepedido.adapters.response.StatusControlePedido;
import br.com.ismyburguer.controlepedido.adapters.response.ControlePedidoResponse;
import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.core.adapter.Converter;
import br.com.ismyburguer.core.adapter.out.PersistenceConverter;

import java.util.UUID;

@PersistenceConverter
public class ControlePedidoToControlePedidoResponseConverter implements Converter<ControlePedido, ControlePedidoResponse> {

    @Override
    public ControlePedidoResponse convert(ControlePedido source) {
        return new ControlePedidoResponse(
                source.getControlePedidoId()
                        .map(ControlePedido.ControlePedidoId::getControlePedidoId)
                        .orElseGet(UUID::randomUUID),
                source.getPedidoId().getPedidoId(),
                StatusControlePedido.valueOf(source.getStatusControlePedido().name()),
                source.getRecebidoEm(),
                source.getInicioDaPreparacao(),
                source.getFimDaPreparacao()
        );
    }

}
