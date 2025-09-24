# 🔐 Secure Login System (Spring Boot + Thymeleaf + MongoDB Atlas)

Sistema de **Login Seguro** desenvolvido com **Java Spring Boot**, **Thymeleaf** e **MongoDB Atlas**, com suporte a autenticação, autorização e gerenciamento de usuários.  

Este projeto foi implementado de forma modular e escalável, permitindo adaptação para diferentes temas e integração futura em sistemas maiores (como PFC) sem grandes alterações na lógica central.

---

## 🚀 Funcionalidades

- Cadastro de usuários com validação de dados e hash de senhas.
- Login e logout seguro com gerenciamento de sessões.
- Recuperação de senha com envio de **token de redefinição** válido por tempo limitado.
- Controle de acesso a rotas com base em **roles (perfis de usuário)**.
- Autenticação e autorização via **Spring Security**.
- Armazenamento de usuários e sessões no **MongoDB Atlas**.
- Interface construída com **Thymeleaf**, desacoplada da lógica de negócio.
- Estrutura escalável, permitindo personalização de layouts e temas futuros.

---

## 📂 Estrutura do Projeto

```
src/main/java/org/example/auth
 ┣ controller/     # Controladores MVC (AuthController, etc.)
 ┣ service/        # Regras de autenticação e lógica de negócios
 ┣ repository/     # Integração com MongoDB Atlas
 ┣ domain/          # Entidades de usuário, sessão, token
 ┗ config/         # Configurações do Spring Security
```

---

## 🛠 Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot 3**
- **Spring Security**
- **Thymeleaf**
- **MongoDB Atlas**
- **Maven**

---

## ⚙️ Configuração do Ambiente

1. Clone este repositório:
   ```bash
   git clone https://github.com/guuhlima/security-system.git
   cd security-system
   ```

2. Configure as variáveis de ambiente no arquivo `application.properties`:
   ```properties
   spring.data.mongodb.uri=mongodb+srv://<usuario>:<senha>@<cluster>/<database>
   spring.data.mongodb.database=security_db
   server.port=8080
   ```

3. Execute a aplicação:
   ```bash
   mvn spring-boot:run
   ```

4. Acesse no navegador:
   ```
   http://localhost:8080/auth/login
   ```

---

## 🔑 Fluxos de Autenticação

### 📌 Login
- Endpoint: `GET /auth/login`
- Exibe a tela de login.  
- Caso falhe, mostra mensagem de erro.

### 📌 Registro
- Endpoint: `GET /auth/register` (tela)  
- Endpoint: `POST /auth/register` (ação)  
- Valida senhas e cria novo usuário no banco de dados.  

### 📌 Esqueci minha senha
- Endpoint: `GET /auth/forgot`  
- Gera token de redefinição de senha válido por 1 hora.  
- (Simulado: link exibido na tela, mas pode ser enviado por e-mail futuramente).  

### 📌 Redefinição de senha
- Endpoint: `GET /auth/reset?token=...`  
- Endpoint: `POST /auth/reset`  
- Valida token e redefine senha do usuário.  

---

## 📖 Documentação

O sistema inclui:
- Estrutura modular baseada em boas práticas do Spring Boot.
- Integração com **MongoDB Atlas** para persistência segura.
- Código comentado para facilitar manutenção.
- **README** com instruções de configuração e execução.

---

## 📌 Futuras Melhorias

- Integração com serviços de e-mail para envio de tokens de redefinição.  
- Templates configuráveis para **customização visual** do sistema.  
- Suporte multilíngue via **i18n**.  

---

## 📜 Licença

Este projeto é distribuído sob a licença **MIT**.  
Sinta-se à vontade para usar, modificar e contribuir.

---
