package br.com.ismyburguer.produto.adapters.repository;

import br.com.ismyburguer.core.adapter.out.PersistenceAdapter;
import br.com.ismyburguer.core.exception.EntityNotFoundException;
import br.com.ismyburguer.produto.adapters.model.ProdutoModel;
import br.com.ismyburguer.produto.gateway.out.InativarProdutoRepository;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
@PersistenceAdapter
public class InativarProdutoRepositoryImpl implements InativarProdutoRepository {
    private final ProdutoRepository produtoRepository;

    public InativarProdutoRepositoryImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public void inativar(String produtoId) {
        UUID uuid = UUID.fromString(produtoId);
        ProdutoModel produtoModel = produtoRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Produto não foi encontrado"));
        produtoModel.setAtivo(false);
        produtoRepository.save(produtoModel);
    }
}
