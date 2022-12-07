package com.br.luizns.desafio2.service;

import com.br.luizns.desafio2.entity.Produto;
import com.br.luizns.desafio2.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    public Produto findById(Long id) {
        Optional<Produto> obj = produtoRepository.findById(id);

        return obj.orElseThrow(() -> new RuntimeException());
    }

}
