-- Criação do banco de dados
CREATE DATABASE IF NOT EXISTS library;
USE library;

-- Criação da tabela de alunos
CREATE TABLE IF NOT EXISTS alunos (
    id_aluno INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    matricula VARCHAR(20) NOT NULL UNIQUE,
    data_nascimento DATE NOT NULL
);

-- Criação da tabela de livros
CREATE TABLE IF NOT EXISTS livros (
    id_livro INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    ano_publicacao INT NOT NULL,
    quantidade_estoque INT NOT NULL DEFAULT 0
);

-- Criação da tabela de empréstimos
CREATE TABLE IF NOT EXISTS emprestimos (
    id_emprestimo INT PRIMARY KEY AUTO_INCREMENT,
    id_aluno INT NOT NULL,
    id_livro INT NOT NULL,
    data_emprestimo DATE NOT NULL,
    data_devolucao DATE NOT NULL,
    FOREIGN KEY (id_aluno) REFERENCES alunos(id_aluno) ON DELETE CASCADE,
    FOREIGN KEY (id_livro) REFERENCES livros(id_livro) ON DELETE CASCADE
);