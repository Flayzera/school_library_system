package src.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import src.model.Aluno;

public class AlunoDAO {
    private Connection conexao;

    public AlunoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public int inserirAluno(Aluno aluno) throws SQLException {
        String sql = "INSERT INTO Alunos (nome_aluno, matricula, data_nascimento) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getMatricula());
            stmt.setString(3, aluno.getDataNascimento());
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    public List<Aluno> listarAlunos() throws SQLException {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM Alunos";
        try (Statement stmt = conexao.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Aluno a = new Aluno(
                    rs.getInt("id_aluno"),
                    rs.getString("nome_aluno"),
                    rs.getString("matricula"),
                    rs.getDate("data_nascimento").toString()
                );
                alunos.add(a);
            }
        }
        return alunos;
    }

    public void atualizarAluno(Aluno aluno) throws SQLException {
        String sql = "UPDATE Alunos SET nome_aluno = ?, data_nascimento = ? WHERE id_aluno = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getDataNascimento());
            stmt.setInt(3, aluno.getId());
            stmt.executeUpdate();
        }
    }

    public void deletarAluno(int id) throws SQLException {
        String sql = "DELETE FROM Alunos WHERE id_aluno = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Aluno buscarAlunoPorId(int id) throws SQLException {
        String sql = "SELECT * FROM alunos WHERE id_aluno = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Aluno(
                    rs.getInt("id_aluno"),
                    rs.getString("nome_aluno"),
                    rs.getString("matricula"),
                    rs.getDate("data_nascimento").toString()
                );
            }
        }
        return null;
    }
}
