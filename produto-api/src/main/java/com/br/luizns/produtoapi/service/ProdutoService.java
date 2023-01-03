package com.br.luizns.produtoapi.service;

import com.br.luizns.produtoapi.convert.ProdutoConvert;
import com.br.luizns.produtoapi.dto.ProdutoDTO;
import com.br.luizns.produtoapi.dto.ProdutoRequestDTO;
import com.br.luizns.produtoapi.entity.Produto;
import com.br.luizns.produtoapi.repository.ProdutoRepository;
import com.br.luizns.produtoapi.util.ProdutoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<ProdutoDTO> findAll() {
        return this.produtoRepository.findAll().stream().map(ProdutoConvert::entityToDto).collect(Collectors.toList());
    }

    public ProdutoDTO findById(Long id) {
        return this.produtoRepository
                .findById(id)
                .map(ProdutoConvert::entityToDto)
                .orElseThrow(RuntimeException::new);
    }

    public ProdutoDTO insert(ProdutoRequestDTO request) {
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
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }


    }





}