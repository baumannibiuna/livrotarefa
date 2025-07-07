# LivroTarefa API

![Java](https://img.shields.io/badge/Java-11-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-2.4.3-brightgreen.svg)
![Maven](https://img.shields.io/badge/Maven-3.6+-red.svg)
![License](https://img.shields.io/badge/License-MIT-blue.svg)

API REST para um sistema de gerenciamento de tarefas (To-Do List), onde usuários podem ser cadastrados para criar e gerenciar suas próprias tarefas. O projeto foi desenvolvido com Spring Boot e demonstra a implementação de um CRUD completo, relacionamentos entre entidades e boas práticas de desenvolvimento de APIs.

## 📜 Sumário

* [Status do Projeto](#-status-do-projeto)
* [Funcionalidades](#-funcionalidades)
* [Tecnologias Utilizadas](#-tecnologias-utilizadas)
* [Estrutura do Projeto](#-estrutura-do-projeto)
* [Como Executar](#-como-executar)
* [Documentação da API (Swagger)](#-documentação-da-api-swagger)
* [Acesso ao Banco de Dados (H2)](#-acesso-ao-banco-de-dados-h2)
* [Como Rodar os Testes](#-como-rodar-os-testes)
* [Autor](#-autor)

## 🎯 Status do Projeto

Projeto concluído e funcional, servindo como um portfólio para demonstrar habilidades em desenvolvimento backend com Java e Spring Boot.

## ✨ Funcionalidades

* **Gestão de Usuários**:
    * [x] Criar, Listar, Atualizar e Excluir Usuários.
* **Gestão de Tarefas**:
    * [x] Criar, Listar, Atualizar e Excluir Tarefas.
    * [x] Associar uma tarefa a um usuário específico.
    * [x] Listar todas as tarefas de um determinado usuário.
* **Consultas Avançadas**:
    * [x] Utilização de QueryDSL para buscas dinâmicas e tipadas no banco de dados.

## 🚀 Tecnologias Utilizadas

Este projeto foi construído utilizando um conjunto de tecnologias modernas e robustas do ecossistema Java:

| Tecnologia         | Descrição                                                              |
| ------------------ | ---------------------------------------------------------------------- |
| **Java 11** | Versão LTS do Java, amplamente utilizada no mercado.                   |
| **Spring Boot** | Framework principal para criação da aplicação e da API REST.           |
| **Spring Data JPA**| Para persistência de dados de forma simplificada.                      |
| **Hibernate** | Implementação da especificação JPA para mapeamento objeto-relacional.  |
| **QueryDSL** | Para a criação de consultas SQL type-safe (seguras em tempo de compilação). |
| **H2 Database** | Banco de dados em memória para ambiente de desenvolvimento e testes.     |
| **Maven** | Ferramenta de gerenciamento de dependências e build do projeto.        |
| **Lombok** | Reduz o código boilerplate (getters, setters, construtores, etc).      |
| **ModelMapper** | Para mapeamento automático entre objetos DTO e Entidades.              |
| **SpringFox** | Geração automática de documentação da API com a UI do Swagger.         |
| **REST Assured** | Biblioteca para facilitar a escrita de testes para a API REST.         |

## 📂 Estrutura do Projeto

A estrutura de pacotes foi organizada seguindo as melhores práticas de arquitetura em camadas para facilitar a manutenção e escalabilidade:

com.baumannibiuna.livrotarefa
├── controller/  # Camada de Apresentação (Endpoints REST)
├── dto/         # Data Transfer Objects (Objetos para transferência de dados)
├── model/       # Camada de Domínio (Entidades JPA)
├── repository/  # Camada de Acesso a Dados (Interfaces do Spring Data)
├── service/     # Camada de Negócios (Lógica da aplicação)
└── exception/   # Tratamento de exceções personalizadas


## ▶️ Como Executar

Para executar o projeto localmente, siga os passos abaixo. Você precisará ter o **JDK 11** e o **Maven** instalados.

```bash
# 1. Clone o repositório
$ git clone [https://github.com/baumannibiuna/livrotarefa.git](https://github.com/baumannibiuna/livrotarefa.git)

# 2. Navegue até o diretório do projeto
$ cd livrotarefa

# 3. Execute o projeto com o Maven
$ mvn spring-boot:run

Após a execução, a API estará disponível em http://localhost:8080.

📖 Documentação da API (Swagger)
A documentação de todos os endpoints da API foi gerada automaticamente com o SpringFox e pode ser acessada através da interface do Swagger.

URL da Documentação: http://localhost:8080/swagger-ui/
Lá você poderá visualizar e testar todos os endpoints de forma interativa.

Exemplos de Requisições (cURL)

Criar uma nova tarefa 
Bash

curl -X POST "http://localhost:8080/api/livrotarefa/tasks" \
-H "Content-Type: application/json" \
-d '{"name": "Tarefa1", 
     "description": "Iniciando estudos Java com Springboot"}
}'

🗄️ Acesso ao Banco de Dados (H2)
O projeto utiliza o banco de dados em memória H2, que pode ser acessado através de um console web para visualização e manipulação dos dados.

URL do Console H2: http://localhost:8080/api/h2-console
JDBC URL: jdbc:h2:mem:testdb
Username: root
Password: root

✅ Como Rodar os Testes
Para garantir a qualidade e o funcionamento correto da API, execute os testes automatizados com o seguinte comando:

Bash

$ mvn test
👨‍💻 Autor
Desenvolvido por [Wellington Baumann].

