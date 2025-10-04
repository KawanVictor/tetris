package dao;

import model.Partida;
import infra.ConexaoBD;
import java.sql.*;
import java.util.*;

public class PartidaDAO {

    public void salvarPartida(Partida partida) throws SQLException {
        String sql = "INSERT INTO partidas (id, jogadorId, pontuacao, totalLinhas, nivel, tipoPecaAtual, dataHora) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoBD.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, partida.getId());
            stmt.setString(2, partida.getJogadorId());
            stmt.setInt(3, partida.getPontuacao());
            stmt.setInt(4, partida.getTotalLinhas());
            stmt.setInt(5, partida.getNivel());
            stmt.setString(6, partida.getTipoPecaAtual());
            stmt.setTimestamp(7, new Timestamp(partida.getDataHora()));
            stmt.executeUpdate();
        }
    }

    public List<Partida> obterTopPontuacoes(int limite) throws SQLException {
        String sql = "SELECT p.*, j.nome FROM partidas p JOIN jogadores j ON p.jogadorId = j.id ORDER BY p.pontuacao DESC LIMIT ?";
        List<Partida> ranking = new ArrayList<>();
        try (Connection conn = ConexaoBD.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, limite);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Partida partida = new Partida();
                    partida.setJogadorId(rs.getString("jogadorId"));
                    partida.setPontuacao(rs.getInt("pontuacao"));
                    // Pode incluir nome do jogador com um getter/setter extra se desejar
                    ranking.add(partida);
                }
            }
        }
        return ranking;
    }
}
