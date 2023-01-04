package com.br.luizns.produtoapi.convert;

import com.br.luizns.produtoapi.dto.ProdutoDTO;
import com.br.luizns.produtoapi.dto.ProdutoRequestDTO;
import com.br.luizns.produtoapi.entity.Produto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ProdutoConvert {
    public static Produto dtoToEntity(ProdutoRequestDTO request) {
        return Produto.builder()
                .codigoProduto(request.getCodigoProduto())
                .codigoDeBarras(request.getCodigoDeBarras())
                .serie(request.getSerie())
                .nome(request.getNome())
                .descricao(request.getDescricao())
                .categoria(request.getCategoria())
                .valorBruto(request.getValorBruto())
                .impostos(request.getImpostos())
                .dataDeFabricacao(request.getDataDeFabricacao())
                .dataDeValidade(request.getDataDeValidade())
                .cor(request.getCor())
                .material(request.getMaterial())
                .quantidade(request.getQuantidade())
                .build();
    }

    public static ProdutoDTO entityToDto(Produto entity) {

//        BigDecimal porcentos = new BigDecimal("100.0");
//        BigDecimal taxa = new BigDecimal("45.0").divide(porcentos);
//        BigDecimal valorImposto = entity.getValorBruto().multiply((entity.getImpostos().divide(porcentos)));
//        BigDecimal calculoTaxa = (valorImposto.add(entity.getValorBruto())).multiply(taxa);
//        var valorTotal  = entity.getValorBruto().add(valorImposto).add(calculoTaxa).setScale(2, RoundingMode.HALF_EVEN);


        return ProdutoDTO.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .categoria(entity.getCategoria())
                .quantidade(entity.getQuantidade())
                .build();
    }



}