package com.br.luizns.desafio2.service;

import com.br.luizns.desafio2.convert.ProdutoConvert;
import com.br.luizns.desafio2.dto.ProdutoDto;
import com.br.luizns.desafio2.entity.Produto;
import com.br.luizns.desafio2.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Produto insert(Produto obj) {
        return produtoRepository.save(obj);
    }

    public void delete(Long id) {
        produtoRepository.deleteById(id);
    }

    public Produto update(Long id, Produto obj) {
        Optional<Produto> optional = produtoRepository.findById(id);
        Produto entity = optional.get();

        updateData(entity, obj);

        return produtoRepository.save(entity);
    }

    private void updateData(Produto entity, Produto obj) {
        entity.setCategoria(obj.getCategoria());
        entity.setCodigoProduto(obj.getCodigoProduto());
        entity.setCodigoDeBarras(obj.getCodigoDeBarras());
        entity.setSerie(obj.getSerie());
        entity.setNome(obj.getNome());
        entity.setDescricao(obj.getDescricao());
        entity.setCategoria(obj.getCategoria());
        entity.setValorBruto(obj.getValorBruto());
        entity.setImpostos(obj.getImpostos());
        entity.setDataDeFabricacao(obj.getDataDeFabricacao());
        entity.setDataDeValidade(obj.getDataDeValidade());
        entity.setCor(obj.getCor());
        entity.setMaterial(obj.getMaterial());
        entity.setQuantidade(obj.getQuantidade());
    }
}