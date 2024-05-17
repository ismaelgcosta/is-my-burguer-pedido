package br.com.ismyburguer.produto.usecase.impl;
import br.com.ismyburguer.produto.entity.Produto;
import br.com.ismyburguer.produto.gateway.out.AlterarProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlterarProdutoUseCaseImplTest {

    @Mock
    private AlterarProdutoRepository repository;

    @InjectMocks
    private AlterarProdutoUseCaseImpl useCase;

    private static final String PRODUTO_ID = "f47ac10b-58cc-4372-a567-0e02b2c3d479";
    private Produto produto;

    @BeforeEach
    void setUp() {
        produto = new Produto(Produto.Categoria.LANCHE);
    }

    @Test
    void deveChamarMetodoDeAlteracaoNoRepositorio() {
        // Quando
        useCase.alterar(PRODUTO_ID, produto);

        // Então
        verify(repository, times(1)).alterarProduto(PRODUTO_ID, produto);
    }
}
