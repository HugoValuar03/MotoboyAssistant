# Motoboy Assistant API

API REST do **Motoboy Assistant**, sistema voltado para o controle de corridas, ganhos, distância percorrida e média por quilômetro de motoboys e entregadores.

Este repositório contém o **backend** da aplicação, responsável por gerenciar corridas, plataformas, filtros, paginação e resumo financeiro consumido pelo frontend Angular.

---

## Sobre o projeto

O Motoboy Assistant foi criado com o objetivo de ajudar entregadores a acompanharem melhor seus ganhos e desempenho.

Na rotina de entregas, é comum que o profissional precise registrar manualmente informações como valor recebido, distância percorrida, plataforma utilizada, gorjeta, taxa de espera e observações. A API centraliza esses dados e fornece informações úteis para análise, como:

- total ganho no período;
- quantidade de corridas realizadas;
- quilômetros rodados;
- média de ganho por quilômetro;
- histórico paginado de corridas;
- filtros por plataforma, período e observações.

---

## Repositórios

Frontend da aplicação:

```txt
https://github.com/HugoValuar03/MotoboyAssistant-front
```

Backend da aplicação:

```txt
https://github.com/HugoValuar03/MotoboyAssistant
```

---

## Tecnologias utilizadas

* Java
* Spring Boot
* Spring Web
* Spring Data JPA
* Bean Validation
* Maven
* Banco de dados relacional
* Swagger / OpenAPI

---

## Funcionalidades da API

* Cadastro de corridas
* Listagem paginada de corridas
* Busca de corrida por ID
* Atualização de corrida
* Exclusão de corrida
* Filtro por plataforma
* Filtro por observação
* Filtro por período
* Cálculo de resumo financeiro
* Listagem de plataformas disponíveis
* Documentação interativa com Swagger

---

## Documentação da API

A API possui documentação interativa gerada com Swagger/OpenAPI.

Com o backend em execução, acesse:

```txt
http://localhost:8080/swagger-ui/index.html
```

A especificação OpenAPI em JSON fica disponível em:

```txt
http://localhost:8080/v3/api-docs
```

---

## Principais endpoints

### Corridas

| Método   | Endpoint             | Descrição                              |
| -------- | -------------------- | -------------------------------------- |
| `GET`    | `/api/corridas`      | Lista corridas com filtros e paginação |
| `GET`    | `/api/corridas/{id}` | Busca uma corrida por ID               |
| `POST`   | `/api/corridas`      | Cadastra uma nova corrida              |
| `PUT`    | `/api/corridas/{id}` | Atualiza uma corrida existente         |
| `DELETE` | `/api/corridas/{id}` | Remove uma corrida                     |

### Resumo

| Método | Endpoint               | Descrição                              |
| ------ | ---------------------- | -------------------------------------- |
| `GET`  | `/api/corridas/resumo` | Retorna o resumo financeiro do período |

### Plataformas

| Método | Endpoint           | Descrição                        |
| ------ | ------------------ | -------------------------------- |
| `GET`  | `/api/platforms` | Lista as plataformas disponíveis |

---

## Exemplos de uso

### Criar uma corrida

```http
POST /api/corridas
Content-Type: application/json
```

```json
{
  "plataformaId": 1,
  "distancia": 12.16,
  "valorTotal": 18.24,
  "taxaEspera": 0.60,
  "gorjeta": 0.00,
  "dataHora": "2026-07-03T22:12:00",
  "observacoes": "Entrega finalizada sem intercorrências"
}
```

### Resposta esperada

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "plataforma": "IFOOD",
  "distancia": 12.16,
  "valorTotal": 18.24,
  "valorPorKm": 1.50,
  "taxaEspera": 0.60,
  "gorjeta": 0.00,
  "dataHora": "2026-07-03T22:12:00",
  "observacoes": "Entrega finalizada sem intercorrências"
}
```

---

## Listagem com paginação e filtros

Exemplo de requisição:

```http
GET /api/corridas?page=0&size=10&plataformaId=1&observacao=cliente&dataInicio=2026-07-01&dataFim=2026-07-31
```

Exemplo de resposta:

```json
{
  "content": [
    {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "plataforma": "IFOOD",
      "distancia": 12.16,
      "valorTotal": 18.24,
      "valorPorKm": 1.50,
      "taxaEspera": 0.60,
      "gorjeta": 0.00,
      "dataHora": "2026-07-03T22:12:00",
      "observacoes": "Entrega finalizada sem intercorrências"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 32,
  "totalPages": 4,
  "last": false,
  "first": true
}
```

---

## Resumo financeiro

Exemplo de requisição:

```http
GET /api/corridas/resumo?dataInicio=2026-07-01&dataFim=2026-07-31
```

Exemplo de resposta:

```json
{
  "totalPeriodo": 212.73,
  "quantidadeCorridas": 32,
  "kmRodados": 135.0,
  "mediaPorKm": 1.58
}
```

---

## Regras de negócio

* A distância da corrida deve ser maior que zero.
* O valor total da corrida deve ser maior que zero.
* A data e hora da corrida são obrigatórias.
* A plataforma da corrida é obrigatória.
* A gorjeta não pode ser negativa.
* A taxa de espera não pode ser negativa.
* O valor por quilômetro é calculado automaticamente pela API:

```txt
valorPorKm = valorTotal / distancia
```

---

## Pré-requisitos

Antes de executar o projeto, é necessário ter instalado:

* Java 17 ou superior
* Maven
* Banco de dados relacional
* Git

---

## Como executar o projeto

### 1. Clonar o repositório

```bash
git clone https://github.com/HugoValuar03/MotoboyAssistant
```

```bash
cd MotoboyAssistant
```

---

### 2. Configurar o banco de dados

Configure o arquivo:

```txt
src/main/resources/application.yml
```

Exemplo:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/MotoboyAssistant
    username: postgres
    password: sua_senha
    driver-class-name: org.postgresql.Driver

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
    database-platform: org.hibernate.dialect.PostgreSQLDialect
```

---

### 3. Executar a aplicação

```bash
mvn spring-boot:run
```

A API ficará disponível em:

```txt
http://localhost:8080
```

---

## Rodando os testes

Para executar os testes automatizados:

```bash
mvn test
```

---

## Estrutura do projeto

Exemplo de organização esperada:

```txt
src
└── main
    ├── java
    │   └── br
    │       └── com
    │            └── valu
    │                 └── motoboyassistant
    │                       ├── controller
    │                       ├── service
    │                       ├── repository
    │                       ├── domain
    │                       ├── dto
    │                       ├── exception
    │                       └── config
    └── resources
        └── application.yml
```

### Camadas principais

| Camada       | Responsabilidade                              |
| ------------ | --------------------------------------------- |
| `controller` | Receber requisições HTTP e retornar respostas |
| `service`    | Concentrar regras de negócio                  |
| `repository` | Acessar o banco de dados                      |
| `domain`      | Representar as entidades do domínio           |
| `dto`        | Transferir dados entre API e cliente          |
| `exception`  | Tratar erros da aplicação                     |
| `config`     | Configurações gerais da aplicação             |

---

## Integração com o frontend

O frontend Angular consome esta API por padrão no endereço:

```txt
http://localhost:8080/api
```

No frontend, a URL da API deve ser configurada no arquivo de environment:

```ts
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api'
};
```

---

## Tratamento de erros

A API deve retornar respostas padronizadas para erros de validação e erros de negócio.

Exemplo de erro de validação:

```json
{
  "status": 400,
  "message": "Erro de validação",
  "errors": [
    {
      "field": "distancia",
      "message": "A distância deve ser maior que zero"
    },
    {
      "field": "valorTotal",
      "message": "O valor total deve ser maior que zero"
    }
  ]
}
```

Exemplo de recurso não encontrado:

```json
{
  "status": 404,
  "message": "Corrida não encontrada"
}
```

---

## Status da V1

* [x] Cadastro de corridas
* [x] Listagem de corridas
* [x] Edição de corridas
* [x] Exclusão de corridas
* [x] Filtros por plataforma
* [x] Filtros por observação
* [x] Filtros por período
* [x] Paginação
* [x] Resumo financeiro
* [x] Documentação com Swagger/OpenAPI
* [ ] Autenticação de usuários
* [ ] Relatórios avançados

---

## Próximas melhorias

Funcionalidades planejadas para versões futuras:

* Autenticação com JWT
* Deploy online do backend
* Cadastro de usuários
* Multiusuário
* Dashboard com gráficos
* Exportação CSV/PDF
* Metas de ganho
* Comparação entre plataformas
* Relatórios mensais
* CI/CD com GitHub Actions
* Integração com mapas ou cálculo de rota

---

## Licença

Este projeto está sob a licença MIT.

---

## Autor

Desenvolvido por **Hugo**.

LinkedIn:

```txt
https://www.linkedin.com/in/hugovaluar/
```

GitHub:

```txt
https://github.com/HugoValuar03
```