package model;

import java.util.Arrays;

public class Tabuleiro {
    public static final int LARGURA = 10;
    public static final int ALTURA = 20;
    private final boolean[][] grid;

    public Tabuleiro() {
        grid = new boolean[ALTURA][LARGURA];
    }

    public boolean posicaoValida(Tetromino tetromino) {
        boolean[][] forma = tetromino.getForma();
        Posicao pos = tetromino.getPosicao();
        for (int i = 0; i < forma.length; i++) {
            for (int j = 0; j < forma[i].length; j++) {
                if (forma[i][j]) {
                    int x = pos.getX() + j;
                    int y = pos.getY() + i;
                    if (x < 0 || x >= LARGURA || y >= ALTURA)
                        return false;
                    if (y >= 0 && grid[y][x])
                        return false;
                }
            }
        }
        return true;
    }

    public void fixarTetromino(Tetromino tetromino) {
        boolean[][] forma = tetromino.getForma();
        Posicao pos = tetromino.getPosicao();
        for (int i = 0; i < forma.length; i++) {
            for (int j = 0; j < forma[i].length; j++) {
                if (forma[i][j]) {
                    int x = pos.getX() + j;
                    int y = pos.getY() + i;
                    if (y >= 0)
                        grid[y][x] = true;
                }
            }
        }
    }

    public void definirBloco(int x, int y, boolean valor) {
        if (x >= 0 && x < LARGURA && y >= 0 && y < ALTURA) {
            grid[y][x] = valor;
        }
    }

    public boolean temBloco(int x, int y) {
        if (x >= 0 && x < LARGURA && y >= 0 && y < ALTURA) {
            return grid[y][x];
        }
        return false;
    }

    public int eliminarLinhasCompletas() {
        int linhasEliminadas = 0;
        for (int y = ALTURA - 1; y >= 0; y--) {
            if (linhaCompleta(y)) {
                eliminarLinha(y);
                linhasEliminadas++;
                y++; // verificar a mesma linha novamente após eliminação
            }
        }
        return linhasEliminadas;
    }

    private boolean linhaCompleta(int y) {
        for (int x = 0; x < LARGURA; x++) {
            if (!grid[y][x]) return false;
        }
        return true;
    }

    private void eliminarLinha(int linha) {
        for (int y = linha; y > 0; y--) {
            System.arraycopy(grid[y - 1], 0, grid[y], 0, LARGURA);
        }
        Arrays.fill(grid[0], false);
    }
}
