package com.br.luizns.produtoapi.repository;

import com.br.luizns.produtoapi.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query(value = "select p from Produto p where p.codigoProduto=:codigoProduto")
    Optional<Produto> findByCodigoProduto(String codigoProduto);
}