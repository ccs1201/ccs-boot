# Servidor HTTP Simples com DI e Roteamento Dinâmico

## Descrição do Projeto
Este projeto implementa um servidor HTTP simples com suporte a injeção de dependência (DI) e roteamento dinâmico, inspirado nas ideias do Spring MVC, mas sem utilizar o Spring framework. O servidor utiliza o HttpServer do Java, Weld para injeção de dependência, Jackson para manipulação de JSON e SLF4J para logging.

### Funcionalidades Principais
- **Requisições HTTP suportadas**: GET, POST, PUT, DELETE
- **Formato de requisição e resposta**: JSON/String
- **Roteamento dinâmico** baseado na URI e no método HTTP
- **Injeção de dependências** via Jboss Weld
- **Anotações** para mapear controladores e métodos HTTP:
  - `@Endpoint`: Marca classes de controladores
  - `@Endpoint.XXX`: Associa métodos HTTP a métodos dos controladores

### Tecnologias Utilizadas
- **HttpServer** do Java (`com.sun.net.httpserver.HttpServer`)
- **Generics** e **Reflection**
- **Weld** para injeção de dependência (CDI)
- **Jackson** para serialização/deserialização de JSON
- **SLF4J** para logging

## O que já foi implementado
- **Servidor HTTP** rodando e respondendo no contexto `/`.
- **Suporte completo** aos métodos HTTP: GET, POST, PUT, DELETE.
- **Anotações** para mapear controladores (`@Endpoint`) e métodos (`@Endpoint.XXX`).
- **Injeção de dependência** via Weld funcionando.
- **HandlerResolver** para cachear controladores anotados e fornecer o controlador correto com base na URI.
- **HandlerDispatcher** que orquestra o processo de roteamento, chamando o método correto do controlador com base na URI e no método HTTP.
- **Respostas em JSON**, onde o body da response é convertido para JSON caso o método do controlador retorne um objeto.
- **Conversão de bodies de Request**: Implementada a conversão de JSON para objetos do tipo esperado nos parâmetros dos métodos dos controladores.
- **Suporte a status codes**: Implementada anotação `@EndpointResponseCode` para permitir o retorno de status HTTP juntamente com a resposta.
- **Fixado o header `Content-Type`** para `apliccation/json` nas respostas.
- **CcsBootCustomException** paa caso queira lançar exceções customizadas com um HTTP status especifico.

## Próximos Passos
- **Conversão de bodies de Request**: ~~Implementar a conversão de JSON para objetos do tipo esperado nos parâmetros dos métodos dos controladores.~~
- **Suporte a status codes**: ~~Implementar uma estratégia semelhante a `ResponseEntity` para permitir o retorno de status HTTP juntamente com a resposta.~~
- **Testes de concorrência**: Validar o comportamento do servidor em cenários de múltiplas requisições simultâneas.

## Melhorias Futuras
- **Eliminar a necessidade de `EndpointController`**: ~~Procurar uma forma de utilizar o Weld para fornecer os controladores anotados diretamente, sem a necessidade de implementar a interface `EndpointController`.~~
- **Cache de métodos**: Implementar um cache para armazenar os métodos dos controladores, eliminando a necessidade de fazer uma busca com reflection a cada requisição.
- **Ajustar o content-type**: ~~Melhorar o ajuste do header `Content-Type` nas respostas.~~

## Como executar
1. Clone o repositório.
2. Execute o servidor utilizando os comandos padrão de compilação e execução em Java.
3. O servidor estará disponível no contexto `/` e porta `8080`, pronto para receber requisições HTTP.

## Dependências
- **Java 21+**
- **Weld (Jboss CDI)**
- **Jackson**
- **SLF4J**

### Siga-me no linkedin -> [CCS1201](https://www.linkedin.com/feed/update/urn:li:activity:7242648326699913219/)

Contribuições e sugestões são bem-vindas!


# Simple HTTP Server with DI and Dynamic Routing

## Project Description
This project implements a simple HTTP server with support for dependency injection (DI) and dynamic routing, inspired by the ideas of Spring MVC, but without using the Spring framework. The server uses Java's `HttpServer`, Weld for dependency injection, Jackson for JSON handling, and SLF4J for logging.

### Main Features
- **Supported HTTP methods**: GET, POST, PUT, DELETE
- **Request and response format**: JSON/String
- **Dynamic routing** based on the URI and HTTP method
- **Dependency Injection** via Jboss Weld
- **Annotations** to map controllers and HTTP methods:
  - `@Endpoint`: Marks controller classes
  - `@Endpoint.XXX`: Associates HTTP methods with controller methods

### Technologies Used
- **HttpServer** from Java (`com.sun.net.httpserver.HttpServer`)
- **Generics** and **Reflection**
- **Weld** for Dependency Injection (CDI)
- **Jackson** for JSON serialization/deserialization
- **SLF4J** for logging

## What Has Been Implemented
- **HTTP Server** running and responding at the `/` context.
- **Full support** for HTTP methods: GET, POST, PUT, DELETE.
- **Annotations** to map controllers (`@Endpoint`) and methods (`@Endpoint.XXX`).
- **Dependency Injection** via Weld working properly.
- **HandlerResolver** to cache annotated controllers and provide the correct controller based on the URI.
- **HandlerDispatcher** that orchestrates the routing process, calling the correct controller method based on the URI and HTTP method.
- **JSON Responses**, where the response body is converted to JSON if the controller method returns an object.
- **Request body conversion**: JSON conversion to the expected object type for controller method parameters has been implemented.
- **Support for status codes**: The `@EndpointResponseCode` annotation has been implemented to allow the return of HTTP status codes along with the response.
- **Fixed `Content-Type` header** to `application/json` in responses.
- - **CcsBootCustomException** to throw exceptions with a custom  HTTP status.

## Next Steps
- **Request body conversion**: ~~Implement JSON conversion to the expected object type for controller method parameters.~~
- **Support for status codes**: ~~Implement a strategy similar to `ResponseEntity` to allow the return of HTTP status codes along with the response.~~
- **Concurrency tests**: Validate the server's behavior under scenarios with multiple simultaneous requests.

## Future Improvements
- **Eliminate the need for `EndpointController`**: ~~Find a way to use Weld to directly provide annotated controllers without requiring the implementation of the `EndpointController` interface.~~
- **Method caching**: Implement a cache to store controller methods, eliminating the need to perform a reflection lookup on every request.
- **Adjust content-type**: ~~Improve the handling of the `Content-Type` header in responses.~~


## How to Run
1. Clone the repository.
2. Run the server using standard Java build and execution commands.
3. The server will be available at the `/` context and port `8080`, ready to handle HTTP requests.

## Dependencies
- **Java 21+**
- **Weld (Jboss CDI)**
- **Jackson**
- **SLF4J**

### Follow-me on linkedin -> [CCS1201](https://www.linkedin.com/feed/update/urn:li:activity:7242648326699913219/)

Contributions and suggestions are welcome!

