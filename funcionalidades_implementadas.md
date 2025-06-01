# N688  Ambiente de dado - Grupo 37

## Alunos

Rafael Angelo Pinheiro do Vale - 2418273

Paulo Henrique de Sousa Gomes - 2425201

Lucas Gabriel Vasconcelos - 2415656

Isaias do Amaral Sousa - 2416767

Amanda Costa Silva - 2415606

Pedro Yuri Rodrigues Nunes - 2425031


# Sistema de Biblioteca Escolar

## Funcionalidades Implementadas

### 1. Gerenciamento de Alunos
- Cadastro de alunos com nome, matrícula e data de nascimento  
- Listagem de todos os alunos cadastrados  
- Atualização de dados do aluno (nome e data de nascimento)  
- Exclusão de alunos  
- Validação de dados durante operações  
- Log de atividades para todas as operações  

### 2. Gerenciamento de Livros
- Cadastro de livros com título, autor, ano de publicação e quantidade em estoque  
- Listagem de todos os livros cadastrados  
- Atualização de dados do livro (título, autor, ano e estoque)  
- Exclusão de livros  
- Controle automático de estoque  
- Log de atividades para todas as operações  

### 3. Gerenciamento de Empréstimos
- Registro de empréstimos com data de empréstimo e devolução  
- Listagem de todos os empréstimos ativos  
- Atualização de data de devolução  
- Exclusão de empréstimos  
- Validação de disponibilidade de livros  
- Atualização automática do estoque ao emprestar/devolver  
- Log detalhado de todas as operações  

### 4. Sistema de Logs
- Registro de todas as operações realizadas no sistema  
- Timestamp para cada operação  
- Detalhamento das alterações realizadas  
- Logs específicos para:
  - Operações com alunos  
  - Operações com livros  
  - Operações com empréstimos  
- Armazenamento em arquivo de texto  

### 5. Validações e Controles
- Verificação de existência de registros  
- Validação de dados de entrada  
- Controle de estoque de livros  
- Prevenção de empréstimos sem estoque  
- Tratamento de erros e exceções  

### 6. Interface do Usuário
- Menu interativo via console  
- Opções claras e organizadas  
- Feedback imediato das operações  
- Exibição de dados atuais antes de atualizações  
- Possibilidade de manter valores atuais em atualizações  

### 7. Persistência de Dados
- Armazenamento em banco de dados MySQL  
- Tabelas relacionadas com chaves estrangeiras  
- Integridade referencial mantida  
- Transações seguras para operações críticas  

### 8. Recursos Adicionais
- Data de devolução automática (7 dias após empréstimo)  
- Identificação única para alunos e livros  
- Histórico completo de operações  
- Interface em português  
- Mensagens de erro claras e informativas  
