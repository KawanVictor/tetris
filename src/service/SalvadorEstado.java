package service;

import model.Partida;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;

public class SalvadorEstado {
    private static final String PASTA_SAVES = "saves";

    static {
        try { Files.createDirectories(Paths.get(PASTA_SAVES)); }
        catch (IOException e) { throw new RuntimeException(e); }
    }

    public static void salvarPartida(Partida partida, String nomeArquivo) throws IOException {
        Path caminho = Paths.get(PASTA_SAVES, nomeArquivo + ".dat");
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(caminho))) {
            oos.writeObject(partida);
        }
    }

    public static Partida carregarPartida(String nomeArquivo) throws IOException, ClassNotFoundException {
        Path caminho = Paths.get(PASTA_SAVES, nomeArquivo + ".dat");
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(caminho))) {
            return (Partida) ois.readObject();
        }
    }

    public static List<String> listarSaves() throws IOException {
        List<String> lista = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(PASTA_SAVES), "*.dat")) {
            for (Path arq : stream)
                lista.add(arq.getFileName().toString().replace(".dat", ""));
        }
        return lista;
    }
}
