package com.br.luizns.produtoapi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
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
    @NotBlank
    private String codigoProduto;

    private String codigoDeBarras;
    private String serie;
    @NotBlank(message = "campo não informado")
    private String nome;
    private String descricao;

    @NotBlank(message = "campo não informado")
    private String categoria;
    @Min(value = 0)
    @PositiveOrZero
    private BigDecimal valorBruto;

    private BigDecimal impostos;

    @Column
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataDeFabricacao;
    @Column
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataDeValidade;
    private String cor;
    private String material;
    @Min(value = 0)
    @PositiveOrZero
    private Integer quantidade;
}