package br.com.ismyburguer.produto.usecase.impl;

import br.com.ismyburguer.produto.adapter.interfaces.in.ListarProdutoUseCase;
import br.com.ismyburguer.produto.entity.Produto;
import br.com.ismyburguer.produto.gateway.out.ListarProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListarProdutoUseCaseImplTest {

    @Mock
    private ListarProdutoRepository repository;

    @InjectMocks
    private ListarProdutoUseCaseImpl listarProdutoUseCase;

    private ListarProdutoUseCase.ListarProdutoQuery query;

    @BeforeEach
    public void setUp() {
        query = new ListarProdutoUseCase.ListarProdutoQuery("LANCHE");
    }

    @Test
    public void listar_DeveChamarMetodoListarProdutosPorCategoriaDoRepositorio_ComCategoriaCorreta() {
        Collection<Produto> produtos = mock(Collection.class);
        when(repository.listarProdutosPorCategoria(Produto.Categoria.LANCHE)).thenReturn(produtos);

        Collection<Produto> resultado = listarProdutoUseCase.listar(query);

        assertEquals(produtos, resultado);
        verify(repository, times(1)).listarProdutosPorCategoria(Produto.Categoria.LANCHE);
    }
}
