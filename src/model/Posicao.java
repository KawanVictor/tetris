package model;

import java.util.Objects;

public class Posicao {
    private final int x;
    private final int y;

    public Posicao(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Posicao mover(int dx, int dy) {
        return new Posicao(x + dx, y + dy);
    }

    public int getX() { return x; }
    public int getY() { return y; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Posicao)) return false;
        Posicao outra = (Posicao) obj;
        return x == outra.x && y == outra.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
