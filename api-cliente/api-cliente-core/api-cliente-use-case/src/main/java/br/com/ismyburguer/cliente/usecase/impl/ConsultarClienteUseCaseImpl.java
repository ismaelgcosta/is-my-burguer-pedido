package br.com.ismyburguer.cliente.usecase.impl;

import br.com.ismyburguer.cliente.adapter.interfaces.out.ConsultarClienteUseCase;
import br.com.ismyburguer.cliente.entity.Cliente;
import br.com.ismyburguer.cliente.gateway.out.ConsultarClienteAPI;
import br.com.ismyburguer.core.exception.EntityNotFoundException;
import br.com.ismyburguer.core.usecase.UseCase;

import java.util.UUID;

@UseCase
public class ConsultarClienteUseCaseImpl implements ConsultarClienteUseCase {

    private final ConsultarClienteAPI api;

    public ConsultarClienteUseCaseImpl(ConsultarClienteAPI api) {
        this.api = api;
    }

    @Override
    public Cliente buscarPorUsername(ConsultaClientePorUsername query) {
        return api.obterPeloClienteUsername(query.username())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não foi encontrado"));
    }

    @Override
    public boolean existsById(UUID id) {
        return api.existsById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return api.existsByUsername(username);
    }
}
