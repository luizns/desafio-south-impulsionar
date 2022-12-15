CREATE TABLE IF NOT EXISTS public.tb_produto (
	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	categoria varchar(255) NULL,
	codigo_de_barras varchar(255) NULL,
	codigo_produto varchar(255) NULL,
	cor varchar(255) NULL,
	data_de_fabricacao date NULL,
	data_de_validade date NULL,
	descricao varchar(255) NULL,
	impostos numeric(19, 2) NULL,
	material varchar(255) NULL,
	nome varchar(255) NULL,
	quantidade int4 NULL,
	serie varchar(255) NULL,
	valor_bruto numeric(19, 2) NULL,
	CONSTRAINT tb_produto_pkey PRIMARY KEY (id)
);