package com.br.luizns.produtoapi.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@With
public class ProdutoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private String categoria;
    private Integer quantidade;
    private BigDecimal valoTotal;
}