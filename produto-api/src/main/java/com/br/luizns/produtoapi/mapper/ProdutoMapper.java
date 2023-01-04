package com.br.luizns.produtoapi.mapper;

import com.br.luizns.produtoapi.dto.ProdutoDTO;
import com.br.luizns.produtoapi.dto.ProdutoRequestDTO;
import com.br.luizns.produtoapi.entity.Produto;
import com.br.luizns.produtoapi.service.ProdutoService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.security.Provider;

@Mapper(componentModel = "spring", imports = ProdutoService.class)
public interface ProdutoMapper {
    ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);


    @Mapping(expression = "java(ProdutoService.getValorFinal(produto))",target = "valorFinal")
    public  ProdutoDTO entidadeParaDto(Produto produto);

    public  Produto dtoParaEntidade(ProdutoRequestDTO request);
}