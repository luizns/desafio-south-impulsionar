package com.br.luizns.desafio2.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@With
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_produto")
public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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