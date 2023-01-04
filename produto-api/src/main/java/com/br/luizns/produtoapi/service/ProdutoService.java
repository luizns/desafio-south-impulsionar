package com.br.luizns.produtoapi.service;

import com.br.luizns.produtoapi.convert.ProdutoConvert;
import com.br.luizns.produtoapi.dto.ProdutoDTO;
import com.br.luizns.produtoapi.dto.ProdutoRequestDTO;
import com.br.luizns.produtoapi.entity.Produto;
import com.br.luizns.produtoapi.mapper.ProdutoMapper;
import com.br.luizns.produtoapi.repository.ProdutoRepository;
import com.br.luizns.produtoapi.service.exceptions.ResourceNotFoundException;
import com.br.luizns.produtoapi.util.ProdutoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;


    public List<ProdutoDTO> listarTodosProdutos() {
        return this.produtoRepository.findAll().stream().map(ProdutoMapper.INSTANCE::entidadeParaDto).collect(Collectors.toList());
    }

    public ProdutoDTO buscarProdutoPorId(Long id) {
        return this.produtoRepository
                .findById(id)
                .map(ProdutoMapper.INSTANCE::entidadeParaDto)
                .orElseThrow(() -> new ResourceNotFoundException("Id produto não encontrado: " + id));
    }

    public ProdutoDTO inserir(ProdutoRequestDTO request) {

        Assert.isNull(request.getId(), "Não foi possível inserir o produto");

        var produto = ProdutoMapper.INSTANCE.dtoParaEntidade(request);

        boolean codidoProduto = produtoRepository.findByCodigoProduto(produto.getCodigoProduto())
                .stream()
                .anyMatch(produtoExistente -> !produtoExistente.equals(produto));

        if (codidoProduto) {
            throw new DataIntegrityViolationException("Produto já cadastrado na base dados: COD. = " + produto.getCodigoProduto());
        }
        return ProdutoMapper.INSTANCE.entidadeParaDto(this.produtoRepository.save(produto));


    }

    public void delete(Long id) {
        this.produtoRepository
                .findById(id)
                .ifPresent(entity -> this.produtoRepository.delete(entity));
    }

    public ProdutoDTO update(Long id, ProdutoRequestDTO request) {

        Optional<Produto> optional = produtoRepository.findById(id);
        if (optional.isPresent()) {
            Produto db = optional.get();
            db.setCategoria(request.getCategoria());
            db.setCodigoProduto(request.getCodigoProduto());
            db.setCodigoDeBarras(request.getCodigoDeBarras());
            db.setSerie(request.getSerie());
            db.setNome(request.getNome());
            db.setDescricao(request.getDescricao());
            db.setCategoria(request.getCategoria());
            db.setValorBruto(request.getValorBruto());
            db.setImpostos(request.getImpostos());
            db.setDataDeFabricacao(request.getDataDeFabricacao());
            db.setDataDeValidade(request.getDataDeValidade());
            db.setCor(request.getCor());
            db.setMaterial(request.getMaterial());
            db.setQuantidade(request.getQuantidade());

            produtoRepository.save(db);

            return ProdutoConvert.entityToDto(db);
        } else {
            throw new RuntimeException("Não foi possível atualizar o registro");

        }
    }

    public List<ProdutoDTO> salvarArquivo(MultipartFile file) {

        try {
            List<Produto> list = ProdutoUtil.csvParaProduto(file.getInputStream());
            return this.produtoRepository.saveAll(list).stream().map(ProdutoConvert::entityToDto).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Falha ao armazenar dados csv: " + e.getMessage());
        }


    }


    public static BigDecimal getValorFinal(Produto request) {

        var valorBrutoProduto = request.getValorBruto();
        var valorImpostoProduto = request.getImpostos();

        var percentual = new BigDecimal("100.00");
        var taxaPercentualDaMargemDeLucro = new BigDecimal("45.0")
                .divide(percentual, 3, RoundingMode.HALF_EVEN)
                .add(new BigDecimal("1.00"));

        var calculoTaxaImpostoMaisValorBruto = valorBrutoProduto.multiply(
                (valorImpostoProduto.add(new BigDecimal("100.00"))
                        .divide(percentual, 3, RoundingMode.HALF_EVEN)));
        var calculoValorFinal = calculoTaxaImpostoMaisValorBruto.multiply(taxaPercentualDaMargemDeLucro);

        return calculoValorFinal.setScale(2, RoundingMode.HALF_EVEN);
    }


}