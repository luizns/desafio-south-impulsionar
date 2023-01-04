package com.br.luizns.produtoapi.creator;

import com.br.luizns.produtoapi.dto.ProdutoRequestDTO;
import com.br.luizns.produtoapi.util.ProdutoUtil;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Locale;

public class ProdutoCreator {

    public static ProdutoRequestDTO createRequest() {
        return ProdutoRequestDTO.builder()
                .codigoProduto("7t09o00n")
                .codigoDeBarras("929936360073")
                .serie(LocalDate.now().getMonth()+"/"+LocalDate.now().getYear())
                .nome("Livro Padrões Projetos")
                .descricao("Livro melhores praticas")
                .categoria("ESCRITORIO")
                .valorBruto(new BigDecimal("89.00"))
                .impostos(new BigDecimal("5.90"))
                .dataDeFabricacao(ProdutoUtil.localDatFormat("23/11/2022"))
                .dataDeValidade(ProdutoUtil.localDatFormat(""))
                .cor("BRANCO")
                .material("PAPEL")
                .quantidade(6)
                .build();
    }

    public static ProdutoRequestDTO createFakerRequest() {
        var fakeValuesService = new FakeValuesService(new Locale("pt-BR"), new RandomService());

        var faker = new Faker(new Locale("pt-BR"));

        String stringAlfaNumerico = fakeValuesService.regexify("[a-z0-9]{8}");
        String stringNumerica = fakeValuesService.regexify("[0-9]{12}");

        return ProdutoRequestDTO.builder()
                .codigoProduto(stringAlfaNumerico)
                .codigoDeBarras(stringNumerica)
                .serie(LocalDate.now().getMonth().getValue()+"/"+LocalDate.now().getYear())
                .nome(faker.commerce().productName())
                .descricao(faker.harryPotter().book())
                .categoria(faker.commerce().department())
                .valorBruto(new BigDecimal(faker.commerce().price().replace(",", ".")))
                .impostos(new BigDecimal(faker.commerce().price().replace(",", ".")))
                .dataDeFabricacao(LocalDate.now())
                .dataDeValidade(ProdutoUtil.localDatFormat(""))
                .cor(faker.commerce().color())
                .material(faker.commerce().material())
                .quantidade(faker.number().randomDigitNotZero())
                .build();

    }

    public static ProdutoRequestDTO updateFakerRequest() {
        var fakeValuesService = new FakeValuesService(new Locale("pt-BR"), new RandomService());

        var faker = new Faker(new Locale("pt-BR"));

        String stringAlfaNumerico = fakeValuesService.regexify("[a-z0-9]{8}");
        String stringNumerica = fakeValuesService.regexify("[0-9]{12}");



        return ProdutoRequestDTO.builder()
                .codigoProduto(stringAlfaNumerico)
                .codigoDeBarras(stringNumerica)
                .serie(LocalDate.now().getMonthValue() +"/"+ LocalDate.now().getYear())
                .nome(faker.commerce().productName())
                .descricao(faker.harryPotter().book())
                .categoria(faker.commerce().department())
                .valorBruto(new BigDecimal(faker.commerce().price().replace(",", ".")))
                .impostos(new BigDecimal(faker.commerce().price().replace(",", ".")))
                .dataDeFabricacao(LocalDate.now())
                .dataDeValidade(ProdutoUtil.localDatFormat(""))
                .cor(faker.commerce().color())
                .material(faker.commerce().material())
                .quantidade(faker.number().randomDigitNotZero())
                .build();

    }

    public static ProdutoRequestDTO updateRequest() {
        return ProdutoRequestDTO.builder()
                .codigoProduto("7t09o00n")
                .codigoDeBarras("929936360073")
                .serie("1/2018")
                .nome("Livro Padrões Projetos")
                .descricao("Livro melhores praticas")
                .categoria("ESCRITORIO")
                .valorBruto(new BigDecimal("59.90"))
                .impostos(new BigDecimal("15.0"))
                .dataDeFabricacao(ProdutoUtil.localDatFormat("22/08/2022"))
                .dataDeValidade(ProdutoUtil.localDatFormat(""))
                .cor("BRANCO")
                .material("PAPEL")
                .quantidade(6)
                .build();
    }

}
