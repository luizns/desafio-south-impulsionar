package com.br.luizns.produtoapi.service;

import com.br.luizns.produtoapi.creator.ProdutoCreator;
import com.br.luizns.produtoapi.mapper.ProdutoMapper;
import com.br.luizns.produtoapi.mapper.ProdutoMapperImpl;
import com.br.luizns.produtoapi.repository.ProdutoRepository;
import com.br.luizns.produtoapi.service.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService service;

    @Mock
    private ProdutoRepository repository;

    @Spy
    private static ProdutoMapper produtoMapper;

    @BeforeAll
    public static void setUp() {
        produtoMapper = new ProdutoMapperImpl();
    }

    @Test
    void criarProdutosRetornarStatusCodeCreated() throws Exception {
        var request = ProdutoCreator.createFakerRequest();
        var produtoSave = produtoMapper.INSTANCE.dtoParaEntidade(request);

        Mockito.when(repository.save(produtoSave)).thenReturn(produtoSave);

        var response = service.inserir(request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getCodigoProduto(), request.getCodigoProduto());
    }

    @Test
    void buscarProdutoPorIdDeveRetornarSuccessQuandoIdExiste() throws Exception {
        var request = ProdutoCreator.createFakerRequest();
        var produtoSave = produtoMapper.dtoParaEntidade(request).withId(1L);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(produtoSave));

        var response = service.buscarProdutoPorId(1L);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getCodigoProduto(), request.getCodigoProduto());
    }

    @Test
    void buscarIdDeveRetornarResourceNotFoundExceptionQuandoIdInexistente() {
        assertThrows(ResourceNotFoundException.class, () -> service.buscarProdutoPorId(1000L));
    }

    @Test
    void atualizarProdutoDTODeveRetornarSuccess() throws Exception {
        var request = ProdutoCreator.createFakerRequest();
        var produtoSave = produtoMapper.INSTANCE.dtoParaEntidade(request).withId(3L);

        Mockito.when(repository.findById(3L)).thenReturn(Optional.of(produtoSave));
        Mockito.when(repository.save(produtoSave)).thenReturn(produtoSave);

        var response = service.atualizarProduto(3L, request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getCodigoProduto(), request.getCodigoProduto());
    }
}