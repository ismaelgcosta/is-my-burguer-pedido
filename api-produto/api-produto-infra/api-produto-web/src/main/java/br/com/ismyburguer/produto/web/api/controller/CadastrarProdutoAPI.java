package br.com.ismyburguer.produto.web.api.controller;


import br.com.ismyburguer.core.adapter.in.WebAdapter;
import br.com.ismyburguer.produto.web.api.converter.CadastrarProdutoRequestConverter;
import br.com.ismyburguer.produto.web.api.request.CriarProdutoRequest;
import br.com.ismyburguer.produto.adapter.interfaces.in.CadastrarProdutoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Tag(name = "Produto", description = "Gerenciamento de Produtos")
@WebAdapter
@RequestMapping("/produtos")
public class CadastrarProdutoAPI {
    private final CadastrarProdutoUseCase cadastrarProdutoUseCase;
    private final CadastrarProdutoRequestConverter cadastrarProdutoRequestConverter;

    public CadastrarProdutoAPI(CadastrarProdutoUseCase cadastrarProdutoUseCase,
                               CadastrarProdutoRequestConverter cadastrarProdutoRequestConverter) {
        this.cadastrarProdutoUseCase = cadastrarProdutoUseCase;
        this.cadastrarProdutoRequestConverter = cadastrarProdutoRequestConverter;
    }

    @Operation(security = @SecurityRequirement(name = "Bearer-Authentication"), description = "Cadastrar Produto")
    @PostMapping
    public UUID cadastrarProduto(@RequestBody CriarProdutoRequest criarProdutoRequest) {
        return cadastrarProdutoUseCase.cadastrar(cadastrarProdutoRequestConverter.convert(criarProdutoRequest));
    }

}
