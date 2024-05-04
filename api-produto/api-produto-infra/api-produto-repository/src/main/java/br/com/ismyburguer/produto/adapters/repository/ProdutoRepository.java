package br.com.ismyburguer.produto.adapters.repository;

import br.com.ismyburguer.core.adapter.out.PersistenceDriver;
import br.com.ismyburguer.produto.adapters.model.Categoria;
import br.com.ismyburguer.produto.adapters.model.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@PersistenceDriver
public interface ProdutoRepository extends JpaRepository<ProdutoModel, UUID> {

    List<ProdutoModel> findAllByCategoriaAndAtivoIsTrue(Categoria categoria);

    boolean existsByProdutoIdAndAtivo(UUID produtoId, boolean status);

    Optional<ProdutoModel> findByProdutoIdAndAtivo(UUID produtoId, boolean status);
}
