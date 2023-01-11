CREATE TABLE IF NOT EXISTS tb_produto (
	id bigint  NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	codigo_de_barras varchar(255),
	codigo_produto varchar(255),
	serie varchar(255),
	nome varchar(255),
	descricao varchar(255),
	categoria varchar(255),
	valor_bruto numeric(19, 2),
	impostos numeric(19, 2),
	data_de_fabricacao date,
	data_de_validade date,
	cor varchar(255),
	material varchar(255),
	quantidade numeric(18),
	valor_final numeric(19, 2),
	CONSTRAINT tb_produto_pkey PRIMARY KEY (id)
);