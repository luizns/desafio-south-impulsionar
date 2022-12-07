package com.br.luizns.desafio2.resources;

import com.br.luizns.desafio2.entity.Produto;
import com.br.luizns.desafio2.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<Produto>> findaAll() {
        List<Produto> list = produtoService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Produto> findById(@PathVariable Long id) {
        Produto obj = produtoService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

}
