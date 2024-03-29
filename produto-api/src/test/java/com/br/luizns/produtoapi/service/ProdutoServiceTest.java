package com.br.luizns.produtoapi.service;

import com.br.luizns.produtoapi.creator.ProdutoCreator;
import com.br.luizns.produtoapi.mapper.ProdutoMapper;
import com.br.luizns.produtoapi.mapper.ProdutoMapperImpl;
import com.br.luizns.produtoapi.repository.ProdutoRepository;
import com.br.luizns.produtoapi.service.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.br.luizns.produtoapi.service.ProdutoService.getValorFinal;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService service;

    @Mock
    private ProdutoRepository repository;

    @Mock
    private RabbitMQService rabbitMQService;

    private static ProdutoMapper produtoMapper;

    @BeforeAll
    public static void setUp() {
        produtoMapper = new ProdutoMapperImpl();
    }

    @Test
    void criarProdutosRetornarStatusCodeCreated() throws Exception {
        var request = ProdutoCreator.createFakerRequest();
        var produtoSave = produtoMapper.INSTANCE.dtoParaEntidade(request);


        produtoSave.setValorFinal(getValorFinal(produtoSave.getValorBruto(), produtoSave.getImpostos()));


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
        produtoSave.setValorFinal(getValorFinal(produtoSave.getValorBruto(), produtoSave.getImpostos()));

        Mockito.when(repository.findById(3L)).thenReturn(Optional.of(produtoSave));
        Mockito.when(repository.save(produtoSave)).thenReturn(produtoSave);

        var response = service.atualizarProduto(3L, request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getCodigoProduto(), request.getCodigoProduto());
    }

    @Test
    void deleteShouldReturnProdutoDtoWhenSuccess() throws Exception {
        var request = ProdutoCreator.createFakerRequest();
        var produtoSave = produtoMapper.dtoParaEntidade(request).withId(1L);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(produtoSave));
        Mockito.doNothing().when(repository).deleteById(1L);


        var response = service.buscarProdutoPorId(1L);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getCodigoProduto(), request.getCodigoProduto());

    }

    @Test
    void alterarQuantidadeEstoqueDeveRetornarSuccess() throws JsonProcessingException {
        var request = ProdutoCreator.createFakerRequest();
        var produtoSave = produtoMapper.dtoParaEntidade(request).withId(1L);

        Mockito.when(repository.findByCodigoProduto(produtoSave.getCodigoProduto())).thenReturn(Optional.of(produtoSave));
        Mockito.doNothing().when(rabbitMQService).enviarMensagem(produtoSave);

        var response = service.alterarQuantidadeEstoque(produtoSave.getCodigoProduto(), 6);

        Assertions.assertNotNull(response);
        Assertions.assertEquals("CodigoProduto: " + produtoSave.getCodigoProduto(), response);
    }

}