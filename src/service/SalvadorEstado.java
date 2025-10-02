package service;

import model.Partida;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SalvadorEstado {
    private static final String PASTA_SAVES = "saves";

    static {
        try {
            Files.createDirectories(Paths.get(PASTA_SAVES));
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível criar pasta de saves", e);
        }
    }

    public static void salvarPartida(Partida partida, String nomeArquivo) throws IOException {
        Path caminhoArquivo = Paths.get(PASTA_SAVES, nomeArquivo + ".json");
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("\"jogadorId\":\"").append(partida.getJogadorId()).append("\",\n");
        json.append("\"pontuacao\":").append(partida.getPontuacao()).append(",\n");
        json.append("\"nivel\":").append(partida.getNivel()).append(",\n");
        json.append("\"totalLinhas\":").append(partida.getTotalLinhas()).append(",\n");
        json.append("\"gameOver\":").append(partida.isGameOver()).append(",\n");
        json.append("\"timestampSave\":\"").append(LocalDateTime.now()).append("\"\n");
        // Tabuleiro e tetromino podem ser salvos também, expandir conforme necessário
        json.append("}\n");
        Files.write(caminhoArquivo, json.toString().getBytes());
    }

    public static List<String> listarSaves() throws IOException {
        List<String> saves = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(PASTA_SAVES), "*.json")) {
            for (Path arquivo : stream) {
                String nome = arquivo.getFileName().toString();
                saves.add(nome.substring(0, nome.lastIndexOf(".")));
            }
        }
        return saves;
    }
}
