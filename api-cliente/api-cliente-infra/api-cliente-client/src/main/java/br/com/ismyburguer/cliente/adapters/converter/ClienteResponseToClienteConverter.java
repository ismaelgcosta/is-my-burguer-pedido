package br.com.ismyburguer.cliente.adapters.converter;

import br.com.ismyburguer.cliente.adapters.response.ClienteResponse;
import br.com.ismyburguer.cliente.entity.Cliente;
import br.com.ismyburguer.core.adapter.Converter;
import br.com.ismyburguer.core.adapter.in.WebConverter;

import java.util.Optional;

@WebConverter
public class ClienteResponseToClienteConverter implements Converter<ClienteResponse, Cliente> {
    @Override
    public Cliente convert(ClienteResponse source) {
        return new Cliente(
                new Cliente.ClienteId(source.getClienteId()),
                new Cliente.Nome(source.getNome(), source.getSobrenome()),
                new Cliente.Email(source.getEmail()),
                source.getCpf().map(Cliente.CPF::new).orElse(null),
                Optional.ofNullable(source.getUsername()).map(Cliente.Username::new).orElse(null)
        );
    }
}
