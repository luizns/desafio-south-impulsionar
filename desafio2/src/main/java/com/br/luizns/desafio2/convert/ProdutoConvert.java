package com.br.luizns.desafio2.convert;

import com.br.luizns.desafio2.dto.ProdutoDto;
import com.br.luizns.desafio2.dto.ProdutoRequestDto;
import com.br.luizns.desafio2.entity.Produto;

import java.util.List;
import java.util.stream.Collectors;

public class ProdutoConvert {
    public static Produto dtoToEntity(ProdutoRequestDto request) {
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

    public static ProdutoDto entityToDto(Produto entity) {
        return ProdutoDto.builder()
                .id(entity.getId())
                 .codigoProduto(entity.getCodigoProduto())
                .codigoDeBarras(entity.getCodigoDeBarras())
                .serie(entity.getSerie())
                .nome(entity.getNome())
                .descricao(entity.getDescricao())
                .categoria(entity.getCategoria())
                .valorBruto(entity.getValorBruto())
                .impostos(entity.getImpostos())
                .dataDeFabricacao(entity.getDataDeFabricacao())
                .dataDeValidade(entity.getDataDeValidade())
                .cor(entity.getCor())
                .material(entity.getMaterial())
                .quantidade(entity.getQuantidade())
                .build();
    }

}

