package infra;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;

public class LogJogo implements Closeable {

    private static final String ARQUIVO_LOG = "tetris.log";
    private PrintWriter writer;

    public LogJogo() throws IOException {
        this.writer = new PrintWriter(new FileWriter(ARQUIVO_LOG, true), true);
        writer.println("---- Nova sessão em " + LocalDateTime.now() + " ----");
    }

    public void logInicioPartida(String jogadorId) {
        writer.printf("[%s] Partida iniciada - Jogador: %s%n", LocalDateTime.now(), jogadorId);
    }

    public void logPontuacao(String jogadorId, int pontos, int linhas, int nivel) {
        writer.printf("[%s] Pontuação - Jogador: %s, Pontos: %d, Linhas: %d, Nível: %d%n",
                      LocalDateTime.now(), jogadorId, pontos, linhas, nivel);
    }

    public void logGameOver(String jogadorId, int pontFinal, long duracaoMs) {
        writer.printf("[%s] GAME OVER - Jogador: %s, Pontuação: %d, Duração: %dms%n",
                      LocalDateTime.now(), jogadorId, pontFinal, duracaoMs);
    }

    public void logErro(String operacao, Exception e) {
        writer.printf("[%s] ERRO em %s: %s%n", LocalDateTime.now(), operacao, e.toString());
        e.printStackTrace(writer);
    }

    @Override
    public void close() {
        writer.println("---- Sessão encerrada " + LocalDateTime.now() + " ----");
        writer.close();
    }

    public static List<String> lerUltimasLinhas(int qtd) throws IOException {
        List<String> linhas = Files.readAllLines(Paths.get(ARQUIVO_LOG));
        if (linhas.size() <= qtd) return linhas;
        return linhas.subList(linhas.size() - qtd, linhas.size());
    }
}
