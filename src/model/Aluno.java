package src.model;

public class Aluno {
    private int id;
    private String nome;
    private String matricula;
    private String dataNascimento;

    public Aluno(int id, String nome, String matricula, String dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.matricula = matricula;
        this.dataNascimento = dataNascimento;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getMatricula() { return matricula; }
    public String getDataNascimento() { return dataNascimento; }

    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public void setDataNascimento(String dataNascimento) { this.dataNascimento = dataNascimento; }
}