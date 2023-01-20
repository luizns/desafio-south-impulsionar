ALTER TABLE tb_produto
    ALTER COLUMN codigo_de_barras
        TYPE VARCHAR(15);

ALTER TABLE IF EXISTS tb_produto
    ALTER COLUMN quantidade
        SET DEFAULT 0;