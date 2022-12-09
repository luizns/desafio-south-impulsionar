package com.br.luizns.desafio2.resources;

import com.br.luizns.desafio2.dto.ProdutoDto;
import com.br.luizns.desafio2.dto.ProdutoRequestDto;
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
    public ResponseEntity<List<ProdutoDto>> findAll() {
        return ResponseEntity.ok().body(this.produtoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDto> byId(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.produtoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProdutoDto> insert(@RequestBody ProdutoRequestDto dto) {
        try {
            ProdutoDto produtoDto = produtoService.insert(dto);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(produtoDto.getId()).toUri();
            return ResponseEntity.created(uri).body(produtoDto);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Produto> put(@PathVariable Long id, @RequestBody Produto produto) {
        produto = produtoService.update(id, produto);
        return ResponseEntity.ok().body(produto);
    }
}