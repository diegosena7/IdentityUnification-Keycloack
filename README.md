# 📄 Projeto Contract Service com Keycloak e PostgreSQL

Este projeto é um serviço Spring Boot que gerencia criação de usuários no Keycloak e persiste dados no PostgreSQL, rodando em um ambiente containerizado com Docker Compose.

---

## 🚀 Tecnologias Utilizadas

- **Java 17** + **Spring Boot**
- **Spring Security OAuth2** (Keycloak)
- **PostgreSQL** como banco de dados
- **Keycloak** para autenticação e gerenciamento de usuários
- **Docker e Docker Compose** para orquestração

---

## ▶️ Como executar o projeto

### 1️⃣ Pré-requisitos

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

---

### 2️⃣ Subir os containers

Na raiz do projeto, execute:

```bash
docker-compose up --build
```

Isso irá subir:

- **PostgreSQL** (porta 5432 no host)
- **Keycloak** (porta 7080 no host)
- **Contract Service** (porta 8082 no host)

---

### 3️⃣ Testar a aplicação via curl

Após os serviços estarem de pé, você pode testar o endpoint de registro com o seguinte comando:

```bash
curl --location 'http://localhost:8082/api/v1/register' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=DA6ED050B6D6BB2A0A2B9211A37FE65F' \
--data-raw '{
  "username": "diegosena7",
  "password": "Bootcamp01",
  "email": "diegos777@impacta.com.br",
  "firstName" : "Diego",
  "lastName" : "SilSena",
  "customerId": "032584131904"
}'
```

Caso esteja rodando o projeto localmente e queira testar por Postman, basta importar este comando ou configurar uma requisição **POST** para `http://localhost:8082/api/v1/register` com o corpo JSON acima.

---

## 💄 Acessando o banco de dados PostgreSQL

Você pode inspecionar as tabelas e rodar queries diretamente no container do PostgreSQL.

### Abrir um shell no container do Postgres

```bash
docker exec -it postgres bash
```

### Conectar ao banco `bootcamp` via psql

Dentro do shell do container:

```bash
psql -U bootcamp -d bootcamp
```

### Comandos úteis no psql

- Listar tabelas:

```sql
\dt
```

- Consultar dados de uma tabela:

```sql
SELECT * FROM nome_da_tabela LIMIT 10;
```

- Sair do psql:

```sql
\q
```

---

### Executar uma query direto sem entrar no bash

Se preferir, rode direto no host:

```bash
docker exec -it postgres psql -U bootcamp -d bootcamp -c "SELECT * FROM nome_da_tabela LIMIT 5;"
```

---

## 🔧 Ajustes e Configurações

O serviço está configurado para acessar o Keycloak na rede Docker interna:

```yaml
keycloak:
  url: http://keycloak:8080
```

No host (para acessar o console do Keycloak), use:

```
http://localhost:7080
```

Usuário e senha padrão do Keycloak:

```
admin / admin
```

---

## 📌 Observações

- Certifique-se de que as portas 5432, 7080 e 8082 estejam livres no seu host antes de subir os containers.
- O mapeamento de volumes do PostgreSQL (`pgdata`) preserva os dados entre reinicializações.

---

💡 **Dúvidas?**\
Abra uma issue ou entre em contato com o responsável pelo projeto. 🚀

