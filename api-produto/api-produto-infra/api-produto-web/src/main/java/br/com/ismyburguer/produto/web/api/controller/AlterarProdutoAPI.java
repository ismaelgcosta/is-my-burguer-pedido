package br.com.ismyburguer.produto.web.api.controller;


import br.com.ismyburguer.core.adapter.in.WebAdapter;
import br.com.ismyburguer.produto.web.api.converter.AlterarProdutoRequestConverter;
import br.com.ismyburguer.produto.web.api.request.AlterarProdutoRequest;
import br.com.ismyburguer.produto.adapter.interfaces.in.AlterarProdutoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Produto", description = "Gerenciamento de Produtos")
@WebAdapter
@RequestMapping("/produtos")
public class AlterarProdutoAPI {
    private final AlterarProdutoUseCase useCase;
    private final AlterarProdutoRequestConverter converter;

    public AlterarProdutoAPI(AlterarProdutoUseCase useCase,
                             AlterarProdutoRequestConverter converter) {
        this.useCase = useCase;
        this.converter = converter;
    }

    @Operation(security = @SecurityRequirement(name = "Bearer-Authentication"), description = "Alterar Produto")
    @PutMapping("/{produtoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarProduto(
            @PathVariable @Valid @UUID(message = "O código do produto informado está num formato inválido") String produtoId,
            @RequestBody AlterarProdutoRequest request
    ) {
        useCase.alterar(produtoId, converter.convert(request));
    }

}
