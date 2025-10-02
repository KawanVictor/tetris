package dao;

import model.Partida;
import infra.ConexaoBD;
import java.sql.*;

public class PartidaDAO {
    public void salvarPartida(Partida partida) throws SQLException {
        Connection conn = null;
        try {
            conn = ConexaoBD.obterConexao();
            conn.setAutoCommit(false);

            String sql = "INSERT INTO partidas (id, jogadorid, pontuacao, linhaseliminadas, nivelalcancado, duracaosegundos) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, partida.getId());
                stmt.setString(2, partida.getJogadorId());
                stmt.setInt(3, partida.getPontuacao());
                stmt.setInt(4, partida.getTotalLinhas());
                stmt.setInt(5, partida.getNivel());
                stmt.setLong(6, partida.getDuracaoSegundos());
                stmt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) try { conn.rollback(); } catch (SQLException ex) { e.addSuppressed(ex); }
            throw e;
        } finally {
            if (conn != null) try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) { }
        }
    }
    // Expanda para buscar, listar, ranking etc.
}
