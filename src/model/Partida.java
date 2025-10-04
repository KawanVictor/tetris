package model;

public class Partida {
    private String id, jogadorId, tipoPecaAtual;
    private int pontuacao, totalLinhas, nivel;
    private Tabuleiro tabuleiro;
    private boolean gameOver;
    private long dataHora;
    
    public Partida(String id, String jogadorId, int pontuacao, int totalLinhas,
                   int nivel, String tipoPecaAtual, Tabuleiro tab, boolean gameOver, long dataHora) {
        this.id = id; this.jogadorId = jogadorId; this.pontuacao = pontuacao; this.totalLinhas = totalLinhas;
        this.nivel = nivel; this.tipoPecaAtual = tipoPecaAtual; this.tabuleiro = tab;
        this.gameOver = gameOver; this.dataHora = dataHora;
    }
    public Partida() {}
    public String getId() { return id; }
    public String getJogadorId() { return jogadorId; }
    public int getPontuacao() { return pontuacao; }
    public int getTotalLinhas() { return totalLinhas; }
    public int getNivel() { return nivel; }
    public String getTipoPecaAtual() { return tipoPecaAtual; }
    public Tabuleiro getTabuleiro() { return tabuleiro; }
    public boolean isGameOver() { return gameOver; }
    public long getDataHora() { return dataHora; }
    public void setJogadorId(String id) { this.jogadorId = id;}
    public void setPontuacao(int p) { this.pontuacao = p;}
}
