package model;

public class SistemaPontuacao {
    private static final int[] PONTOS_BASE = {0, 100, 300, 500, 800};

    public int calcularPontos(int linhasEliminadas, int nivel) {
        if (linhasEliminadas < 0 || linhasEliminadas > 4)
            throw new IllegalArgumentException("Linhas eliminadas deve ser 0-4");
        return PONTOS_BASE[linhasEliminadas] * nivel;
    }

    public int calcularNovoNivel(int totalLinhas) {
        return totalLinhas / 10 + 1;
    }

    public long calcularVelocidade(int nivel) {
        // Velocidade diminui com o nível em ms
        return Math.max(50, 1000 - nivel * 50);
    }
}
