# AI Budgeting

Assistente financeiro pessoal com voz, construído com **Spring Boot 4** e **Spring AI**. O sistema aceita áudios descrevendo gastos, transcreve com Whisper, interpreta com GPT-4o-mini via function calling, persiste as transações no banco e devolve uma resposta em áudio (TTS).

---

## O que o projeto faz

O usuário envia um arquivo de áudio descrevendo um gasto — por exemplo, *"gastei cinquenta reais no mercado"*. A aplicação:

1. Transcreve o áudio com o modelo **Whisper** (OpenAI)
2. Envia o texto ao **GPT-4o-mini** com um system prompt que o instrui a agir como assistente financeiro
3. O modelo identifica a intenção e chama as **tools** disponíveis:
   - `persistTransaction` — salva a transação com descrição, valor e categoria
   - `listTransactionsByCategory` — recupera transações por categoria
4. Converte a resposta do modelo em áudio via **TTS** (voz `nova`, modelo `gpt-4o-mini-tts`)
5. Retorna o áudio `.mp3` para o cliente

Também há endpoints REST convencionais para criar e listar transações diretamente via JSON.

---

## Como executar

### Pré-requisitos

- Java 25
- Docker e Docker Compose
- Chave de API da OpenAI

### Passos

```bash
# 1. Clone o repositório
git clone https://github.com/ThalesF93/Ai_budgeting.git
cd Ai_budgeting

# 2. Defina a chave da OpenAI como variável de ambiente
export OPENAI_API_KEY=sk-...

# 3. Suba o banco (o Spring Boot sobe o Compose automaticamente via spring-boot-docker-compose)
# Ou suba manualmente:
docker compose up -d

# 4. Execute a aplicação
./gradlew bootRun
```

A aplicação estará disponível em `http://localhost:8080`.

---

## Melhoria implementada: ChatClient configurado em classe separada

O Spring AI disponibiliza um `ChatClient.Builder` que poderia ser injetado diretamente no construtor do controller, mas isso tornaria a controller responsável por montar o cliente — misturando configuração de infraestrutura com lógica de entrada HTTP.

A solução adotada foi isolar toda a configuração do `ChatClient` em `ChatClientConfig`, uma classe `@Component` dedicada que expõe o bean já configurado com:

- Modelo e temperatura definidos via `OpenAiChatOptions`
- System prompt carregado do arquivo `prompts/system-message.st` via `@Value`
- Tools (`PersistTransactionUseCase`, `ListTransactionsByCategoryUseCase`) registradas como function calling

O controller recebe apenas o `ChatClient` pronto via `@RequiredArgsConstructor`, mantendo o construtor limpo e as responsabilidades bem separadas.

---

## Tecnologias utilizadas

| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 25 | Linguagem principal |
| Spring Boot | 4.0.6 | Framework base |
| Spring AI | 2.0.0-M4 | Integração com modelos OpenAI |
| OpenAI Whisper | whisper-1 | Transcrição de áudio |
| OpenAI GPT-4o-mini | — | Chat + function calling |
| OpenAI TTS | gpt-4o-mini-tts | Síntese de voz |
| Spring Data JPA | — | Persistência |
| PostgreSQL | 16 | Banco de dados |
| Lombok | 1.18.42 | Redução de boilerplate |
| Docker Compose | — | Ambiente local de banco |

---

## Como testar o fluxo principal

### Via Postman

**1. Enviar áudio e receber resposta em voz**

```
POST /transactions/ai
Content-Type: multipart/form-data

Body (form-data):
  file  →  [selecione um arquivo .mp3 ou .wav]
```

A resposta será um arquivo `audio.mp3` com o assistente confirmando o registro da transação.

**2. Criar transação manualmente**

```
POST /transactions
Content-Type: application/json

{
  "description": "Compra no mercado",
  "amount": 15000,
  "category": "GROCERIES"
}
```

> Valores monetários são representados em centavos (ex: `15000` = R$ 150,00).

**3. Listar transações por categoria**

```
GET /transactions/GROCERIES
GET /transactions/PHARMA
GET /transactions/AUTO
```

### Áudio de teste

Você pode gravar qualquer frase como:  
*"gastei trinta reais na farmácia"*  
*"paguei duzentos reais no mecânico"*

---

## O que aprendi durante o desafio

- **Spring AI function calling**: como anotar serviços com `@Tool` e `@ToolParam` para que o modelo decida automaticamente qual ferramenta chamar com base na intenção do usuário — sem lógica condicional manual.

- **Pipeline voz → texto → IA → voz**: integrar três modelos distintos (Whisper, GPT-4o-mini, TTS) em uma única requisição HTTP, cada um com responsabilidade clara.

- **Separação de configuração de infraestrutura**: a importância de manter a construção de beans complexos (como o `ChatClient`) fora das classes de entrada, usando `@Component` de configuração dedicado.

- **spring-boot-docker-compose**: como o Spring Boot 3+ pode gerenciar o ciclo de vida do Docker Compose automaticamente, simplificando o setup local sem scripts externos.

- **Prompt engineering aplicado**: escrever um system prompt objetivo que instrui o modelo a extrair dados financeiros em português e usar as tools corretas, evitando respostas livres onde não são necessárias.
