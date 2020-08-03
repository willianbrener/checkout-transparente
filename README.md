# Sistema SGA

Sistema para servir de base à outros sistemas.

## Informações

O projeto foi publicado no heroku. O acesso é através do link 
[https://sga-init.herokuapp.com/](https://sga-init.herokuapp.com/)

Usuários para testes
- Usuário: 'admin' | Senha: '123456'
- Usuário: 'user' | Senha: '123456'

## Execução Local

É possível importar o projeto em sua IDE e executar a classe SpringBootRun.java.

Caso prefira executar via linha de comando, seguem as instruções abaixo:

Abra o terminal na raiz do projeto e instale o projeto via maven

```bash
mvn install
```

Rode o comando para executar o spring boot e subir a aplicação

```bash
mvn spring-boot:run
```

Ao terminar de subir a aplicação,o acesso ao sistema será pelo link:
[http://localhost:8080](http://localhost:8080)

## Extras
É possível utilizar o banco Postgres descomentando a linha 2 do application.properties e definindo a conexão com o banco

## Subir aplicação no Heroku

Será necessário criar uma conta no heroku.
[https://www.heroku.com/](https://www.heroku.com/)

Descomentar as linhas do application.properties

```bash
#spring.datasource.url=${JDBC_DATABASE_URL}
#spring.datasource.username=${JDBC_DATABASE_USERNAME}
#spring.datasource.password=${JDBC_DATABASE_PASSWORD}
```
No terminal executar os seguintes comandos:

```bash
heroku login
```

```bash
heroku create NOME_PROJETO
```

```bash
heroku addons:create heroku-postgresql:hobby-dev
```

```bash
git push heroku master
```
Para logs segue o comando:

```bash
heroku logs --tail
```