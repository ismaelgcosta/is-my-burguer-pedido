package br.com.ismyburguer.produto.gateway.out;

import br.com.ismyburguer.produto.entity.Produto;
import jakarta.validation.Valid;

import java.util.UUID;

public interface CadastrarProdutoRepository {
    UUID salvarProduto(@Valid Produto produto);
}
