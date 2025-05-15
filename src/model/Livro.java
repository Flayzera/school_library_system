package src.model;

public class Livro {
  private int id;
  private String titulo;
  private String autor;
  private int anoPublicacao;
  private int quantidadeEstoque;

  public Livro(int id, String titulo, String autor, int anoPublicacao, int quantidadeEstoque) {
      this.id = id;
      this.titulo = titulo;
      this.autor = autor;
      this.anoPublicacao = anoPublicacao;
      this.quantidadeEstoque = quantidadeEstoque;
  }

  public int getId() { return id; }
  public String getTitulo() { return titulo; }
  public String getAutor() { return autor; }
  public int getAnoPublicacao() { return anoPublicacao; }
  public int getQuantidadeEstoque() { return quantidadeEstoque; }

  public void setId(int id) { this.id = id; }
  public void setTitulo(String titulo) { this.titulo = titulo; }
  public void setAutor(String autor) { this.autor = autor; }
  public void setAnoPublicacao(int anoPublicacao) { this.anoPublicacao = anoPublicacao; }
  public void setQuantidadeEstoque(int quantidadeEstoque) { this.quantidadeEstoque = quantidadeEstoque; }
}
