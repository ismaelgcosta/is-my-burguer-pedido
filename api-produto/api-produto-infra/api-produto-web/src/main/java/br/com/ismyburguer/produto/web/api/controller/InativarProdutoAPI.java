package br.com.ismyburguer.produto.web.api.controller;


import br.com.ismyburguer.core.adapter.in.WebAdapter;
import br.com.ismyburguer.produto.adapter.interfaces.in.InativarProdutoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Produto", description = "Gerenciamento de Produtos")
@WebAdapter
@RequestMapping("/produtos")
public class InativarProdutoAPI {
    private final InativarProdutoUseCase useCase;

    public InativarProdutoAPI(InativarProdutoUseCase useCase) {
        this.useCase = useCase;
    }

    @Operation(security = @SecurityRequirement(name = "Bearer-Authentication"), description = "Inativar Produto")
    @DeleteMapping("/{produtoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarProduto(
            @PathVariable @Valid @UUID(message = "O código do produto informado está num formato inválido")String produtoId
    ) {
        useCase.inativar(produtoId);
    }

}
