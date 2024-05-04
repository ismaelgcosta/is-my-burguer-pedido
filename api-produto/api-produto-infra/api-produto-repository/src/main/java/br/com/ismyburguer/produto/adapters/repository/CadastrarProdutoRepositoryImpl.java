package br.com.ismyburguer.produto.adapters.repository;

import br.com.ismyburguer.core.adapter.out.PersistenceAdapter;
import br.com.ismyburguer.produto.adapters.converter.ProdutoToProdutoModelConverter;
import br.com.ismyburguer.produto.adapters.model.ProdutoModel;
import br.com.ismyburguer.produto.entity.Produto;
import br.com.ismyburguer.produto.gateway.out.CadastrarProdutoRepository;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
@PersistenceAdapter
public class CadastrarProdutoRepositoryImpl implements CadastrarProdutoRepository {
    private final ProdutoRepository produtoRepository;
    private final ProdutoToProdutoModelConverter converter;

    public CadastrarProdutoRepositoryImpl(ProdutoRepository produtoRepository,
                                          ProdutoToProdutoModelConverter converter) {
        this.produtoRepository = produtoRepository;
        this.converter = converter;
    }

    @Override
    public UUID salvarProduto(Produto produto) {
        ProdutoModel produtoModel = converter.convert(produto);
        return produtoRepository.save(produtoModel).getProdutoId();
    }
}
