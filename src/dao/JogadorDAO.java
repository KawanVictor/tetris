package dao;

import model.Jogador;
import infra.ConexaoBD;
import java.sql.*;
import java.util.*;

public class JogadorDAO {

    public void salvarJogador(Jogador jogador) throws SQLException {
        String sql = "INSERT INTO jogadores (id, nome, email, dataCriacao) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexaoBD.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, jogador.getId());
            stmt.setString(2, jogador.getNome());
            stmt.setString(3, jogador.getEmail());
            stmt.setTimestamp(4, Timestamp.valueOf(jogador.getDataCriacao()));
            stmt.executeUpdate();
        }
    }

    public Jogador buscarPorId(String id) throws SQLException {
        String sql = "SELECT * FROM jogadores WHERE id = ?";
        try (Connection conn = ConexaoBD.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next())
                    return new Jogador(
                        rs.getString("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getTimestamp("dataCriacao").toLocalDateTime());
            }
        }
        return null;
    }

    public List<Jogador> listarTodos() throws SQLException {
        List<Jogador> jogadores = new ArrayList<>();
        String sql = "SELECT * FROM jogadores ORDER BY nome";
        try (Connection conn = ConexaoBD.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next())
                jogadores.add(new Jogador(
                    rs.getString("id"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getTimestamp("dataCriacao").toLocalDateTime()
                ));
        }
        return jogadores;
    }
}
