package br.com.ismyburguer.produto.usecase.impl;

import br.com.ismyburguer.core.usecase.UseCase;
import br.com.ismyburguer.produto.adapter.interfaces.in.CadastrarProdutoUseCase;
import br.com.ismyburguer.produto.entity.Produto;
import br.com.ismyburguer.produto.gateway.out.CadastrarProdutoRepository;

import java.util.UUID;

@UseCase
public class CadastrarProdutoUseCaseImpl implements CadastrarProdutoUseCase {
    private final CadastrarProdutoRepository repository;

    public CadastrarProdutoUseCaseImpl(CadastrarProdutoRepository repository) {
        this.repository = repository;
    }

    @Override
    public UUID cadastrar(Produto produto) {
        return repository.salvarProduto(produto);
    }
}
