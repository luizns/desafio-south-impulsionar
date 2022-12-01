package com.br.luizns.desafio2.resources;

import com.br.luizns.desafio2.entity.Produto;
import com.br.luizns.desafio2.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService produtoService;

    public ResponseEntity<List<Produto>> findaAll() {
        List<Produto> list = produtoService.findAll();
        return ResponseEntity.ok().body(list);
    }
}
