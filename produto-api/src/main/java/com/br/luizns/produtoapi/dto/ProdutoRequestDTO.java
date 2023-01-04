package com.br.luizns.produtoapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.With;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@With
public class ProdutoRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    @Column(unique = true)
    @Length(min = 8, max = 8, message = "Tamanho 8 carecteres")
    private String codigoProduto;

    @Length(min = 12, max = 12, message = "Tamanho permitidos 12 carecteres")
    private String codigoDeBarras;
    private String serie;

    @Length(min = 3, max = 40, message = "O nome deverá ter no mínimo {min} e no  máximo {max} caracteres")
    @NotBlank(message = "campo não informado")
    private String nome;

    @Length(min = 5, max = 100, message = "O nome deverá ter no mínimo {min} e no  máximo {max} caracteres")
    private String descricao;

    @NotBlank(message = "campo não informado")
    private String categoria;
    @Min(value = 0)
    @PositiveOrZero
    private BigDecimal valorBruto;

    @Min(value = 0)
    @PositiveOrZero
    private BigDecimal impostos;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataDeFabricacao;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataDeValidade;
    private String cor;
    private String material;
    @Min(value = 0)
    @PositiveOrZero(message = "campo não pode ser menor que zero")
    private Integer quantidade;

}