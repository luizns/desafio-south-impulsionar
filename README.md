# DESAFIO BACKEND M2

## Descrição

A aplicação tem por objetivo realizar as operações de CRUD de uma API REST.
Onde é possivel realizar algumas opereções no gerenciamento do seu produto com as seguintes ações:

* Cadastrar um produtos
* Alterar um produtos
* Excluir um produtos
* Importar um produtos
* Listar os produtos cadastrados

## 🛠️ Construído com

* [IntelliJ](https://www.jetbrains.com/pt-br/idea/download/#section=windows) - IDE para desenvolvimento em Java
* [Java V. 11](https://www.java.com/pt-BR/) - GLinguagem para desenvolvimento multiplataforma
* [SpringBoot](https://spring.io/projects/spring-boot) - Framework Java com o objetivo de se construir aplicações REST
* [PostMan](https://www.postman.com/) - Plataforma de desenvolvimento de API.
* [DBeaver](https://dbeaver.io/download/) - Plataforma de banco de dados OpenSource e universal.
* [PostgreSQL](https://www.postgresql.org/download/) - Sistema de Gerenciamento de Banco de Dados.
* [Docker](https://www.docker.com/) - Plataforma de imagens e container.

### Para iniciar a aplicação é necessarios:

Para iniciar a aplicação é necessário ter configurado:
* Java JDK: a partir da versão 11.
* IDE/Editor de Codigo:  Eclipse, Intellij ou VSCode
* Banco de PostgreSQL ou image docker
* Docker Instalado

Para Iniciar a aplicação via terminal:
- Entre na pasta onde localizado o arquivo `docker-compose.yml` e a partir deste diretório digitar o seguinte comando:
 ```
  docker-compose up -d
 ```
para finalizar a aplicação digite:
 ```
  docker-compose down
 ```
Para Iniciar a aplicação via usando IDE, é necessário as seguintes configurações no PROFILE - `dev`:
- JDK 11 ou +
- POSTGRES CONFIGURADO:
  - PORTA: 5432
  - Database: produtodb
  - username: postgres
  - password: 1234567

ou altera o PROFILE para `local`para rodar em banco de dados em memória


## 🔌	Endpoints
Para acessar o banco de dados, é necessário acessar os endpoints que esta configurados via Postman:

```
http://localhost:8080
```

- ### Cadastrar novo Produtos - METODO POST

```
http://localhost:8080/produtos
```
```
{
    "codigoProduto": "8t09o00n",
    "codigoDeBarras": "729936360073",
    "serie": "1/2018",
    "nome": "Livro Padrões Projetos",
    "descricao": "Livro melhores praticas",
    "categoria": "ESCRITORIO",
    "valorBruto": 79.90,
    "impostos": 5.9,
    "dataDeFabricacao": "2022-08-01",
    "dataDeValidade": "2022-11-02",
    "cor": "BRANCO",
    "material": "PAPEL",
    "quantidade": 9
}
```


- ### Alterar Produto por ID - METODO PUT
```
http://localhost:8080/produtos/{ID}
```
```
{
    "codigoProduto": "8t09o00n",
    "codigoDeBarras": "729936360073",
    "serie": "1/2018",
    "nome": "Livro Padrões Projetos",
    "descricao": "Livro melhores praticas",
    "categoria": "ESCRITORIO",
    "valorBruto": 79.90,
    "impostos": 5.9,
    "dataDeFabricacao": "2022-08-01",
    "dataDeValidade": "2022-11-02",
    "cor": "BRANCO",
    "material": "PAPEL",
    "quantidade": 9
}
```

- ### Listar Produtos Cadastrados - METODO GET
```
http://localhost:8080/produtos
```

- ### Listar Produtos Cadastrados POR ID - METODO GET
```
http://localhost:8080/produtos/{id}
```

- ### Deletar Produtos Cadastrados POR ID - METODO DELETE
```
http://localhost:8080/produtos/{id}
```


- ### Importar  Produto - METODO POST
```
http://localhost:8080/produtos/upload
```

Como gerar arquivos de importação.

|código|codigo de barras|série|nome|descrição|categoria| valor bruto                         | impostos (%)               | data de fabricação       | data de validade | cor      | material |
|------|----------------|-----|----|---------|---------|-------------------------------------|----------------------------|--------------------------|-----------------|----------|----------|
|7t0do00n|629936360072|2/2016|Livro Design Patterns|"Livro sobre padrões de projeto de Erich Gamma, Ralph Johnson, Richard Helm e John Vlissides"|ESCRITÓRIO| "101,11"| 25| 18/05/2016| n/a| n/a| papel    |

#### Modelo do arquivos gerado com extensão CSV (Ex: `arquivo.csv`)
<br>

<a href="./desafio2/mostruario_fabrica.csv/">Arquivos de  Importação</a>

<br>
