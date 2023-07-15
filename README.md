# Projeto DrogaBraia

Este é um projeto Java utilizando o Spring 2.7.13 que implementa uma API para gerenciamento de usuários, produtos, fabricantes e carrinhos de compras.

## Requisitos

- Java 11
- Spring Framework 2.7.13

## Documentação da API

A documentação da API pode ser encontrada no Swagger (/swagger-ui/index.htm).

# Endpoints

| UserController       | ProductController       | ManufacturerController | CartController         |
|----------------------|-------------------------|------------------------|------------------------|
| GET /users/{userId}  | GET /products/{productId} | GET /manufacturers/{id} | GET /carts/{cartId}    |
| PUT /users/{userId}  | PUT /products/{productId} | PUT /manufacturers/{id} | PUT /carts/{cartId}    |
| DELETE /users/{userId} | DELETE /products/{productId} | DELETE /manufacturers/{id} | DELETE /carts/{cartId} |
| GET /users           | GET /products            | GET /manufacturers     | GET /carts             |
| POST /users          | POST /products           | POST /manufacturers    | POST /carts            |

# Relacionamentos - Diagrama

<a href="https://ibb.co/HG01GcQ"><img src="https://i.ibb.co/QYZzYWG/Diagram.png" width=200px alt="Diagram" border="0"></a><br /><a target='_blank' href='https://imgbb.com/'></a><br />
## Nota - Implementaçoes futuras
- [ ] Extender entidade usuário e endereços
- [ ] Implementação módulo de pagamentos
- [ ] Implementação módulo de gestão de pedidos
- [ ] Implementação módulo de gestão de produtos


<h2>Como Executar Localmente</h2>

<p>Para executar o projeto DrogaBraia localmente usando o Spring, siga as etapas abaixo:</p>

<h3>Pré-requisitos</h3>

<ul>
  <li>Java 11 instalado em sua máquina.</li>
  <li>Maven instalado em sua máquina.</li>
  <li>Spring Framework 2.7.13.</li>
</ul>

<h3>Passos</h3>

<ol>
  <li>Clone este repositório para o seu ambiente local:</li>

  <pre><code>git clone https://github.com/seu-usuario/projeto-drogabraia.git</code></pre>

  <li>Acesse o diretório do projeto:</li>

  <pre><code>cd projeto-drogabraia</code></pre>

  <li>Compile o projeto usando o Maven:</li>

  <pre><code>mvn compile</code></pre>

  <li>Execute o projeto usando o comando do Spring:</li>

  <pre><code>mvn spring-boot:run</code></pre>

  <li>O servidor local será iniciado e o projeto DrogaBraia estará acessível em <a href="http://localhost:8080">http://localhost:8080</a>.</li>
</ol>

<p>Pronto! Agora você pode acessar a API do DrogaBraia localmente e utilizar os endpoints conforme documentado no Swagger. Certifique-se de ter o Java 11 e o Spring Framework 2.7.13 instalados corretamente em seu ambiente antes de executar o projeto.</p>


## Licença

Este projeto está licenciado sob a [MIT License](link-da-licenca).
