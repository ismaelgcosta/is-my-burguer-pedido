package br.com.ismyburguer.produto.usecase.impl;

import br.com.ismyburguer.core.exception.EntityNotFoundException;
import br.com.ismyburguer.core.usecase.UseCase;
import br.com.ismyburguer.produto.adapter.interfaces.in.ConsultarProdutoUseCase;
import br.com.ismyburguer.produto.entity.Produto;
import br.com.ismyburguer.produto.gateway.out.ConsultaProdutoRepository;

import java.util.UUID;

@UseCase
public class ConsultarProdutoUseCaseImpl implements ConsultarProdutoUseCase {

    private final ConsultaProdutoRepository repository;

    public ConsultarProdutoUseCaseImpl(ConsultaProdutoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Produto buscar(ConsultaProdutoQuery query) {
        return repository.obterPeloProdutoId(UUID.fromString(query.produtoId()))
                .orElseThrow(() -> new EntityNotFoundException("Produto não foi encontrado"));
    }

    @Override
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }
}
