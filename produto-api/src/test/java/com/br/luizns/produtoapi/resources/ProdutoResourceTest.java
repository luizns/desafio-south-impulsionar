package com.br.luizns.produtoapi.resources;

import com.br.luizns.produtoapi.config.RabbitMQConnection;
import com.br.luizns.produtoapi.creator.ProdutoCreator;
import com.br.luizns.produtoapi.mapper.ProdutoMapper;
import com.br.luizns.produtoapi.mapper.ProdutoMapperImpl;
import com.br.luizns.produtoapi.service.RabbitMQService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@AutoConfigureMockMvc
class ProdutoResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Spy
    private static ProdutoMapper produtoMapper;
    private final String URL = "/produtos";

    @BeforeAll
    public static void setUp() {
        produtoMapper = new ProdutoMapperImpl();
    }

    @Test
    void criarProdutosComDadosCorretosRetornarStatusCode201Faker() throws Exception {

        var request = produtoMapper.INSTANCE.dtoParaEntidade(ProdutoCreator.createFakerRequest());
        var jsonBody = objectMapper.writeValueAsString(request);

        var result = mockMvc
                .perform(MockMvcRequestBuilders.post(URL)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                );

        result.andDo(MockMvcResultHandlers.print());

        result.andExpect(MockMvcResultMatchers.status().isCreated());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

        result.andExpect(MockMvcResultMatchers.jsonPath("$.codigoProduto").value(request.getCodigoProduto()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(request.getNome()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.categoria").value(request.getCategoria()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.quantidade").value(request.getQuantidade()));
    }

    @Test
    void criarProdutosComDadosCorretosRetornarStatusCode201() throws Exception {
        var request = produtoMapper.INSTANCE.dtoParaEntidade(ProdutoCreator.createRequest());
        var jsonBody = objectMapper.writeValueAsString(request);

        var result = mockMvc
                .perform(MockMvcRequestBuilders.post(URL)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                );

        result.andDo(MockMvcResultHandlers.print());
        result.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        result.andExpect(MockMvcResultMatchers.status().isCreated());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

        result.andExpect(MockMvcResultMatchers.jsonPath("$.codigoProduto").value(request.getCodigoProduto()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(request.getNome()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.categoria").value(request.getCategoria()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.quantidade").value(request.getQuantidade()));

    }


    @Test
    public void criarProdutoComDadosIncorretosRetornarStatusCode400() throws Exception {

        var result = mockMvc
                .perform(MockMvcRequestBuilders.post(URL)
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
                );
        result.andDo(MockMvcResultHandlers.print());
        result.andExpect(MockMvcResultMatchers.status().is4xxClientError());
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void listarTodosProdutosDeveRetornarSuccess() throws Exception {
        var result = mockMvc.perform(MockMvcRequestBuilders.get(URL)
                .contentType(MediaType.APPLICATION_JSON)
        );
        result.andDo(MockMvcResultHandlers.print());
        result.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.[0].codigoProduto").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.[0].codigoProduto").value("7t09o00n"));

    }

    @Test
    void buscarProdutoIdDeveRetornarSuccessQuandoIdExiste() throws Exception {

        var result = mockMvc
                .perform(MockMvcRequestBuilders.get(URL.concat("/{id}"), 1)
                        .contentType(MediaType.APPLICATION_JSON)
                );

        result.andDo(MockMvcResultHandlers.print());
        result.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.codigoProduto").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.codigoProduto").value("7t09o00n"));
    }

    @Test
    void buscarProdutoIdDeveRetornarNotFoundQuandoNaoIdExiste() throws Exception {

        var result = mockMvc
                .perform(MockMvcRequestBuilders.get(URL.concat("/{id}"), 100)
                        .contentType(MediaType.APPLICATION_JSON)
                );

        result.andDo(MockMvcResultHandlers.print());
        result.andExpect(MockMvcResultMatchers.status().is4xxClientError());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.codigoProduto").doesNotExist());
        result.andExpect(MockMvcResultMatchers.status().isNotFound());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.codigoProduto").doesNotExist());
    }

    @Test
    void atualizaDeveRetornarNotFoundQuandoIdNaoExiste() throws Exception {
        var request = produtoMapper.INSTANCE.dtoParaEntidade(ProdutoCreator.updateFakerRequest());

        var jsonBody = objectMapper.writeValueAsString(request);
        var result = mockMvc
                .perform(MockMvcRequestBuilders.put(URL.concat("/{id}"), 1000L)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                );

        result.andDo(MockMvcResultHandlers.print());
        result.andExpect(MockMvcResultMatchers.status().is4xxClientError());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.codigoProduto").doesNotExist());
        result.andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    void atualizarProdutoDeveRetornarSuccessQuandoIdExiste() throws Exception {
        var request = produtoMapper.INSTANCE.dtoParaEntidade(ProdutoCreator.updateRequest());

        var jsonBody = objectMapper.writeValueAsString(request);
        var result = mockMvc
                .perform(MockMvcRequestBuilders.put(URL.concat("/{id}"), 1L)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                );

        result.andDo(MockMvcResultHandlers.print());
        result.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

        result.andExpect(MockMvcResultMatchers.jsonPath("$.codigoProduto").value(request.getCodigoProduto()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(request.getNome()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.categoria").value(request.getCategoria()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.quantidade").value(request.getQuantidade()));

    }

    @Test
    void atualizarProdutoDeveRetornarSuccessQuandoIdExisteFaker() throws Exception {
        var request = produtoMapper.INSTANCE.dtoParaEntidade(ProdutoCreator.updateFakerRequest());

        var jsonBody = objectMapper.writeValueAsString(request);
        var result = mockMvc
                .perform(MockMvcRequestBuilders.put(URL.concat("/{id}"), 2)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                );

        result.andDo(MockMvcResultHandlers.print());
        result.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

        result.andExpect(MockMvcResultMatchers.jsonPath("$.codigoProduto").value(request.getCodigoProduto()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(request.getNome()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.categoria").value(request.getCategoria()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.quantidade").value(request.getQuantidade()));

    }

    @Test
    void deleteProdutoDTODeveRetonarSuccessQuandoIdExiste() throws Exception {

        var result = mockMvc
                .perform(MockMvcRequestBuilders.delete(URL.concat("/{id}"), 3L)

                        .contentType(MediaType.APPLICATION_JSON)
                );

        result.andDo(MockMvcResultHandlers.print());
        result.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        result.andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    void deleteProdutoDTODeveRetornaNotFoundQuandoIdNaoExiste() throws Exception {

        var result = mockMvc
                .perform(MockMvcRequestBuilders.delete(URL.concat("/{id}"), 902)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                );

        result.andDo(MockMvcResultHandlers.print());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").doesNotExist());
        result.andExpect(MockMvcResultMatchers.status().is4xxClientError());
        result.andExpect(MockMvcResultMatchers.status().isNotFound());

    }


    @Test
    void alteraQuantidade_ProdutoDeveRetornarSuccessQuandoCodigoExiste() throws Exception {
        var request = produtoMapper.INSTANCE.dtoParaEntidade(ProdutoCreator.createRequest());
        var codigo = "7t0do00n";
        var texto = "Produto enviado para fila " + "CodigoProduto: " + codigo;
        ;
        var result = mockMvc
                .perform(MockMvcRequestBuilders.patch(URL.concat("/{codigoProduto}"), codigo)
                        .param("quantidade", String.valueOf(request.getQuantidade()))
                );

        result.andDo(MockMvcResultHandlers.print());
        result.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        result.andExpect(MockMvcResultMatchers.content().string(containsString(texto)));

    }

    @Test
    void alterarQuantidadeEstoqueDeveRetornarNotFoundQuandoQuandoCodigoExiste() throws Exception {
        var request = produtoMapper.INSTANCE.dtoParaEntidade(ProdutoCreator.createRequest());
        var codigo = "z123z211";
        var result = mockMvc
                .perform(MockMvcRequestBuilders.patch(URL.concat("/{codigoProduto}"), codigo)
                        .param("quantidade", String.valueOf(request.getQuantidade()))
                        .contentType(MediaType.APPLICATION_JSON)
                );

        result.andDo(MockMvcResultHandlers.print());
        result.andExpect(MockMvcResultMatchers.status().isNotFound());

    }
}