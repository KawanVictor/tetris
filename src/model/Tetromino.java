package model;

public class Tetromino {
    private final TipoTetromino tipo;
    private Posicao posicao;
    private int rotacao;

    public Tetromino(TipoTetromino tipo, Posicao posicao) {
        this.tipo = tipo;
        this.posicao = posicao;
        this.rotacao = 0;
    }

    public void mover(int dx, int dy) { posicao = posicao.mover(dx, dy); }
    public void rotacionar() { rotacao = (rotacao+1)%4; }
    public boolean[][] getForma() { return tipo.getForma(rotacao); }
    public Posicao getPosicao() { return posicao; }
    public TipoTetromino getTipo() { return tipo; }
    public int getRotacao() { return rotacao; }
}
