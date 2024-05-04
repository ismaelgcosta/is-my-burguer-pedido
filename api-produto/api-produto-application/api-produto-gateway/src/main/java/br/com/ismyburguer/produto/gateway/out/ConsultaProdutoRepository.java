package br.com.ismyburguer.produto.gateway.out;

import br.com.ismyburguer.produto.entity.Produto;

import java.util.Optional;
import java.util.UUID;

public interface ConsultaProdutoRepository {
    Optional<Produto> obterPeloProdutoId(UUID produtoId);
    boolean existsById(UUID produtoId);
}
