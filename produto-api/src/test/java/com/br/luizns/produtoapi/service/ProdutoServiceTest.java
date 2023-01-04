package com.br.luizns.produtoapi.service;

import com.br.luizns.produtoapi.creator.ProdutoCreator;
import com.br.luizns.produtoapi.mapper.ProdutoMapper;
import com.br.luizns.produtoapi.mapper.ProdutoMapperImpl;
import com.br.luizns.produtoapi.repository.ProdutoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ProdutoServiceTest {


    @InjectMocks
    private ProdutoService service;

    @Mock
    private ProdutoRepository repository;

    @Spy
    private static ProdutoMapper produtoMapper;

    //    private static ProdutoMapper produtoMapper;
//
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




}