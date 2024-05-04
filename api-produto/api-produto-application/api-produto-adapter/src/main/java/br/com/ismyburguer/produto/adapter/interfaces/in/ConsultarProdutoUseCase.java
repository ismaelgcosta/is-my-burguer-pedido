package br.com.ismyburguer.produto.adapter.interfaces.in;

import br.com.ismyburguer.core.adapter.ExistsByIdUseCase;
import br.com.ismyburguer.produto.entity.Produto;

public interface ConsultarProdutoUseCase extends ExistsByIdUseCase<Produto> {

    Produto buscar(ConsultaProdutoQuery query);

    record ConsultaProdutoQuery(String produtoId) {

    }
}
