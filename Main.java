import src.dao.AlunoDAO;
import src.dao.LivroDAO;
import src.dao.EmprestimoDAO;
import src.model.Aluno;
import src.model.Livro;
import src.model.Emprestimo;
import src.util.ConnectionFactory;
import src.util.ActivityLogger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

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
                        int idAluno = alunoDAO.inserirAluno(new Aluno(0, nome, matricula, dataNascimento));
                        ActivityLogger.logStudentActivity("CADASTRADO", nome, String.valueOf(idAluno));
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
                        
                        // Buscar aluno atual
                        Aluno alunoAtual = alunoDAO.buscarAlunoPorId(idAtualizar);
                        if (alunoAtual == null) {
                            System.out.println("Aluno não encontrado!");
                            break;
                        }
                        
                        System.out.println("\nDados atuais do aluno:");
                        System.out.println("Nome: " + alunoAtual.getNome());
                        System.out.println("Matrícula: " + alunoAtual.getMatricula());
                        System.out.println("Data de nascimento: " + alunoAtual.getDataNascimento());
                        
                        System.out.println("\nDigite os novos dados (deixe em branco para manter o valor atual):");
                        
                        System.out.print("Novo nome [" + alunoAtual.getNome() + "]: ");
                        String novoNome = scanner.nextLine().trim();
                        if (novoNome.isEmpty()) {
                            novoNome = alunoAtual.getNome();
                        }
                        
                        System.out.print("Nova data de nascimento [" + alunoAtual.getDataNascimento() + "] (yyyy-MM-dd): ");
                        String novaData = scanner.nextLine().trim();
                        if (novaData.isEmpty()) {
                            novaData = alunoAtual.getDataNascimento();
                        }
                        
                        // Preparar mensagens de alteração
                        List<String> changes = new ArrayList<>();
                        if (!novoNome.equals(alunoAtual.getNome())) {
                            changes.add(String.format("Nome alterado de '%s' para '%s'", alunoAtual.getNome(), novoNome));
                        }
                        if (!novaData.equals(alunoAtual.getDataNascimento())) {
                            changes.add(String.format("Data de nascimento alterada de '%s' para '%s'", alunoAtual.getDataNascimento(), novaData));
                        }
                        
                        // Se não houver alterações, informar o usuário
                        if (changes.isEmpty()) {
                            System.out.println("Nenhuma alteração foi feita.");
                            break;
                        }
                        
                        alunoDAO.atualizarAluno(new Aluno(idAtualizar, novoNome, alunoAtual.getMatricula(), novaData));
                        ActivityLogger.logStudentActivity("ATUALIZADO", novoNome, String.valueOf(idAtualizar), 
                            changes.toArray(new String[0]));
                        System.out.println("Aluno atualizado com sucesso!");
                        break;
                    case "4":
                        System.out.print("ID do aluno a excluir: ");
                        int idExcluir = Integer.parseInt(scanner.nextLine());
                        Aluno alunoExcluir = alunoDAO.buscarAlunoPorId(idExcluir);
                        alunoDAO.deletarAluno(idExcluir);
                        ActivityLogger.logStudentActivity("EXCLUÍDO", alunoExcluir.getNome(), String.valueOf(idExcluir));
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
                      int idLivro = livroDAO.inserirLivro(new Livro(0, titulo, autor, ano, estoque));
                      ActivityLogger.logBookActivity("CADASTRADO", titulo, String.valueOf(idLivro));
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
                      
                      // Buscar livro atual
                      Livro livroAtual = livroDAO.buscarLivroPorId(idAtualizar);
                      if (livroAtual == null) {
                          System.out.println("Livro não encontrado!");
                          break;
                      }
                      
                      System.out.println("\nDados atuais do livro:");
                      System.out.println("Título: " + livroAtual.getTitulo());
                      System.out.println("Autor: " + livroAtual.getAutor());
                      System.out.println("Ano de publicação: " + livroAtual.getAnoPublicacao());
                      System.out.println("Quantidade em estoque: " + livroAtual.getQuantidadeEstoque());
                      
                      System.out.println("\nDigite os novos dados (deixe em branco para manter o valor atual):");
                      
                      System.out.print("Novo título [" + livroAtual.getTitulo() + "]: ");
                      String novoTitulo = scanner.nextLine().trim();
                      if (novoTitulo.isEmpty()) {
                          novoTitulo = livroAtual.getTitulo();
                      }
                      
                      System.out.print("Novo autor [" + livroAtual.getAutor() + "]: ");
                      String novoAutor = scanner.nextLine().trim();
                      if (novoAutor.isEmpty()) {
                          novoAutor = livroAtual.getAutor();
                      }
                      
                      System.out.print("Novo ano de publicação [" + livroAtual.getAnoPublicacao() + "]: ");
                      String novoAnoStr = scanner.nextLine().trim();
                      int novoAno = livroAtual.getAnoPublicacao();
                      if (!novoAnoStr.isEmpty()) {
                          novoAno = Integer.parseInt(novoAnoStr);
                      }
                      
                      System.out.print("Nova quantidade em estoque [" + livroAtual.getQuantidadeEstoque() + "]: ");
                      String novaQtdStr = scanner.nextLine().trim();
                      int novaQtd = livroAtual.getQuantidadeEstoque();
                      if (!novaQtdStr.isEmpty()) {
                          novaQtd = Integer.parseInt(novaQtdStr);
                      }
                      
                      // Preparar mensagens de alteração
                      List<String> changes = new ArrayList<>();
                      if (!novoTitulo.equals(livroAtual.getTitulo())) {
                          changes.add(String.format("Título alterado de '%s' para '%s'", livroAtual.getTitulo(), novoTitulo));
                      }
                      if (!novoAutor.equals(livroAtual.getAutor())) {
                          changes.add(String.format("Autor alterado de '%s' para '%s'", livroAtual.getAutor(), novoAutor));
                      }
                      if (novoAno != livroAtual.getAnoPublicacao()) {
                          changes.add(String.format("Ano de publicação alterado de '%d' para '%d'", livroAtual.getAnoPublicacao(), novoAno));
                      }
                      if (novaQtd != livroAtual.getQuantidadeEstoque()) {
                          changes.add(String.format("Quantidade em estoque alterada de '%d' para '%d'", livroAtual.getQuantidadeEstoque(), novaQtd));
                      }
                      
                      // Se não houver alterações, informar o usuário
                      if (changes.isEmpty()) {
                          System.out.println("Nenhuma alteração foi feita.");
                          break;
                      }
                      
                      livroDAO.atualizarLivro(new Livro(idAtualizar, novoTitulo, novoAutor, novoAno, novaQtd));
                      ActivityLogger.logBookActivity("ATUALIZADO", novoTitulo, String.valueOf(idAtualizar), 
                          changes.toArray(new String[0]));
                      System.out.println("Livro atualizado com sucesso!");
                      break;
                  case "4":
                      System.out.print("ID do livro a excluir: ");
                      int idExcluir = Integer.parseInt(scanner.nextLine());
                      Livro livroExcluir = livroDAO.buscarLivroPorId(idExcluir);
                      livroDAO.deletarLivro(idExcluir);
                      ActivityLogger.logBookActivity("EXCLUÍDO", livroExcluir.getTitulo(), String.valueOf(idExcluir));
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
  
  private static void mostrarEmprestimos() throws SQLException {
      var emprestimos = emprestimoDAO.listarEmprestimos();
      if (emprestimos.isEmpty()) {
          System.out.println("Não há empréstimos registrados!");
          return;
      }
      
      System.out.println("\nLista de empréstimos ativos:");
      System.out.println("----------------------------------------");
      for (var e : emprestimos) {
          Aluno aluno = alunoDAO.buscarAlunoPorId(e.getIdAluno());
          Livro livro = livroDAO.buscarLivroPorId(e.getIdLivro());
          System.out.printf("ID: %d | Aluno: %s | Livro: %s%n", 
              e.getId(), aluno.getNome(), livro.getTitulo());
          System.out.printf("Data do empréstimo: %s | Data de devolução: %s%n",
              e.getDataEmprestimo(), e.getDataDevolucao());
          System.out.println("----------------------------------------");
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
        System.out.println("5. Registrar devolução");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");
        String opcao = scanner.nextLine();

        try {
            switch (opcao) {
                case "1":
                    // Mostrar alunos disponíveis
                    System.out.println("\nAlunos disponíveis:");
                    List<Aluno> alunos = alunoDAO.listarAlunos();
                    if (alunos.isEmpty()) {
                        System.out.println("Não há alunos cadastrados!");
                        break;
                    }
                    for (Aluno a : alunos) {
                        System.out.printf("ID: %d | Nome: %s | Matrícula: %s%n", 
                            a.getId(), a.getNome(), a.getMatricula());
                    }
                    
                    System.out.print("\nID do aluno: ");
                    int idAluno = Integer.parseInt(scanner.nextLine());
                    
                    // Verificar se o aluno existe
                    Aluno aluno = alunoDAO.buscarAlunoPorId(idAluno);
                    if (aluno == null) {
                        System.out.println("Erro: Aluno não encontrado!");
                        break;
                    }
                    
                    // Mostrar livros disponíveis
                    System.out.println("\nLivros disponíveis:");
                    List<Livro> livros = livroDAO.listarLivros();
                    if (livros.isEmpty()) {
                        System.out.println("Não há livros cadastrados!");
                        break;
                    }
                    for (Livro l : livros) {
                        System.out.printf("ID: %d | Título: %s | Autor: %s | Estoque: %d%n", 
                            l.getId(), l.getTitulo(), l.getAutor(), l.getQuantidadeEstoque());
                    }
                    
                    System.out.print("\nID do livro: ");
                    int idLivro = Integer.parseInt(scanner.nextLine());
                    
                    // Verificar se o livro existe
                    Livro livro = livroDAO.buscarLivroPorId(idLivro);
                    if (livro == null) {
                        System.out.println("Erro: Livro não encontrado!");
                        break;
                    }
                    
                    try {
                        emprestimoDAO.registrarEmprestimo(idAluno, idLivro);
                        // Obter o empréstimo recém-criado para pegar as datas
                        Emprestimo novoEmprestimo = emprestimoDAO.buscarEmprestimoPorId(
                            emprestimoDAO.getUltimoEmprestimoId());
                        ActivityLogger.logLoanActivity("CADASTRADO", aluno.getNome(), livro.getTitulo(),
                            novoEmprestimo.getDataEmprestimo(), novoEmprestimo.getDataDevolucao());
                        System.out.println("Empréstimo registrado com sucesso!");
                    } catch (SQLException e) {
                        System.out.println("Erro ao registrar empréstimo: " + e.getMessage());
                    }
                    break;
                case "2":
                    mostrarEmprestimos();
                    break;
                case "3":
                    mostrarEmprestimos();
                    System.out.print("\nID do empréstimo a atualizar: ");
                    int idEmprestimo = Integer.parseInt(scanner.nextLine());
                    
                    // Buscar empréstimo atual
                    Emprestimo emprestimoAtual = emprestimoDAO.buscarEmprestimoPorId(idEmprestimo);
                    if (emprestimoAtual == null) {
                        System.out.println("Empréstimo não encontrado!");
                        break;
                    }
                    
                    // Buscar informações do aluno e livro
                    Aluno alunoAtualizado = alunoDAO.buscarAlunoPorId(emprestimoAtual.getIdAluno());
                    Livro livroAtualizado = livroDAO.buscarLivroPorId(emprestimoAtual.getIdLivro());
                    
                    System.out.println("\nDados atuais do empréstimo:");
                    System.out.println("Aluno: " + alunoAtualizado.getNome());
                    System.out.println("Livro: " + livroAtualizado.getTitulo());
                    System.out.println("Data de empréstimo: " + emprestimoAtual.getDataEmprestimo());
                    System.out.println("Data de devolução: " + emprestimoAtual.getDataDevolucao());
                    
                    System.out.println("\nDigite a nova data de devolução (deixe em branco para manter a data atual):");
                    System.out.print("Nova data de devolução [" + emprestimoAtual.getDataDevolucao() + "] (yyyy-MM-dd): ");
                    String novaData = scanner.nextLine().trim();
                    if (novaData.isEmpty()) {
                        novaData = emprestimoAtual.getDataDevolucao();
                    }
                    
                    // Preparar mensagens de alteração
                    List<String> changes = new ArrayList<>();
                    if (!novaData.equals(emprestimoAtual.getDataDevolucao())) {
                        changes.add(String.format("Data de devolução alterada de '%s' para '%s'", 
                            emprestimoAtual.getDataDevolucao(), novaData));
                    }
                    
                    // Se não houver alterações, informar o usuário
                    if (changes.isEmpty()) {
                        System.out.println("Nenhuma alteração foi feita.");
                        break;
                    }
                    
                    emprestimoDAO.atualizarDataDevolucao(idEmprestimo, novaData);
                    ActivityLogger.logLoanActivity("ATUALIZADO", alunoAtualizado.getNome(), livroAtualizado.getTitulo(),
                        emprestimoAtual.getDataEmprestimo(), novaData,
                        changes.toArray(new String[0]));
                    System.out.println("Data de devolução atualizada com sucesso!");
                    break;
                case "4":
                    mostrarEmprestimos();
                    System.out.print("\nID do empréstimo a excluir: ");
                    int idExcluir = Integer.parseInt(scanner.nextLine());
                    var emprestimoExcluir = emprestimoDAO.buscarEmprestimoPorId(idExcluir);
                    if (emprestimoExcluir == null) {
                        System.out.println("Empréstimo não encontrado!");
                        break;
                    }
                    Aluno alunoEmprestimo = alunoDAO.buscarAlunoPorId(emprestimoExcluir.getIdAluno());
                    Livro livroEmprestimo = livroDAO.buscarLivroPorId(emprestimoExcluir.getIdLivro());
                    emprestimoDAO.deletarEmprestimo(idExcluir);
                    ActivityLogger.logLoanActivity("EXCLUÍDO", alunoEmprestimo.getNome(), livroEmprestimo.getTitulo(),
                        emprestimoExcluir.getDataEmprestimo(), emprestimoExcluir.getDataDevolucao());
                    System.out.println("Empréstimo excluído com sucesso!");
                    break;
                case "5":
                    mostrarEmprestimos();
                    System.out.print("\nID do empréstimo a devolver: ");
                    int idDevolver = Integer.parseInt(scanner.nextLine());
                    
                    // Buscar empréstimo
                    Emprestimo emprestimoDevolver = emprestimoDAO.buscarEmprestimoPorId(idDevolver);
                    if (emprestimoDevolver == null) {
                        System.out.println("Empréstimo não encontrado!");
                        break;
                    }
                    
                    // Buscar informações do aluno e livro
                    Aluno alunoDevolver = alunoDAO.buscarAlunoPorId(emprestimoDevolver.getIdAluno());
                    Livro livroDevolver = livroDAO.buscarLivroPorId(emprestimoDevolver.getIdLivro());
                    
                    try {
                        emprestimoDAO.registrarDevolucao(idDevolver);
                        ActivityLogger.logLoanActivity("DEVOLVIDO", alunoDevolver.getNome(), livroDevolver.getTitulo(),
                            emprestimoDevolver.getDataEmprestimo(), emprestimoDevolver.getDataDevolucao());
                        System.out.println("Livro devolvido com sucesso!");
                    } catch (SQLException e) {
                        System.out.println("Erro ao registrar devolução: " + e.getMessage());
                    }
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
