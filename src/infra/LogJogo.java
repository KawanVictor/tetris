package infra;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;

public class LogJogo implements Closeable {
    private static final String ARQUIVO_LOG = "tetris.log";
    private final PrintWriter writer;

    public LogJogo() throws IOException {
        FileWriter fileWriter = new FileWriter(ARQUIVO_LOG, true);
        this.writer = new PrintWriter(fileWriter, true);
        writer.println("NOVA SESSÃO INICIADA EM " + LocalDateTime.now());
    }

    public void logInicioPartida(String jogadorId) {
        writer.printf("PARTIDA INICIADA - Jogador: %s - %s\n", jogadorId, LocalDateTime.now());
    }

    public void logPontuacao(String jogadorId, int pontos, int linhas, int nivel) {
        writer.printf("PONTUAÇÃO - Jogador: %s, Pontos: %d, Linhas: %d, Nível: %d - %s\n", jogadorId, pontos, linhas, nivel, LocalDateTime.now());
    }

    public void logGameOver(String jogadorId, int pontuacaoFinal, long duracaoMs) {
        writer.printf("GAME OVER - Jogador: %s, Pontuação Final: %d, Duração: %dms - %s\n", jogadorId, pontuacaoFinal, duracaoMs, LocalDateTime.now());
    }

    public void logErro(String operacao, Exception e) {
        writer.printf("ERRO em %s - %s: %s\n", operacao, LocalDateTime.now(), e.getMessage());
        e.printStackTrace(writer);
    }

    @Override
    public void close() {
        writer.println("SESSÃO ENCERRADA EM " + LocalDateTime.now());
        writer.close();
    }

    public static List<String> lerUltimasLinhas(int quantidade) throws IOException {
        List<String> todasLinhas = Files.readAllLines(Paths.get(ARQUIVO_LOG));
        if (todasLinhas.size() <= quantidade) return todasLinhas;
        return todasLinhas.subList(todasLinhas.size() - quantidade, todasLinhas.size());
    }
}
