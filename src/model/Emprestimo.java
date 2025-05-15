package src.model;

public class Emprestimo {
  private int id;
  private int idAluno;
  private int idLivro;
  private String dataEmprestimo;
  private String dataDevolucao;

  public Emprestimo(int id, int idAluno, int idLivro, String dataEmprestimo, String dataDevolucao) {
      this.id = id;
      this.idAluno = idAluno;
      this.idLivro = idLivro;
      this.dataEmprestimo = dataEmprestimo;
      this.dataDevolucao = dataDevolucao;
  }

  public int getId() { return id; }
  public int getIdAluno() { return idAluno; }
  public int getIdLivro() { return idLivro; }
  public String getDataEmprestimo() { return dataEmprestimo; }
  public String getDataDevolucao() { return dataDevolucao; }

  public void setId(int id) { this.id = id; }
  public void setIdAluno(int idAluno) { this.idAluno = idAluno; }
  public void setIdLivro(int idLivro) { this.idLivro = idLivro; }
  public void setDataEmprestimo(String dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }
  public void setDataDevolucao(String dataDevolucao) { this.dataDevolucao = dataDevolucao; }
}
