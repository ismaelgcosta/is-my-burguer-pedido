package br.com.ismyburguer.produto.web.api.controller;


import br.com.ismyburguer.core.adapter.in.WebAdapter;
import br.com.ismyburguer.produto.web.api.converter.BuscarProdutoConverter;
import br.com.ismyburguer.produto.web.api.response.BuscarProdutoResponse;
import br.com.ismyburguer.produto.adapter.interfaces.in.ConsultarProdutoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Produto", description = "Gerenciamento de Produtos")
@WebAdapter
@RequestMapping("/produtos")
public class BuscarProdutoAPI {
    private final ConsultarProdutoUseCase consultarProdutoUseCase;
    private final BuscarProdutoConverter buscarProdutoConverter;

    public BuscarProdutoAPI(ConsultarProdutoUseCase consultarProdutoUseCase,
                            BuscarProdutoConverter buscarProdutoConverter) {
        this.consultarProdutoUseCase = consultarProdutoUseCase;
        this.buscarProdutoConverter = buscarProdutoConverter;
    }

    @Operation(security = @SecurityRequirement(name = "Bearer-Authentication"), description = "Consultar Produto")
    @GetMapping("/{produtoId}")
    public BuscarProdutoResponse obterProduto(@PathVariable(name = "produtoId") @UUID @Valid String produtoId) {
        return buscarProdutoConverter.convert(consultarProdutoUseCase.buscar(new ConsultarProdutoUseCase.ConsultaProdutoQuery(produtoId)));
    }

}
