# API PRODUTO - RABBITMQ E SPRINGBOOT

## Descrição

A aplicação tem por objetivo realizar as operações de CRUD de uma API REST.
Onde é possivel realizar algumas opereções no gerenciamento do seu produto com as seguintes ações:

* Cadastrar um produtos
* Alterar um produtos
* Excluir um produtos
* Importar um produtos
* Listar os produtos cadastrados
* Enviar solicitação para uma fila no RabbitMQ
* Faz uma alteração na quantidade produto após consumir a fila no RabbitMQ

## 🛠️ Tecnologias & Ferramentas

<div align="center">

|                           Tecnologia                            |                                   Ferramentas                                   |
|:---------------------------------------------------------------:|:-------------------------------------------------------------------------------:|
|   [SpringBoot 2.6.13](https://spring.io/projects/spring-boot)   | [IntelliJ Idea](https://www.jetbrains.com/pt-br/idea/download/#section=windows) |
|            [Java v.11](https://www.java.com/pt-BR/)             |                     [DBeaver](https://dbeaver.io/download/)                     |
|       [RabbitMQ](https://www.rabbitmq.com/download.html)        |                       [PostMan](https://www.postman.com/)                       |
|          [JUnit5](https://pt.wikipedia.org/wiki/JUnit)          |                        [Docker](https://www.docker.com/)                        |
|        [Mockito](https://en.wikipedia.org/wiki/Mockito)         |               [Docker-compose](https://docs.docker.com/compose/)                |
|       [PostgreSQL](https://www.postgresql.org/download/)        |                                                                                 |
|              [Lombok](https://projectlombok.org/)               |                                                                                 |
|               [Mapstruct](https://mapstruct.org/)               ||
| [Banco de dados  H2](https://www.h2database.com/html/main.html) ||
|                 [Flyway](https://flywaydb.org/)                 ||

</div>

### Para iniciar a aplicação é necessarios:

Requisitos para rodar aplicação:

* Java JDK: a partir da versão 11.
* IDE/Editor de Codigo:  Eclipse, Intellij ou VSCode
* Banco de PostgreSQL ou image docker
* Docker Instalado
* Maven

- POSTGRES CONFIGURADO:
    - PORTA: 5432
    - Database: produtodb
    - username: postgres
    - password: 12345678

  <br>

- CONFIGURACÃO RABBITMQ:
    - PORTA: 15672
    - username: guest
    - password: 12345678

endereço de acesso:

```
      http://localhost:15672/
```

- Entre na pasta onde localizado o arquivo `docker-compose.yml` e a partir deste diretório digitar o seguinte comando:

Para iniciar a aplicação abra o terminal da IDE:

 ``` shell | bash
  cd produto-api/
 ````

Para gerar um JAR da aplicação sem os Testes, digite o codigo abaixo:

 ``` sh
  mvn clean package -DskipTests
 ````

Para gerar um o JAR como os teste é necessarios o RabbitMQ está conectado, vá no
diretorio principal da aplicção digite o camando:

``` sh
  docker-compose up rabbitmq -d
 ````

Após iniciar RabbitMQ
Gerar um JAR como os teste

``` sh
  mvn clean package
 ````

para finalizar a aplicação no docker-compose digite:

 ```sh
  docker-compose down
 ```

### Após gerar o JAR da aplicação:

- #### Gerar uma imagem da aplicação com:

    - RabbitMQ
    - Aplicação Spring Boot
    - Banco de Dados Postgres

Digite o o camando via terminal, necessário está diretorio principal da aplicação:

 ```sh
  docker-compose up -d
 ```

## 🔌 Endpoints Para Consumir API Via Postman

[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/24127932-d2131eb4-0611-4199-b0d7-733a72daf789?action=collection%2Ffork&collection-url=entityId%3D24127932-d2131eb4-0611-4199-b0d7-733a72daf789%26entityType%3Dcollection%26workspaceId%3D888345a5-e1f4-4eb9-bf83-c2c16ab7a69f#?env%5BTeste%5D=W3sia2V5IjoidXJsX2Jhc2UiLCJ2YWx1ZSI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MCIsImVuYWJsZWQiOnRydWUsInR5cGUiOiJkZWZhdWx0Iiwic2Vzc2lvblZhbHVlIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwIiwic2Vzc2lvbkluZGV4IjowfSx7ImtleSI6ImlkIiwidmFsdWUiOiIyIiwiZW5hYmxlZCI6dHJ1ZSwic2Vzc2lvblZhbHVlIjoiMSIsInNlc3Npb25JbmRleCI6MX0seyJrZXkiOiJxdWFudGlkYWRlIiwidmFsdWUiOiIxMCIsImVuYWJsZWQiOnRydWUsInNlc3Npb25WYWx1ZSI6IjEwIiwic2Vzc2lvbkluZGV4IjoyfSx7ImtleSI6ImNvZGlnby1wcm9kdXRvIiwidmFsdWUiOiI3dDBkbzAwbiIsImVuYWJsZWQiOnRydWUsInNlc3Npb25WYWx1ZSI6Ijd0MGRvMDBuIiwic2Vzc2lvbkluZGV4IjozfV0=)

Então, para acessar o banco de dados, é necessário acessar os endpoints que esta configurados no caminho abaixo e
listados mais abaixo:

```
http://localhost:8080
```

| Tipo   | Descrição                                          | Caminho                                   |
|--------|----------------------------------------------------|-------------------------------------------|
| GET    | Listar Produtos Cadastrados                        | /produtos                                 |
| POST   | Cadastrar novo Produtos                            | /produtos                                 |
| DELETE | Deletar Produtos Cadastrados POR ID                | /produtos/{id}                            |
| POST   | Importar  Produto                                  | /produtos/upload                          |
| GET    | Listar Produtos Cadastrados POR ID                 | /produtos/{id}                            |
| PUT    | Alterar Produto por ID                             | /produtos/{id}                            |
| PATCH  | Alterar produto - Enviar para uma fila no RabbitMQ | /produtos/{{codigo-produto}}?quantidade=5 |
