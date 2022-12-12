package com.br.luizns.desafio2.service;

import com.br.luizns.desafio2.convert.ProdutoConvert;
import com.br.luizns.desafio2.dto.ProdutoDto;
import com.br.luizns.desafio2.dto.ProdutoRequestDto;
import com.br.luizns.desafio2.entity.Produto;
import com.br.luizns.desafio2.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<ProdutoDto> findAll() {
        return this.produtoRepository.findAll().stream().map(ProdutoConvert::entityToDto).collect(Collectors.toList());
    }

    public ProdutoDto findById(Long id) {
        return this.produtoRepository
                .findById(id)
                .map(ProdutoConvert::entityToDto)
                .orElseThrow(RuntimeException::new);
    }

    public ProdutoDto insert(ProdutoRequestDto request) {
        Assert.isNull(request.getId(), "Não foi possível inserir o registro");

        return ProdutoConvert
                .entityToDto(this.produtoRepository
                        .save(ProdutoConvert.dtoToEntity(request)));
    }

    public void delete(Long id) {
        this.produtoRepository
                .findById(id)
                .ifPresent(entity -> this.produtoRepository.delete(entity));
    }

    public ProdutoDto update(Long id, ProdutoRequestDto request) {

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
}