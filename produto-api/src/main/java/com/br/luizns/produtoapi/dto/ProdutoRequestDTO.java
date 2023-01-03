package com.br.luizns.produtoapi.dto;

import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@With
public class ProdutoRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String codigoProduto;
    private String codigoDeBarras;
    private String serie;
    private String nome;
    private String descricao;
    private String categoria;
    private BigDecimal valorBruto;
    private BigDecimal impostos;
    private LocalDate dataDeFabricacao;
    private LocalDate dataDeValidade;
    private String cor;
    private String material;
    private Integer quantidade;
}