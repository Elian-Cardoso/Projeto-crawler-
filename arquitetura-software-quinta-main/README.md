# Crawler de Preços de Produtos

Projeto desenvolvido para a disciplina de Arquitetura de Software.

## O que o sistema faz

O sistema percorre links de produtos em lojas online e salva o histórico de preços encontrados, permitindo acompanhar o preço de um mesmo produto em mais de uma loja.

## Produtos cadastrados

- PlayStation 5 — Amazon, Kabum, Magalu
- Xbox Series X 1TB — Amazon, Casas Bahia, Mercado Livre
- Notebook Dell Inspiron 15 — Amazon, Kabum, Magalu
- iPhone 15 128GB — Amazon, Casas Bahia, Mercado Livre
- Smart TV Samsung 55" 4K QLED — Amazon, Kabum, Casas Bahia

## Como o crawler foi implementado

1. Cada produto possui uma lista de links, um para cada loja
2. Ao executar, o crawler percorre todos os produtos cadastrados
3. Para cada produto, acessa cada link usando a biblioteca **Jsoup** para fazer o scraping da página
4. Extrai o preço atual da página de cada loja
5. Compara todos os preços encontrados e identifica o menor
6. Salva o menor preço no histórico junto com o nome da loja e a data

## Tecnologias utilizadas

- Java 17
- Hibernate + JPA (persistência)
- SQLite (banco de dados)
- Jsoup (web scraping)
- Maven (gerenciamento de dependências)

## Como executar

Abrir o projeto e roda a classe `Main.java` como Java Application.
