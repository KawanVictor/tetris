package model;

public enum TipoTetromino {
    I(
        new boolean[][] {
            {true, true, true, true}
        }, 
        new boolean[][] {
            {true}, {true}, {true}, {true}
        }
    ),
    O(
        new boolean[][] {
            {true, true},
            {true, true}
        }
    ),
    T(
        new boolean[][] {
            {false, true, false},
            {true,  true, true}
        },
        new boolean[][] {
            {true, false},
            {true, true},
            {true, false}
        }
    ),
    S(
        new boolean[][] {
            {false, true, true},
            {true,  true, false}
        },
        new boolean[][] {
            {true, false},
            {true, true},
            {false, true}
        }
    ),
    Z(
        new boolean[][] {
            {true, true, false},
            {false, true, true}
        },
        new boolean[][] {
            {false, true},
            {true, true},
            {true, false}
        }
    ),
    J(
        new boolean[][] {
            {true, false, false},
            {true, true, true}
        },
        new boolean[][] {
            {true, true},
            {true, false},
            {true, false}
        }
    ),
    L(
        new boolean[][] {
            {false, false, true},
            {true, true, true}
        },
        new boolean[][] {
            {true, false},
            {true, false},
            {true, true}
        }
    );

    private final boolean[][][] formas;

    TipoTetromino(boolean[][]... formas) {
        this.formas = formas;
    }

    public boolean[][] getForma(int rotacao) {
        return formas[rotacao % formas.length];
    }
}
