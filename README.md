[![Build Status](https://travis-ci.org/m4rciosouza/ponto-inteligente-api.svg?branch=master)](https://travis-ci.org/m4rciosouza/ponto-inteligente-api)
# Desafio Desenvolvedor JAVA
API do Desafio Desenvolvedor JAVA - SURA com Java e Spring Boot.
### Detalhes da API RESTful
A API RESTful do Desafio Técnico contém as seguintes características:  
* Projeto criado com Spring Boot e Java 8
* Banco de dados POSTGRES com JPA e Spring Data JPA
* Autenticação e autorização com Spring Security e tokens JWT (JSON Web Token)
* Migração de banco de dados com Flyway
* Testes unitários e de integração com JUnit e Mockito
* Caching com EhCache
* Integração contínua com TravisCI
### Como executar a aplicação
Certifique-se de ter o Maven instalado e adicionado ao PATH de seu sistema operacional, assim como o Git.
```

Caso não tenha o postgres intalado em sua maquina rodar os sequintes comandos
 se não tiver o docker instalado siga as intruções da documentação oficial do Docker
    https://docs.docker.com/get-docker/
    
Criando Docker do banco de dados
 # docker run -p 5432:5432 --name dockerpostgresql -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=desafio_sura -d postgres:10.7-alpine

Criar Rede para Conectar container do banco com da aplicação
 # docker network create postgresnetwork
 
Conectar Container a rede criada
 # docker network connect postgresnetwork dockerpostgresql 
 
Se já tiver o postgres Instalado criar o banco  com o seguinte nome desafio_sura

git clone https://github.com/luizbsilva/sura_seguros_desafio.git
cd sura_seguros_desafio
mvn spring-boot:run
Acesse os endpoints através da url http://localhost:8080
```
### Importando o projeto no Eclipse ou STS
No terminal, execute a seguinte operação:
```
mvn eclipse:eclipse
```
No Eclipse/STS, importe o projeto como projeto Maven.

Usuário BD: postgres
Senha: postgres

URLS:

|Serviço|Url|Swagger/BD|
|-------|---|-------|
|API|http://localhost:8080/|http://localhost:8080/swagger-ui.html|
|DOC|http://localhost:8080/|http://localhost:8080/v2/api-docs|
