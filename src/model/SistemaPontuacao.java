package model;

public class SistemaPontuacao {
    private static final int[] PONTOS_BASE = {0, 100, 300, 500, 800};
    public int calcularPontos(int linhas, int nivel) {
        if (linhas < 0 || linhas > 4) throw new IllegalArgumentException();
        return PONTOS_BASE[linhas] * nivel;
    }
    public int calcularNovoNivel(int totalLinhas) { return totalLinhas / 10 + 1; }
    public long calcularVelocidade(int nivel) { return Math.max(50, 1000 - nivel * 50); }
}
