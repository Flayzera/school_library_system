import src.dao.AlunoDAO;
import src.dao.LivroDAO;
import src.dao.EmprestimoDAO;
import src.model.Aluno;
import src.model.Livro;
import src.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Connection conexao;

    private static AlunoDAO alunoDAO;
    private static LivroDAO livroDAO;
    private static EmprestimoDAO emprestimoDAO;

    public static void main(String[] args) {
        try {
            conexao = ConnectionFactory.getConnection();
            alunoDAO = new AlunoDAO(conexao);
            livroDAO = new LivroDAO(conexao);
            emprestimoDAO = new EmprestimoDAO(conexao);

            boolean executar = true;
            while (executar) {
                System.out.println("=== Sistema Biblioteca Escolar ===");
                System.out.println("1. Gerenciar Alunos");
                System.out.println("2. Gerenciar Livros");
                System.out.println("3. Gerenciar Empréstimos");
                System.out.println("0. Sair");
                System.out.print("Escolha uma opção: ");
                String opcao = scanner.nextLine();

                switch (opcao) {
                    case "1": gerenciarAlunos(); break;
                    case "2": gerenciarLivros(); break;
                    case "3": gerenciarEmprestimos(); break;
                    case "0": executar = false; break;
                    default: System.out.println("Opção inválida."); break;
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro de conexão: " + e.getMessage());
        } finally {
            try {
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            scanner.close();
        }
    }

    private static void gerenciarAlunos() {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n-- Gerenciar Alunos --");
            System.out.println("1. Cadastrar aluno");
            System.out.println("2. Listar alunos");
            System.out.println("3. Atualizar aluno");
            System.out.println("4. Excluir aluno");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            try {
                switch (opcao) {
                    case "1":
                        System.out.print("Nome: ");
                        String nome = scanner.nextLine();
                        System.out.print("Matrícula: ");
                        String matricula = scanner.nextLine();
                        System.out.print("Data de nascimento (yyyy-MM-dd): ");
                        String dataNascimento = scanner.nextLine();
                        alunoDAO.inserirAluno(new Aluno(0, nome, matricula, dataNascimento));
                        System.out.println("Aluno cadastrado com sucesso!");
                        break;
                    case "2":
                        List<Aluno> alunos = alunoDAO.listarAlunos();
                        System.out.println("Lista de alunos:");
                        for (Aluno a : alunos) {
                            System.out.println(a.getId() + " - " + a.getNome() + " | " + a.getMatricula() + " | " + a.getDataNascimento());
                        }
                        break;
                    case "3":
                        System.out.print("ID do aluno a atualizar: ");
                        int idAtualizar = Integer.parseInt(scanner.nextLine());
                        System.out.print("Novo nome: ");
                        String novoNome = scanner.nextLine();
                        System.out.print("Nova matrícula: ");
                        String novaMatricula = scanner.nextLine();
                        System.out.print("Nova data de nascimento (yyyy-MM-dd): ");
                        String novaData = scanner.nextLine();
                        alunoDAO.atualizarAluno(new Aluno(idAtualizar, novoNome, novaMatricula, novaData));
                        System.out.println("Aluno atualizado com sucesso!");
                        break;
                    case "4":
                        System.out.print("ID do aluno a excluir: ");
                        int idExcluir = Integer.parseInt(scanner.nextLine());
                        alunoDAO.deletarAluno(idExcluir);
                        System.out.println("Aluno excluído com sucesso!");
                        break;
                    case "0":
                        voltar = true;
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.err.println("Erro: " + e.getMessage());
            }
        }
    }

    private static void gerenciarLivros() {
      boolean voltar = false;
      while (!voltar) {
          System.out.println("\n-- Gerenciar Livros --");
          System.out.println("1. Cadastrar livro");
          System.out.println("2. Listar livros");
          System.out.println("3. Atualizar livro");
          System.out.println("4. Excluir livro");
          System.out.println("0. Voltar");
          System.out.print("Escolha uma opção: ");
          String opcao = scanner.nextLine();
  
          try {
              switch (opcao) {
                  case "1":
                      System.out.print("Título: ");
                      String titulo = scanner.nextLine();
                      System.out.print("Autor: ");
                      String autor = scanner.nextLine();
                      System.out.print("Ano de publicação: ");
                      int ano = Integer.parseInt(scanner.nextLine());
                      System.out.print("Quantidade em estoque: ");
                      int estoque = Integer.parseInt(scanner.nextLine());
                      livroDAO.inserirLivro(new Livro(0, titulo, autor, ano, estoque));
                      System.out.println("Livro cadastrado com sucesso!");
                      break;
                  case "2":
                      List<Livro> livros = livroDAO.listarLivros();
                      System.out.println("Lista de livros:");
                      for (Livro l : livros) {
                          System.out.println(l.getId() + " - " + l.getTitulo() + " | " + l.getAutor() + " | " + l.getAnoPublicacao() + " | Estoque: " + l.getQuantidadeEstoque());
                      }
                      break;
                  case "3":
                      System.out.print("ID do livro a atualizar: ");
                      int idAtualizar = Integer.parseInt(scanner.nextLine());
                      System.out.print("Novo título: ");
                      String novoTitulo = scanner.nextLine();
                      System.out.print("Novo autor: ");
                      String novoAutor = scanner.nextLine();
                      System.out.print("Novo ano de publicação: ");
                      int novoAno = Integer.parseInt(scanner.nextLine());
                      System.out.print("Nova quantidade em estoque: ");
                      int novaQtd = Integer.parseInt(scanner.nextLine());
                      livroDAO.atualizarLivro(new Livro(idAtualizar, novoTitulo, novoAutor, novoAno, novaQtd));
                      System.out.println("Livro atualizado com sucesso!");
                      break;
                  case "4":
                      System.out.print("ID do livro a excluir: ");
                      int idExcluir = Integer.parseInt(scanner.nextLine());
                      livroDAO.deletarLivro(idExcluir);
                      System.out.println("Livro excluído com sucesso!");
                      break;
                  case "0":
                      voltar = true;
                      break;
                  default:
                      System.out.println("Opção inválida.");
              }
          } catch (Exception e) {
              System.err.println("Erro: " + e.getMessage());
          }
      }
  }
  
  private static void gerenciarEmprestimos() {
    boolean voltar = false;
    while (!voltar) {
        System.out.println("\n-- Gerenciar Empréstimos --");
        System.out.println("1. Registrar empréstimo");
        System.out.println("2. Listar empréstimos");
        System.out.println("3. Atualizar data de devolução");
        System.out.println("4. Excluir empréstimo");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");
        String opcao = scanner.nextLine();

        try {
            switch (opcao) {
                case "1":
                    System.out.print("ID do aluno: ");
                    int idAluno = Integer.parseInt(scanner.nextLine());
                    System.out.print("ID do livro: ");
                    int idLivro = Integer.parseInt(scanner.nextLine());
                    emprestimoDAO.registrarEmprestimo(idAluno, idLivro);
                    System.out.println("Empréstimo registrado com sucesso!");
                    break;
                case "2":
                    var emprestimos = emprestimoDAO.listarEmprestimos();
                    System.out.println("Lista de empréstimos:");
                    for (var e : emprestimos) {
                        System.out.printf("ID: %d | Aluno ID: %d | Livro ID: %d | Emprestimo: %s | Devolução: %s%n",
                            e.getId(), e.getIdAluno(), e.getIdLivro(), e.getDataEmprestimo(), e.getDataDevolucao());
                    }
                    break;
                case "3":
                    System.out.print("ID do empréstimo a atualizar: ");
                    int idEmprestimo = Integer.parseInt(scanner.nextLine());
                    System.out.print("Nova data de devolução (yyyy-MM-dd): ");
                    String novaData = scanner.nextLine();
                    emprestimoDAO.atualizarDataDevolucao(idEmprestimo, novaData);
                    System.out.println("Data de devolução atualizada com sucesso!");
                    break;
                case "4":
                    System.out.print("ID do empréstimo a excluir: ");
                    int idExcluir = Integer.parseInt(scanner.nextLine());
                    emprestimoDAO.deletarEmprestimo(idExcluir);
                    System.out.println("Empréstimo excluído com sucesso!");
                    break;
                case "0":
                    voltar = true;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
  }
}
