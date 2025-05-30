package src.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import src.model.Emprestimo;

public class EmprestimoDAO {
    private Connection conexao;

    public EmprestimoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void registrarEmprestimo(int idAluno, int idLivro) throws SQLException {
        String verificaEstoque = "SELECT quantidade_estoque FROM Livros WHERE id_livro = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(verificaEstoque)) {
            stmt.setInt(1, idLivro);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int estoque = rs.getInt("quantidade_estoque");
                if (estoque <= 0) {
                    throw new SQLException("Estoque insuficiente para o livro.");
                }
            } else {
                throw new SQLException("Livro não encontrado.");
            }
        }
    
        String atualizaEstoque = "UPDATE Livros SET quantidade_estoque = quantidade_estoque - 1 WHERE id_livro = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(atualizaEstoque)) {
            stmt.setInt(1, idLivro);
            stmt.executeUpdate();
        }
    
        String sql = "INSERT INTO Emprestimos (id_aluno, id_livro, data_emprestimo, data_devolucao) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            stmt.setInt(2, idLivro);
            stmt.setDate(3, java.sql.Date.valueOf(java.time.LocalDate.now()));  
            stmt.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now().plusDays(7))); 
            stmt.executeUpdate();
        }
    }
    

    public List<Emprestimo> listarEmprestimos() throws SQLException {
        List<Emprestimo> lista = new ArrayList<>();
        String sql = "SELECT * FROM Emprestimos";
        try (Statement stmt = conexao.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Emprestimo e = new Emprestimo(
                    rs.getInt("id_emprestimo"),
                    rs.getInt("id_aluno"),
                    rs.getInt("id_livro"),
                    rs.getDate("data_emprestimo").toString(),
                    rs.getDate("data_devolucao").toString()
                );
                lista.add(e);
            }
        }
        return lista;
    }

    public void atualizarDataDevolucao(int idEmprestimo, String novaDataDevolucao) throws SQLException {
        String sql = "UPDATE Emprestimos SET data_devolucao = ? WHERE id_emprestimo = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(novaDataDevolucao));
            stmt.setInt(2, idEmprestimo);
            stmt.executeUpdate();
        }
    }

    public void deletarEmprestimo(int idEmprestimo) throws SQLException {
        String sqlLivro = "SELECT id_livro FROM Emprestimos WHERE id_emprestimo = ?";
        int idLivro = -1;
        try (PreparedStatement stmt = conexao.prepareStatement(sqlLivro)) {
            stmt.setInt(1, idEmprestimo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                idLivro = rs.getInt("id_livro");
            } else {
                throw new SQLException("Empréstimo não encontrado.");
            }
        }

        String atualizaEstoque = "UPDATE Livros SET quantidade_estoque = quantidade_estoque + 1 WHERE id_livro = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(atualizaEstoque)) {
            stmt.setInt(1, idLivro);
            stmt.executeUpdate();
        }

        String sql = "DELETE FROM Emprestimos WHERE id_emprestimo = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idEmprestimo);
            stmt.executeUpdate();
        }
    }

    public Emprestimo buscarEmprestimoPorId(int id) throws SQLException {
        String sql = "SELECT * FROM emprestimos WHERE id_emprestimo = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Emprestimo(
                    rs.getInt("id_emprestimo"),
                    rs.getInt("id_aluno"),
                    rs.getInt("id_livro"),
                    rs.getDate("data_emprestimo").toString(),
                    rs.getDate("data_devolucao").toString()
                );
            }
        }
        return null;
    }

    public int getUltimoEmprestimoId() throws SQLException {
        String sql = "SELECT LAST_INSERT_ID() as id";
        try (Statement stmt = conexao.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        return -1;
    }
}