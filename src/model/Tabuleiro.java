package model;

public class Tabuleiro {
    public static final int LARGURA = 10, ALTURA = 20;
    private final boolean[][] grid;

    public Tabuleiro() { grid = new boolean[ALTURA][LARGURA]; }

    public boolean posicaoValida(Tetromino tetromino) {
        boolean[][] forma = tetromino.getForma();
        Posicao pos = tetromino.getPosicao();
        for (int i = 0; i < forma.length; i++)
            for (int j = 0; j < forma[i].length; j++)
                if (forma[i][j]) {
                    int x = pos.getX() + j, y = pos.getY() + i;
                    if (x < 0 || x >= LARGURA || y >= ALTURA) return false;
                    if (y >= 0 && grid[y][x]) return false;
                }
        return true;
    }

    public void fixarTetromino(Tetromino tetromino) {
        boolean[][] forma = tetromino.getForma();
        Posicao pos = tetromino.getPosicao();
        for (int i = 0; i < forma.length; i++)
            for (int j = 0; j < forma[i].length; j++)
                if (forma[i][j]) {
                    int x = pos.getX() + j, y = pos.getY() + i;
                    if (y >= 0) grid[y][x] = true;
                }
    }

    public boolean temBloco(int x, int y) {
        if (x >= 0 && x < LARGURA && y >= 0 && y < ALTURA) return grid[y][x];
        return false;
    }

    public void definirBloco(int x, int y, boolean valor) {
        if (x >= 0 && x < LARGURA && y >= 0 && y < ALTURA)
            grid[y][x] = valor;
    }

    public int eliminarLinhasCompletas() {
        int eliminadas = 0;
        for (int y = ALTURA-1; y >= 0; y--) {
            boolean completa = true;
            for (int x = 0; x < LARGURA; x++) if (!grid[y][x]) completa = false;
            if (completa) {
                for (int k = y; k > 0; k--) grid[k] = grid[k-1].clone();
                grid[0] = new boolean[LARGURA];
                eliminadas++;
                y++;
            }
        }
        return eliminadas;
    }
}
