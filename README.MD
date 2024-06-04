# MINI AUTORIZADOR

# Tecnologias utilizadas

- **[Chat GPT](https://chatgpt.com/)**
- **[Spring Boot](https://spring.io/projects/spring-boot)**
- **[Spring Data JPA](https://spring.io/projects/spring-data-jpa#overview)** 
- **[Hibernate](https://hibernate.org/orm/)**
- **[Docker](https://www.docker.com/)**
- **[MySQL](https://www.mysql.com/)**
- **[H2](https://h2database.com/)**

# Requisitos para executar o projeto
- [Git](https://git-scm.com/)
- [Docker](https://www.docker.com/)
- [JDK 11+](https://www.oracle.com/br/java/technologies/javase/jdk11-archive-downloads.html)

# Como executar o projeto
- Clone o projeto.
```bash
  git clone https://github.com/choliveira84/mini-autorizador.git
```
- Abra um terminal na raiz do projeto e execute o comando abaixo para iniciar o banco de dados Mysql no docker.
```bash
  cd src/docker && docker-compose up -d
```
- Alternativamente, pode executar a aplicação utilizando o banco em memória H2 executando a aplicação com o profile local ativo.

- Abra a IDE de sua preferência e importe o projeto clonado e aguarde o download de todas dependências do projeto

- Execute o arquivo com a classe main MiniAutorizadorApplication.java

- Acesse a interface dos recursos do backend através do swagger usando o endereço local http://localhost:8080/swagger.ui.html


# Rotas
## Cartão Controller
| Método  | Path  | Descrição  |
| ------------ | ------------ | ------------ |
| POST  |  /cartoes | Cria um novo cartão |
| GET  |  /cartoes/{numeroCartao} | Consulta saldo do cartão |

## Transação Controller
| Método  | Path  | Descrição  |
| ------------ | ------------ | ------------ |
| POST  |  /transacoes | Realiza uma transação |