package br.com.ismyburguer.controlepedido.adapters.client;

import br.com.ismyburguer.controlepedido.adapters.converter.ControlePedidoToControlePedidoResponseConverter;
import br.com.ismyburguer.controlepedido.adapters.response.ControlePedidoResponse;
import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.controlepedido.gateway.out.GerarControlePedidoRepository;
import br.com.ismyburguer.core.adapter.out.PersistenceAdapter;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
@PersistenceAdapter
public class GerarControlePedidoRepositoryImpl implements GerarControlePedidoRepository {
    private final ControlePedidoRepository controlepedidoRepository;
    private final ControlePedidoToControlePedidoResponseConverter converter;

    public GerarControlePedidoRepositoryImpl(ControlePedidoRepository controlepedidoRepository,
                                             ControlePedidoToControlePedidoResponseConverter converter) {
        this.controlepedidoRepository = controlepedidoRepository;
        this.converter = converter;
    }

    @Override
    public UUID gerar(ControlePedido controlePedido) {
        ControlePedidoResponse controlepedidoResponse = converter.convert(controlePedido);
        return controlepedidoRepository.save(controlepedidoResponse).getControlePedidoId();
    }
}
