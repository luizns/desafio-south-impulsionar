UPDATE tb_produto
SET valor_final = (valor_bruto * (impostos / 100) + valor_bruto) * 1.45
WHERE valor_final isnull
   or valor_final = 0;