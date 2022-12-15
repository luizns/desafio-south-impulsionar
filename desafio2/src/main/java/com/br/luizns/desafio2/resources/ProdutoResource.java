package com.br.luizns.desafio2.resources;

import com.br.luizns.desafio2.dto.ProdutoDto;
import com.br.luizns.desafio2.dto.ProdutoRequestDto;
import com.br.luizns.desafio2.service.ProdutoService;
import com.br.luizns.desafio2.util.ProdutoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    public ResponseEntity<ProdutoDto> update(@PathVariable Long id, @RequestBody ProdutoRequestDto request) {
        ProdutoDto produto = produtoService.update(id, request);
        return ResponseEntity.ok().body(produto);
    }

    @PostMapping("/upload")
    public ResponseEntity<List<ProdutoDto>> uploadFile(@RequestParam("file") MultipartFile file) {

        if (ProdutoUtil.temFormatoCSV(file)) {
            try {
                produtoService.salvar(file);

                return ResponseEntity.ok().body(produtoService.salvar(file));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }
        return ResponseEntity.badRequest().build();
    }

}