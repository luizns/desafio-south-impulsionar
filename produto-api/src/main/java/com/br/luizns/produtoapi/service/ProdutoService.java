package com.br.luizns.produtoapi.service;

import com.br.luizns.produtoapi.dto.ProdutoDTO;
import com.br.luizns.produtoapi.dto.ProdutoRequestDTO;
import com.br.luizns.produtoapi.entity.Produto;
import com.br.luizns.produtoapi.mapper.ProdutoMapper;
import com.br.luizns.produtoapi.repository.ProdutoRepository;
import com.br.luizns.produtoapi.service.exceptions.ResourceNotFoundException;
import com.br.luizns.produtoapi.util.ProdutoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ProdutoMapper produtoMapper;

    @Autowired
    private RabbitMQService rabbitmqService;


    public List<ProdutoDTO> listarTodosProdutos() {
        return this.produtoRepository.findAll().stream().map(produtoMapper.INSTANCE::entidadeParaDto).collect(Collectors.toList());
    }

    public ProdutoDTO buscarProdutoPorId(Long id) {
        return this.produtoRepository
                .findById(id)
                .map(produtoMapper.INSTANCE::entidadeParaDto)
                .orElseThrow(() -> new ResourceNotFoundException("Id produto não encontrado: " + id));
    }

    public ProdutoDTO inserir(ProdutoRequestDTO request) {

        Assert.isNull(request.getId(), "Não foi possível inserir o produto");

        var produto = produtoMapper.INSTANCE.dtoParaEntidade(request);

        var codidoProduto = buscarProdutoCodigoProduto(produto);

        if (codidoProduto) {
            throw new DataIntegrityViolationException("Produto já cadastrado na base dados: COD. = " + produto.getCodigoProduto());
        }

        produto.setValorFinal(getValorFinal(produto.getValorBruto(), produto.getImpostos()));
        return produtoMapper.INSTANCE.entidadeParaDto(this.produtoRepository.save(produto));
    }

    public void delete(Long id) {
        try {
            this.produtoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id não encontrado: " + id);
        }
    }

    public String alterarQuantidadeEstoque(String codigoProduto, Integer quantidade) {
        return this.produtoRepository
                .findByCodigoProduto(codigoProduto)
                .map(produto -> {
                    produto.setQuantidade(quantidade);
                    this.rabbitmqService.enviarMensagem(produto);
                    return "CodigoProduto: " + produto.getCodigoProduto();
                }).orElseThrow(() -> new ResourceNotFoundException("Código do produto não encontrado: " + codigoProduto));
    }

    public ProdutoDTO atualizarProduto(Long id, ProdutoRequestDTO request) {

        return this.produtoRepository.findById(id)
                .map(produto -> {
                            produto.setCodigoProduto(request.getCodigoProduto());
                            produto.setCodigoDeBarras(request.getCodigoDeBarras());
                            produto.setSerie(request.getSerie());
                            produto.setNome(request.getNome());
                            produto.setDescricao(request.getDescricao());
                            produto.setCategoria(request.getCategoria());
                            produto.setValorBruto(request.getValorBruto());
                            produto.setImpostos(request.getImpostos());
                            produto.setDataDeFabricacao(request.getDataDeFabricacao());
                            produto.setDataDeValidade(request.getDataDeValidade());
                            produto.setCor(request.getCor());
                            produto.setMaterial(request.getMaterial());
                            produto.setQuantidade(request.getQuantidade());

                            produtoRepository.save(produto);

                            return produtoMapper.INSTANCE.entidadeParaDto(produto);
                        }
                )
                .orElseThrow(() -> new ResourceNotFoundException("Id não encontrado: " + id));

    }

    public List<ProdutoDTO> salvarArquivo(MultipartFile file) {

        try {
            List<Produto> list = ProdutoUtil.csvParaProduto(file.getInputStream());
            return this.produtoRepository.saveAll(list).stream().map(produtoMapper.INSTANCE::entidadeParaDto).collect(Collectors.toList());
        } catch (IOException e) {
            throw new ResourceNotFoundException("Falha ao armazenar dados csv: " + e.getMessage());
        }

    }

    public static BigDecimal getValorFinal(BigDecimal valorBrutoProduto, BigDecimal impostoProduto) {

        if (Objects.equals(valorBrutoProduto, BigDecimal.valueOf(0)) ||
                Objects.equals(impostoProduto, BigDecimal.valueOf(0))) {
            return BigDecimal.ZERO;
        }

        var percentual = new BigDecimal("100.00");
        var taxaPercentualDaMargemDeLucro = new BigDecimal("45.0")
                .divide(percentual, 3, RoundingMode.HALF_EVEN)
                .add(new BigDecimal("1.00"));

        var calculoTaxaImpostoMaisValorBruto = valorBrutoProduto.multiply(
                (impostoProduto.add(new BigDecimal("100.00"))
                        .divide(percentual, 3, RoundingMode.HALF_EVEN)));
        var calculoValorFinal = calculoTaxaImpostoMaisValorBruto.multiply(taxaPercentualDaMargemDeLucro);

        return calculoValorFinal.setScale(2, RoundingMode.HALF_EVEN);
    }

    public Boolean buscarProdutoCodigoProduto(Produto request) {
        return produtoRepository.findByCodigoProduto(request.getCodigoProduto())
                .stream()
                .anyMatch(produtoExistente -> !produtoExistente.equals(request));
    }


}
