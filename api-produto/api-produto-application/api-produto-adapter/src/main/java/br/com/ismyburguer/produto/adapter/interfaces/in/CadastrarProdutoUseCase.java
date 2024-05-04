package br.com.ismyburguer.produto.adapter.interfaces.in;

import br.com.ismyburguer.produto.entity.Produto;

import java.util.UUID;

public interface CadastrarProdutoUseCase {
    UUID cadastrar(Produto produto);
}
