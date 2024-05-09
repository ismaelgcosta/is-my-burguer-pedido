package br.com.ismyburguer.produto.web.api.controller;


import br.com.ismyburguer.core.adapter.in.WebAdapter;
import br.com.ismyburguer.core.validation.EnumNamePattern;
import br.com.ismyburguer.produto.web.api.converter.ListarProdutoConverter;
import br.com.ismyburguer.produto.web.api.response.ListarProdutoResponse;
import br.com.ismyburguer.produto.adapter.interfaces.in.ListarProdutoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "Produto", description = "Gerenciamento de Produtos")
@WebAdapter
@RequestMapping("/produtos")
public class ListarProdutoAPI {
    private final ListarProdutoUseCase consultarProdutoUseCase;
    private final ListarProdutoConverter listarProdutoConverter;

    public ListarProdutoAPI(ListarProdutoUseCase consultarProdutoUseCase,
                            ListarProdutoConverter listarProdutoConverter) {
        this.consultarProdutoUseCase = consultarProdutoUseCase;
        this.listarProdutoConverter = listarProdutoConverter;
    }

    @Operation(security = @SecurityRequirement(name = "Bearer-Authentication"), description = "Listar Produtos por Categoria")
    @GetMapping("/categorias/{categoria}")
    public List<ListarProdutoResponse> listarProdutos(
            @Valid
            @EnumNamePattern(
                    regexp = "LANCHE|ACOMPANHAMENTO|BEBIDA|SOBREMESA",
                    message = "O campo categoria do produto deve ser igual a {regexp}"
            )
            @Schema(allowableValues = {
                    "LANCHE",
                    "ACOMPANHAMENTO",
                    "BEBIDA",
                    "SOBREMESA"
            })
            @PathVariable(name = "categoria") String categoria
    ) {
        return
                List.copyOf(consultarProdutoUseCase.listar(new ListarProdutoUseCase.ListarProdutoQuery(StringUtils.upperCase(categoria))))
                        .stream().map(listarProdutoConverter::convert)
                        .toList();
    }

}
