package br.com.ismyburguer.pedido.usecase.impl;

import br.com.ismyburguer.core.usecase.UseCase;
import br.com.ismyburguer.pedido.adapter.interfaces.in.ListarPedidoUseCase;
import br.com.ismyburguer.pedido.entity.Pedido;
import br.com.ismyburguer.pedido.gateway.out.ListarPedidoRepository;

import java.util.List;

@UseCase
public class ListarPedidoUseCaseImpl implements ListarPedidoUseCase {

    private final ListarPedidoRepository repository;

    public ListarPedidoUseCaseImpl(ListarPedidoRepository repository) {
        this.repository = repository;
    }

    public List<Pedido> listar() {
        return repository.listar();
    }
}
