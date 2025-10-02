package infra;

import java.sql.*;

public class ConexaoBD {
    private static final String URL = "jdbc:mysql://localhost:3306/tetrisdb";
    private static final String USUARIO = "root";
    private static final String SENHA = "senha123";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver MySQL não encontrado!", e);
        }
    }

    public static Connection obterConexao() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    public static void testarConexao() {
        try (Connection conn = obterConexao()) {
            System.out.println("Conexão estabelecida com sucesso!");
            System.out.println("Database: " + conn.getCatalog());
        } catch (SQLException e) {
            System.err.println("Erro ao conectar: " + e.getMessage());
        }
    }
}
