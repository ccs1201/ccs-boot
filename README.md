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

## Próximos Passos
- **Conversão de bodies de Request**: Implementar a conversão de JSON para objetos do tipo esperado nos parâmetros dos métodos dos controladores.
- **Suporte a status codes**: Implementar uma estratégia semelhante a `ResponseEntity` para permitir o retorno de status HTTP juntamente com a resposta.
- **Testes de concorrência**: Validar o comportamento do servidor em cenários de múltiplas requisições simultâneas.

## Melhorias Futuras
- **Eliminar a necessidade de `EndpointController`**: Procurar uma forma de utilizar o Weld para fornecer os controladores anotados diretamente, sem a necessidade de implementar a interface `EndpointController`.
- **Cache de métodos**: Implementar um cache para armazenar os métodos dos controladores, eliminando a necessidade de fazer uma busca com reflection a cada requisição.
- **Ajustar o content-type**: Melhorar o ajuste do header `Content-Type` nas respostas.

## Como executar
1. Clone o repositório.
2. Execute o servidor utilizando os comandos padrão de compilação e execução em Java.
3. O servidor estará disponível no contexto `/`, pronto para receber requisições HTTP.

## Dependências
- **Java 11+**
- **Weld (Jboss CDI)**
- **Jackson**
- **SLF4J**

Contribuições e sugestões são bem-vindas!
