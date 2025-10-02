package ui;

import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TetrisGUI extends JFrame {
    private Tabuleiro tabuleiro;
    // ... outros campos para peças, timer de animação, controle, etc.

    public TetrisGUI() {
        setTitle("Tetris Java");
        setSize(400, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        tabuleiro = new Tabuleiro();
        // Inicializar painel, controles, etc.
        // Adicionar listeners de tecla e renderização
    }

    // Métodos para renderizar tabuleiro, mover peças, atualizar tela
    // Exemplo de sobrescrita de paintComponent:
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int y = 0; y < Tabuleiro.ALTURA; y++) {
            for (int x = 0; x < Tabuleiro.LARGURA; x++) {
                if (tabuleiro.temBloco(x, y)) {
                    g.setColor(Color.BLUE); // cor da peça
                    g.fillRect(x * 40, y * 40, 38, 38);
                }
            }
        }
    }
}
