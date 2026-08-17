# PayShap Lite - Financial Transaction Engine 🚀

![Java](https://img.shields.io/badge/Java-21-blue?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen?logo=spring)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?logo=postgresql)

A lightweight, high-integrity financial transaction engine inspired by South Africa's PayShap system. This backend API is designed to process secure transfers between user accounts utilizing a strict **Double-Entry Ledger** architecture and **Idempotency Keys** to prevent duplicate transactions.

---

## 🇬🇧 System Architecture & Features

This project was built to demonstrate enterprise-level backend patterns used by modern payment gateways and fintech applications.

*   **Double-Entry Ledger:** Balances are not stored as a single mutable column. Instead, every transfer generates a strict `CREDIT` and `DEBIT` ledger entry. Balances are calculated dynamically by summing historical entries, ensuring absolute data integrity.
*   **Idempotency Handling:** The `TransactionService` enforces unique idempotency keys per transfer request. If a client retries a failed network request, the system prevents duplicate charges.
*   **ACID Compliant Transactions:** Wrapped in Spring's `@Transactional`, ensuring that if a debit succeeds but a credit fails, the entire database operation rolls back safely.

> **⚠️ Testing Utilities Disclaimer:** The Vanilla JS frontend and the `DatabaseSeeder` configuration class are not part of the core engineering showcase. They are strictly temporary testing utilities implemented to allow visual demonstration of the REST API without requiring Postman.

## 🇧🇷 Arquitetura do Sistema e Recursos

Este projeto foi construído para demonstrar padrões de backend de nível corporativo usados por gateways de pagamento e aplicativos fintech modernos.

*   **Livro-Razão de Dupla Entrada (Double-Entry Ledger):** Os saldos não são armazenados em uma única coluna mutável. Em vez disso, cada transferência gera uma entrada estrita de `CRÉDITO` e `DÉBITO`. Os saldos são calculados dinamicamente somando as entradas históricas, garantindo integridade absoluta dos dados.
*   **Tratamento de Idempotência:** O `TransactionService` impõe chaves de idempotência exclusivas por solicitação de transferência. Se um cliente tentar novamente uma solicitação de rede que falhou, o sistema evita cobranças duplicadas.
*   **Transações em Conformidade com ACID:** Envolvido no `@Transactional` do Spring, garantindo que se um débito for bem-sucedido, mas um crédito falhar, toda a operação do banco de dados seja revertida com segurança.

> **⚠️ Aviso sobre Utilitários de Teste:** O frontend em Vanilla JS e a classe de configuração `DatabaseSeeder` não fazem parte do portfólio principal de engenharia. Eles são estritamente utilitários de teste temporários implementados para permitir a demonstração visual da API REST sem a necessidade do Postman.

---

## 🎥 Visual Demo
* - *

---

## 🛠️ How to Run Locally

1. Clone the repository.
2. Configure your PostgreSQL database in `application.properties`.
3. Run `DemoApplication.java`.
4. The system includes a `DatabaseSeeder` that automatically populates dummy accounts with $10,000 for immediate testing.
5. Open `src/main/resources/static/index.html` in your browser to access the Merchant UI.