package src.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import src.model.Livro;

public class LivroDAO {
    private Connection conexao;

    public LivroDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserirLivro(Livro livro) throws SQLException {
        String sql = "INSERT INTO Livros (titulo, autor, ano_publicacao, quantidade_estoque) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setInt(3, livro.getAnoPublicacao());
            stmt.setInt(4, livro.getQuantidadeEstoque());
            stmt.executeUpdate();
        }
    }

    public List<Livro> listarLivros() throws SQLException {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM Livros";
        try (Statement stmt = conexao.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Livro l = new Livro(
                    rs.getInt("id_livro"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getInt("ano_publicacao"),
                    rs.getInt("quantidade_estoque")
                );
                livros.add(l);
            }
        }
        return livros;
    }

    public void atualizarLivro(Livro livro) throws SQLException {
        String sql = "UPDATE Livros SET titulo = ?, autor = ?, ano_publicacao = ?, quantidade_estoque = ? WHERE id_livro = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setInt(3, livro.getAnoPublicacao());
            stmt.setInt(4, livro.getQuantidadeEstoque());
            stmt.setInt(5, livro.getId());
            stmt.executeUpdate();
        }
    }

    public void deletarLivro(int id) throws SQLException {
        String sql = "DELETE FROM Livros WHERE id_livro = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
