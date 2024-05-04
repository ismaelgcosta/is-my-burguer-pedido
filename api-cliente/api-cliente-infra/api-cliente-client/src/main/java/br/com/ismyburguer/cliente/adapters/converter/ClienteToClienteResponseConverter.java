package br.com.ismyburguer.cliente.adapters.converter;

import br.com.ismyburguer.cliente.adapters.response.ClienteResponse;
import br.com.ismyburguer.cliente.entity.Cliente;
import br.com.ismyburguer.core.adapter.Converter;
import br.com.ismyburguer.core.adapter.in.WebConverter;

@WebConverter
public class ClienteToClienteResponseConverter implements Converter<Cliente, ClienteResponse> {
    @Override
    public ClienteResponse convert(Cliente source) {
        return new ClienteResponse(
                source.getNome().getNome(),
                source.getNome().getSobrenome(),
                source.getEmail().getEndereco(),
                source.getCpf().map(Cliente.CPF::getNumero).orElse(null),
                source.getUsername().map(Cliente.Username::getUsername).orElse(null)
        );
    }
}
