package br.com.ismyburguer.cliente.adapters.client;

import br.com.ismyburguer.cliente.adapters.converter.ClienteResponseToClienteConverter;
import br.com.ismyburguer.cliente.entity.Cliente;
import br.com.ismyburguer.cliente.gateway.out.ConsultarClienteAPI;
import br.com.ismyburguer.core.adapter.in.WebClient;
import br.com.ismyburguer.core.adapter.out.FeignClientAPI;

import java.util.Optional;
import java.util.UUID;

@WebClient
public class ConsultarClienteAPIImpl implements ConsultarClienteAPI {
    private final ClienteResponseToClienteConverter converter;
    private final ClienteAPI clienteAPI;

    public ConsultarClienteAPIImpl(FeignClientAPI feignClientAPI,
                                   ClienteResponseToClienteConverter converter) {
        this.clienteAPI = feignClientAPI.createClient(ClienteAPI.class);
        this.converter = converter;
    }

    @Override
    public boolean existsById(UUID clienteId) {
        return clienteAPI.existsById(clienteId);
    }

    @Override
    public boolean existsByUsername(String username) {
        return clienteAPI.existsByUsername(username);
    }

    @Override
    public Optional<Cliente> obterPeloClienteUsername(String username) {
        return Optional.ofNullable(clienteAPI.findByUsername(username))
                .map(converter::convert);
    }

}
