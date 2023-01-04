package com.br.luizns.produtoapi.resources;

import com.br.luizns.produtoapi.dto.ProdutoDTO;
import com.br.luizns.produtoapi.dto.ProdutoRequestDTO;
import com.br.luizns.produtoapi.service.ProdutoService;
import com.br.luizns.produtoapi.util.ProdutoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listarTodosProdutos() {
        return ResponseEntity.ok().body(this.produtoService.listarTodosProdutos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarProdutoPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.produtoService.buscarProdutoPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProdutoDTO> cadastrarProduto(@RequestBody @Valid ProdutoRequestDTO dto) {

        ProdutoDTO produtoDto = produtoService.inserir(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(produtoDto.getId()).toUri();
        return ResponseEntity.created(uri).body(produtoDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProdutoDTO> update(@PathVariable Long id, @RequestBody @Valid ProdutoRequestDTO request) {
        ProdutoDTO produto = produtoService.update(id, request);
        return ResponseEntity.ok().body(produto);
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<List<ProdutoDTO>> uploadFile(@RequestParam("file") MultipartFile file) {

        if (ProdutoUtil.temFormatoCSV(file)) {
            try {
                return ResponseEntity.ok().body(produtoService.salvarArquivo(file));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
}