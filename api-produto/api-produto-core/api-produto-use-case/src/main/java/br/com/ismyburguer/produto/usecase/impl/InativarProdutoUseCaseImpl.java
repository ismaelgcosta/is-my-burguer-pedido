package br.com.ismyburguer.produto.usecase.impl;

import br.com.ismyburguer.core.usecase.UseCase;
import br.com.ismyburguer.produto.adapter.interfaces.in.InativarProdutoUseCase;
import br.com.ismyburguer.produto.gateway.out.InativarProdutoRepository;

@UseCase
public class InativarProdutoUseCaseImpl implements InativarProdutoUseCase {
    private final InativarProdutoRepository repository;

    public InativarProdutoUseCaseImpl(InativarProdutoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void inativar(String produtoId) {
        repository.inativar(produtoId);
    }
}
