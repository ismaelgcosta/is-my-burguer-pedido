package br.com.ismyburguer.controlepedido.adapters.client;

import br.com.ismyburguer.controlepedido.adapters.converter.ControlePedidoToControlePedidoResponseConverter;
import br.com.ismyburguer.controlepedido.adapters.response.ControlePedidoResponse;
import br.com.ismyburguer.controlepedido.entity.ControlePedido;
import br.com.ismyburguer.controlepedido.gateway.out.AlterarControlePedidoRepository;
import br.com.ismyburguer.core.adapter.out.PersistenceAdapter;
import org.springframework.validation.annotation.Validated;

@Validated
@PersistenceAdapter
public class AlterarControlePedidoRepositoryImpl implements AlterarControlePedidoRepository {
    private final ControlePedidoRepository controlepedidoRepository;
    private final ControlePedidoToControlePedidoResponseConverter converter;

    public AlterarControlePedidoRepositoryImpl(ControlePedidoRepository controlepedidoRepository,
                                               ControlePedidoToControlePedidoResponseConverter converter) {
        this.controlepedidoRepository = controlepedidoRepository;
        this.converter = converter;
    }

    @Override
    public void alterar(ControlePedido controlepedido) {
        ControlePedidoResponse controlePedidoResponse = converter.convert(controlepedido);
        controlepedidoRepository.save(controlePedidoResponse);
    }
}
