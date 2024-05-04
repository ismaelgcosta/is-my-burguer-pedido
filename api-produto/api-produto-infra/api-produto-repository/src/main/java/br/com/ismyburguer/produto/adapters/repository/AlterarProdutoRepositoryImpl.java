package br.com.ismyburguer.produto.adapters.repository;

import br.com.ismyburguer.core.adapter.out.PersistenceAdapter;
import br.com.ismyburguer.core.exception.EntityNotFoundException;
import br.com.ismyburguer.produto.adapters.converter.ProdutoToProdutoModelConverter;
import br.com.ismyburguer.produto.adapters.model.ProdutoModel;
import br.com.ismyburguer.produto.entity.Produto;
import br.com.ismyburguer.produto.gateway.out.AlterarProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
@PersistenceAdapter
public class AlterarProdutoRepositoryImpl implements AlterarProdutoRepository {
    private final ProdutoRepository produtoRepository;
    private final ProdutoToProdutoModelConverter converter;

    public AlterarProdutoRepositoryImpl(ProdutoRepository produtoRepository,
                                        ProdutoToProdutoModelConverter converter) {
        this.produtoRepository = produtoRepository;
        this.converter = converter;
    }

    public void alterarProduto(String produtoId, @Valid Produto produto) {
        UUID uuid = UUID.fromString(produtoId);
        if(!produtoRepository.existsById(uuid)) {
            throw new EntityNotFoundException("Produto não foi encontrado");
        }

        ProdutoModel produtoModel = converter.convert(produto);
        produtoModel.setProdutoId(uuid);
        produtoRepository.save(produtoModel);
    }
}
