package br.com.ismyburguer.produto.usecase.impl;

import br.com.ismyburguer.core.usecase.UseCase;
import br.com.ismyburguer.produto.entity.Produto;
import br.com.ismyburguer.produto.adapter.interfaces.in.ListarProdutoUseCase;
import br.com.ismyburguer.produto.gateway.out.ListarProdutoRepository;

import java.util.Collection;

@UseCase
public class ListarProdutoUseCaseImpl implements ListarProdutoUseCase {

    private final ListarProdutoRepository repository;

    public ListarProdutoUseCaseImpl(ListarProdutoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Collection<Produto> listar(ListarProdutoQuery query) {
        return repository.listarProdutosPorCategoria(Produto.Categoria.valueOf(query.categoria()));
    }
}
